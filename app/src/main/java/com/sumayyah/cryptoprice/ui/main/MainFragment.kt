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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumayyah.cryptoprice.MainApplication
import com.sumayyah.cryptoprice.R
import com.sumayyah.cryptoprice.model.MarketsResponse

class MainFragment : Fragment() {

    private lateinit var listView: RecyclerView
    private lateinit var listAdapter: CoinAdapter
    private lateinit var loadingView: ProgressBar
    private lateinit var mainContentView: ConstraintLayout

//    val viewModel = ViewModelProviders.of(activity, )

    val viewModel: MainViewModel by activityViewModels {  }

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

        listAdapter = CoinAdapter(requireContext(), arrayListOf())
        listView.adapter = listAdapter

        return view
    }

    private fun showSuccess(data: MarketsResponse?) {

        hideLoading()
        showData(data)
    }

    private fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    private fun showData(data: MarketsResponse?) {
        mainContentView.visibility = View.VISIBLE
        data?.let { response ->
            listAdapter.swapData(response.markets.get(0).subList(0, 20))
        }
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
}