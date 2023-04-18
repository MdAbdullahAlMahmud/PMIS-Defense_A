package com.mkrlabs.pmisdefence.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {


    lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.alreadyhaveAnAccountButton.setOnClickListener {

            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)

        }





    }


}