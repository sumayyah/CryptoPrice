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
import com.sumayyah.cryptoprice.ui.detail.DetailFragment
import com.sumayyah.cryptoprice.ui.main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainFragment.MainUserActionInterface {


    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private var mainFragment: MainFragment? = null
    private var detailFragment: DetailFragment? = null

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        (application as MainApplication).component.inject(this)
        lifecycle.addObserver(viewModel)

        if (mainFragment == null) {
            mainFragment = MainFragment().apply { listener = this@MainActivity }
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, mainFragment!!)
            .addToBackStack("main")
            .commit()
    }

    override fun onCoinTapped(label: String) {
        if (detailFragment == null) {
            detailFragment = DetailFragment()
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, detailFragment!!)
            .addToBackStack("detail")
            .commit()
    }
}
