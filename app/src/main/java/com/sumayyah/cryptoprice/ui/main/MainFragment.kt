package com.sumayyah.cryptoprice.ui.main

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumayyah.cryptoprice.MainApplication
import com.sumayyah.cryptoprice.R
import com.sumayyah.cryptoprice.model.Market
import javax.inject.Inject

class MainFragment : Fragment() {

    private lateinit var listView: RecyclerView
    private lateinit var listAdapter: CoinAdapter
    private lateinit var loadingView: ProgressBar
    private lateinit var mainContentView: ConstraintLayout

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    val viewModel: MainViewModel by activityViewModels { viewModelFactory }

    var listener: MainUserActionInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.main_fragment, container, false)

        loadingView = view.findViewById(R.id.progress_loader)
        mainContentView = view.findViewById(R.id.mainContent)
        listView = view.findViewById(R.id.list)

        val layoutManager = LinearLayoutManager(activity)
        listView.layoutManager = layoutManager

        listAdapter = CoinAdapter(requireContext(), arrayListOf(), coinHandler)
        listView.adapter = listAdapter

        setObservers()

        return view
    }

    private fun setObservers() {
        viewModel.callStatus.observe(viewLifecycleOwner, Observer { responseData ->

            when (responseData.responseStatus) {
                ResponseStatus.SUCCESS -> {
                    hideLoading()
                }
                ResponseStatus.LOADING -> {
                    hideData()
                    showLoading()
                }
                ResponseStatus.ERROR -> {
                    showError(responseData.error?.message)
                }
            }
        })

        viewModel.currentCoinList.observe(viewLifecycleOwner, Observer { coinList ->
            updateData(coinList)
        })
    }

    private fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    private fun updateData(data: List<Market>?) {
        mainContentView.visibility = View.VISIBLE
        data?.let { listAdapter.swapData(it) }
    }

    private fun hideData() {
        mainContentView.visibility = View.GONE
    }

    private fun showError(errorMessage: String?) {
        AlertDialog.Builder(activity)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("Ok", null)
            .create()
            .show()
    }

    private val coinHandler : (String) -> Unit = {
        listener?.onCoinTapped(it)
    }

    interface MainUserActionInterface {
        fun onCoinTapped(label: String)
    }
}