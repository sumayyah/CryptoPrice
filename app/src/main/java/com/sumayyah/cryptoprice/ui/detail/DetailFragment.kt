package com.sumayyah.cryptoprice.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.sumayyah.cryptoprice.MainApplication
import com.sumayyah.cryptoprice.R
import com.sumayyah.cryptoprice.ui.main.MainViewModel
import com.sumayyah.cryptoprice.ui.main.MainViewModelFactory
import javax.inject.Inject

class DetailFragment : Fragment() {

    private lateinit var label: TextView
    private lateinit var name: TextView
    private lateinit var price: TextView
    private lateinit var volume: TextView
    private lateinit var timestamp: TextView

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private val viewModel: MainViewModel by activityViewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.detail_layout, container, false)

        label = view.findViewById(R.id.label_value)
        name = view.findViewById(R.id.name_value)
        price = view.findViewById(R.id.price_value)
        volume = view.findViewById(R.id.volume_value)
        timestamp = view.findViewById(R.id.timestamp_value)

        showCoinInfo()
        return view
    }

    private fun showCoinInfo() {
        viewModel.currentCoinSelected?.let {
            label.text = it.label
            name.text = it.name
            price.text = it.price.toString()
            volume.text = it.volume_24h.toString()
            timestamp.text = it.timestamp.toString()
        }
    }
}