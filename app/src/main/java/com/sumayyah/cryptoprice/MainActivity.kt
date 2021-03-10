package com.sumayyah.cryptoprice

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumayyah.cryptoprice.model.MarketsResponse
import com.sumayyah.cryptoprice.ui.main.CoinAdapter
import com.sumayyah.cryptoprice.ui.main.MainViewModel
import com.sumayyah.cryptoprice.ui.main.MainViewModelFactory
import com.sumayyah.cryptoprice.ui.main.ResponseStatus
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var listView: RecyclerView
    private lateinit var listAdapter: CoinAdapter
    private lateinit var loadingView: ProgressBar
    private lateinit var mainContentView: ConstraintLayout

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        (application as MainApplication).component.inject(this)
        lifecycle.addObserver(viewModel)

        loadingView = findViewById(R.id.progress_loader)
        mainContentView = findViewById(R.id.mainContent)
        listView = findViewById(R.id.list)

        val layoutManager = LinearLayoutManager(this)
        listView.layoutManager = layoutManager

        listAdapter = CoinAdapter(this, arrayListOf())
        listView.adapter = listAdapter

        setObservers()
    }

    private fun setObservers() {
        viewModel.callStatus.observe(this, Observer { responseData ->

            when (responseData.responseStatus) {
                ResponseStatus.SUCCESS -> {
                    showSuccess(responseData.data)
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
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("Ok", null)
            .create()
            .show()
    }
}
