package com.sumayyah.cryptoprice

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.sumayyah.finance.FinanceActivity
import com.sumayyah.cryptoprice.model.MarketsResponse
import com.sumayyah.cryptoprice.ui.detail.DetailFragment
import com.sumayyah.cryptoprice.ui.main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        val testButton = findViewById<Button>(R.id.testButton)
        testButton.setOnClickListener {
            Timber.d("Sumi clicked on testButton")
//            startActivity(Intent(this, FinanceActivity::class.java))
        }

        (application as MainApplication).component.inject(this)
        lifecycle.addObserver(viewModel)
    }
}
