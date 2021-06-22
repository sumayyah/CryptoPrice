package com.example.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LinkAccountFragment : Fragment() {
    private lateinit var linkButton: Button

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_link_account, container, false)

        linkButton = view.findViewById(R.id.linkButton)

        linkButton.setOnClickListener {
            findNavController().navigate(R.id.action_linkAccountFragment_to_linkAccountConfirmedFragment)
        }
        return view
    }
}