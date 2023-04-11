package com.mkrlabs.pmisdefence.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {


    lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.donthaveAnAccountTV.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }


}