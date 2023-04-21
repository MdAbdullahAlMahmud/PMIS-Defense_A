package com.mkrlabs.pmisdefence.fragment

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.FragmentSignUpBinding
import com.mkrlabs.pmisdefence.model.Teacher
import com.mkrlabs.pmisdefence.util.CommonFunction
import com.mkrlabs.pmisdefence.util.Resource
import com.mkrlabs.pmisdefence.view_model.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {


    lateinit var binding: FragmentSignUpBinding
    lateinit var authViewModel : AuthenticationViewModel



     val TAG :String = "Auth"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]


        binding.alreadyhaveAnAccountButton.setOnClickListener {

            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)

        }


        binding.signUpSignUpButton.setOnClickListener {


            val name = binding.signUpNameEdt.text.toString()
            val email = binding.signUpEmailEdt.text.toString()
            val teacher_id = binding.signUpIDEdt.text.toString()
            val password = binding.signUpPasswordEdt.text.toString()
            val confirm_password = binding.signUpConfirmPasswordEdt.text.toString()


            if (TextUtils.isEmpty(name)){
                binding.signUpNameEdt.error= "required"
                return@setOnClickListener
            }

             if (TextUtils.isEmpty(email)){
                binding.signUpEmailEdt.error= "required"
                return@setOnClickListener
            }

             if (TextUtils.isEmpty(teacher_id)){
                binding.signUpIDEdt.error= "required"
                return@setOnClickListener
            }

             if (TextUtils.isEmpty(password)){
                binding.signUpPasswordEdt.error= "required"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(confirm_password)){
                binding.signUpConfirmPasswordEdt.error= "required"
                return@setOnClickListener
            }


            if (!password.equals(confirm_password)){
                CommonFunction.successToast(view.context,"Password must be same")
            }

            val teacher = Teacher(name, email,confirm_password,teacher_id,"","Teacher","","","")
            authViewModel.createUserAccount(teacher)

        }



        authViewModel.authState.observe(viewLifecycleOwner, Observer { response->

            when(response){
                is  Resource.Success->{
                    hideLoading(view.context)
                    response.data?.let { result ->
                        CommonFunction.successToast(view.context,"Account created successfully")
                    }
                }
                is Resource.Error->{
                    hideLoading(view.context)
                    response.message?.let {message->

                        Log.e(TAG,"An error occured $message")
                    }
                }

                is  Resource.Loading->{
                    showLoading(view.context)
                }

            }
        })


        authViewModel.savedUserData.observe(viewLifecycleOwner, Observer { response->

            when(response){
                is  Resource.Success->{
                    hideLoading(view.context)
                    response.data?.let { result ->

                        findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                        CommonFunction.successToast(view.context,"Saved user data successfully")
                    }
                }
                is Resource.Error->{
                    hideLoading(view.context)
                    response.message?.let {message->

                        Log.e(TAG,"An error occured $message")
                    }
                }

                is  Resource.Loading->{
                    showLoading(view.context)
                }

            }
        })

    }


    fun hideLoading(context: Context){
        binding.signUpProgressBar.visibility= View.GONE
    }

    fun showLoading(context: Context){
        binding.signUpProgressBar.visibility= View.VISIBLE
    }




}