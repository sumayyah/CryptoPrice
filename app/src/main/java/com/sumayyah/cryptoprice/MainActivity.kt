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


    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        (application as MainApplication).component.inject(this)
        lifecycle.addObserver(viewModel)
    }
}
