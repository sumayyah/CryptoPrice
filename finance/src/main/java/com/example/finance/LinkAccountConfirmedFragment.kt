package com.example.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LinkAccountConfirmedFragment : Fragment() {
    private lateinit var continueButton: Button

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_link_confirmed, container, false)

        continueButton = view.findViewById(R.id.continueButton)
        continueButton.setOnClickListener {
            findNavController().navigate(R.id.action_linkAccountConfirmedFragment_to_tradeFragment)
        }

        return view
    }
}