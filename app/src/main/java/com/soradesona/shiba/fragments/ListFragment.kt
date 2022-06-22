package com.soradesona.shiba.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.soradesona.shiba.R
import com.soradesona.shiba.RecyclerViewPaddingItemDecoration
import com.soradesona.shiba.adapter.ShibaAdapter
import com.soradesona.shiba.status.StatusEnum
import com.soradesona.shiba.viewmodel.ShibaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment() : Fragment() {

    private val viewModel: ShibaViewModel by viewModels()

    @Inject lateinit var mAdapter: ShibaAdapter

    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()
        setObservers()
        initButtons()
    }

    private fun setViews() {
        mRecyclerView = main_recycler_view
        mRecyclerView.apply {
            adapter = mAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(
                RecyclerViewPaddingItemDecoration()
            )
        }
    }

    private fun setObservers() {
        viewModel.loadingStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                StatusEnum.LOADING -> {
                    mRecyclerView.visibility = View.GONE
                    progress_bar_loading.visibility = View.VISIBLE
                    error.visibility = View.GONE
                }
                StatusEnum.SUCCESS -> {
                    mRecyclerView.visibility = View.VISIBLE
                    progress_bar_loading.visibility = View.GONE
                    error.visibility = View.GONE
                }
                StatusEnum.ERROR -> {
                    error.visibility = View.VISIBLE
                }
                else -> println("should not happen")
            }
        }

        viewModel.shibaResponse.observe(viewLifecycleOwner) { shibas ->
            renderShibaList(shibas)
        }
    }

    private fun renderShibaList(shibas: List<String>) {
        mAdapter.apply {
            addData(shibas)
            notifyDataSetChanged()
        }
    }

    private fun initButtons() {
        error.setOnClickListener {
            viewModel.loadShibaList()
        }

        requireActivity().settings_button.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_settingsFragment)
        }

    }


}