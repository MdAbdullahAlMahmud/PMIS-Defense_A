package com.mkrlabs.pmisdefence.fragment

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.adapter.ProjectAdapter
import com.mkrlabs.pmisdefence.databinding.FragmentHomeBinding
import com.mkrlabs.pmisdefence.databinding.NavDesignBinding
import com.mkrlabs.pmisdefence.model.ChatItem
import com.mkrlabs.pmisdefence.model.ChatTYPE
import com.mkrlabs.pmisdefence.ui.HomeActivity
import com.mkrlabs.pmisdefence.util.CommonFunction
import com.mkrlabs.pmisdefence.util.NotificationService
import com.mkrlabs.pmisdefence.util.Resource
import com.mkrlabs.pmisdefence.util.SharedPref
import com.mkrlabs.pmisdefence.view_model.AuthenticationViewModel
import com.mkrlabs.pmisdefence.view_model.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    lateinit var mAuth: FirebaseAuth
    lateinit var projectViewModel: ProjectViewModel
    lateinit var authenticationViewModel: AuthenticationViewModel

    lateinit var projectAdapter: ProjectAdapter


    lateinit var navLayout: NavDesignBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        projectViewModel = ViewModelProvider(this)[ProjectViewModel::class.java]
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        mAuth = FirebaseAuth.getInstance()

        //navLayout = NavDesignBinding.inflate(LayoutInflater.from(context), null,false)

        binding.homeNotification.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(view.context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                val notificationService = NotificationService()

                val title = "Notification Title"
                val msg = "This is a demo description"
                val url = ""

                CommonFunction.infoToast(view.context,"Permission Not Granted ")
            }else{
                showNotification(view.context)

                CommonFunction.infoToast(view.context,"NO Need for permission")
            }
        }


            binding.addNewTeamHome.setOnClickListener {

                findNavController().navigate(R.id.action_homeFragment_to_projectInformationFragment)
            }


            CommonFunction.successToast(
                view.context,
                SharedPref(view.context).getLoggedInUserName()
            )

            projectViewModel.fetchProjectList()
            projectViewModel.projectList.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideLoading()
                        response.data?.let { projectAdapter.differ.submitList(it) }
                    }
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                        CommonFunction.successToast(view.context, response.message.toString())
                    }
                }
            })

            projectAdapter.setOnProjectItemClickListener { project ->
                val projectIntent = project
                val bundle = Bundle().apply { putSerializable("project", projectIntent) }
                findNavController().navigate(
                    R.id.action_homeFragment_to_projectDetailsFragment,
                    bundle
                )
            }

            projectAdapter.setOnAddTeamMemberItemClickListener {
                val bundle = Bundle().apply { putSerializable("project", it) }
                findNavController().navigate(
                    R.id.action_homeFragment_to_teacherAddMemberFragment,
                    bundle
                )
            }


            projectAdapter.setOnGroupMessageClickListener {
                var groupChatItem = ChatItem(
                    it.projectName, it.projectUID, it.projectDescription,
                    Date().time, it.teacher_id, "'",
                    ChatTYPE.GROUP
                )

                val bundle = Bundle().apply {
                    putSerializable("chatItem", groupChatItem)
                }
                findNavController().navigate(R.id.action_homeFragment_to_chatFragment, bundle)

            }

            binding.homeNavDrawerMenu.setOnClickListener {
                (requireActivity() as HomeActivity).openNavigationDrawer()
            }


        }


        fun hideLoading() {
            binding.teacherHomeProgressBar.visibility = View.GONE
        }

        fun showLoading() {
            binding.teacherHomeProgressBar.visibility = View.VISIBLE
        }

         fun setupRecycleView() {

            projectAdapter = ProjectAdapter()

            binding.projectRV.apply {
                adapter = projectAdapter
                layoutManager = LinearLayoutManager(activity)
            }

        }

    private fun showNotification(context: Context) {

        val channelId = "12345"
        val description = "Test Notification"

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.lightColor = Color.BLUE

            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

        }

        val  builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Hello World")
            .setContentText("Test Notification")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources, R.drawable
                        .ic_launcher_background
                )
            )
        notificationManager.notify(12345, builder.build())
    }
    }



