package com.soradesona.shiba.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.soradesona.shiba.RecyclerViewPaddingItemDecoration
import com.soradesona.shiba.ShibaImageDownloader
import com.soradesona.shiba.adapter.CommonAdapter
import com.soradesona.shiba.databinding.ListFragmentBinding
import com.soradesona.shiba.status.StatusEnum
import com.soradesona.shiba.viewmodel.ShibaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment() : Fragment() {

    private val viewModel: ShibaViewModel by viewModels()

    private val args: ListFragmentArgs by navArgs()

    private var commonAdapter : CommonAdapter? = null

    private var shibaImageDownloader: ShibaImageDownloader? = null

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

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
        viewModel.loadList(args.categoryName)
        setViews()
        setObservers()
        initButtons()
    }

    private fun setViews() {

        shibaImageDownloader = ShibaImageDownloader(this.requireContext())

        commonAdapter = CommonAdapter(shibaImageDownloader!!, args.categoryName)

        binding.commonRv.apply {
            adapter = commonAdapter
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                RecyclerViewPaddingItemDecoration()
            )
        }
    }

    private fun setObservers() {
        viewModel.loadingStatus.observe(viewLifecycleOwner) { status ->
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

        viewModel.listResponse.observe(viewLifecycleOwner) { typeList ->
            commonAdapter?.addData(typeList)
        }
    }

    private fun initButtons() {

        when(args.categoryName){
            "shibes" -> binding.mainTitle.text = "Shibas"
            "cats" -> binding.mainTitle.text = "Cats"
            "birds" -> binding.mainTitle.text = "Birds"
        }

        binding.error.setOnClickListener {
            viewModel.loadList(args.categoryName)
        }


        binding.swipe.setOnRefreshListener {
            viewModel.loadList(args.categoryName)
            binding.swipe.isRefreshing = false
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        commonAdapter = null
        shibaImageDownloader = null
        _binding = null
    }


}