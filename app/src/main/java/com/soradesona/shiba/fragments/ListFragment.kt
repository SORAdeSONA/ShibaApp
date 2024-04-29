package com.soradesona.shiba.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.soradesona.shiba.RecyclerViewPaddingItemDecoration
import com.soradesona.shiba.ShibaImageDownloader
import com.soradesona.shiba.adapter.CommonAdapter
import com.soradesona.shiba.adapter.LoadMoreAdapter
import com.soradesona.shiba.adapter.PagingAdapter
import com.soradesona.shiba.databinding.ListFragmentBinding
import com.soradesona.shiba.status.StatusEnum
import com.soradesona.shiba.viewmodel.ShibaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ListFragment() : Fragment() {

    private val viewModel: ShibaViewModel by viewModels()

    private val args: ListFragmentArgs by navArgs()

    private var commonAdapter: CommonAdapter? = null
    private var pagingAdapter: PagingAdapter? = null

    private var shibaImageDownloader: ShibaImageDownloader? = null

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private var loadingType: Boolean = false


    private var loadListPagingJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingType = viewModel.getCurrentDownloadType()
        viewModel.pagingTypeToDownload = args.categoryName

        if (!loadingType) viewModel.loadList(args.categoryName)

        setViews()
        setObservers()
        initButtons()
    }

    private fun setViews() {

        shibaImageDownloader = ShibaImageDownloader(this.requireContext())

        if (!loadingType) {
            commonAdapter = CommonAdapter(shibaImageDownloader!!, args.categoryName)

            binding.commonRv.apply {
                adapter = commonAdapter
                layoutManager = LinearLayoutManager(this.context)
                addItemDecoration(
                    RecyclerViewPaddingItemDecoration()
                )
            }
        } else {
            pagingAdapter = PagingAdapter()

            val footerAdapter = LoadMoreAdapter() { pagingAdapter!!.retry() }

            binding.commonRv.apply {
                adapter = pagingAdapter!!.withLoadStateFooter(footerAdapter)
                layoutManager = LinearLayoutManager(this.context)
                addItemDecoration(
                    RecyclerViewPaddingItemDecoration()
                )
            }

        }

    }

    private fun setObservers() {
        viewModel.loadingStatusMain.observe(viewLifecycleOwner) { status ->
            when (status) {
                StatusEnum.LOADING -> {
                    binding.commonRv.visibility = View.GONE
                    binding.progressBarLoading.visibility = View.VISIBLE
                }

                StatusEnum.SUCCESS -> {
                    binding.commonRv.visibility = View.VISIBLE
                    binding.progressBarLoading.visibility = View.GONE
                    binding.error.visibility = View.GONE
                }

                StatusEnum.ERROR -> {
                    binding.error.visibility = View.VISIBLE
                }

                else -> println("should not happen")
            }
        }

        viewModel.listResponse.observe(viewLifecycleOwner) {
            if (!loadingType) {
                commonAdapter?.addData(it)
            }
        }

        when (loadingType) {
            true -> {
                loadListPagingJob = CoroutineScope(Dispatchers.IO).launch {
                    viewModel.loadListPaging.collect {
                        pagingAdapter?.submitData(it)
                        pagingAdapter?.retry()
                    }
                }
            }

            else -> {}
        }

    }

    private fun initButtons() {

        when (args.categoryName) {
            "shibes" -> binding.mainTitle.text = "Shibas"
            "cats" -> binding.mainTitle.text = "Cats"
            "birds" -> binding.mainTitle.text = "Birds"
        }

        binding.error.setOnClickListener {
            refreshData()
        }


        binding.swipe.setOnRefreshListener {
            refreshData()
            binding.swipe.isRefreshing = false
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun refreshData() {
        when (loadingType) {
            false -> {
                viewModel.loadList(args.categoryName)
            }

            true -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.refreshData()

                    viewModel.loadListPaging.collect {
                        pagingAdapter?.submitData(it)
                        pagingAdapter?.retry()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        commonAdapter = null
        pagingAdapter = null
        shibaImageDownloader = null
        _binding = null
    }


}