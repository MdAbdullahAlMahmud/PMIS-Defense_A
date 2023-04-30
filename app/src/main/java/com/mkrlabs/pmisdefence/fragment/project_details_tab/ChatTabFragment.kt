package com.mkrlabs.pmisdefence.fragment.project_details_tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.FragmentChatTabBinding
import com.mkrlabs.pmisdefence.util.Constant

class ChatTabFragment : Fragment() {

    lateinit var binding : FragmentChatTabBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.superVisorChatItem.setOnClickListener{
            findNavController().navigate(R.id.action_chatTabFragment_to_chatFragment)

        }


    }


}