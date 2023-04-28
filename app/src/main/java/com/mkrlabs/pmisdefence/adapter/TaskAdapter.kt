package com.mkrlabs.pmisdefence.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mkrlabs.pmisdefence.databinding.TaskItemBinding
import com.mkrlabs.pmisdefence.databinding.TeamItemBinding
import com.mkrlabs.pmisdefence.model.Project
import com.mkrlabs.pmisdefence.model.TaskItem
import com.mkrlabs.pmisdefence.util.CommonFunction

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        return  TaskViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        var task = differ.currentList.get(position)

        holder.itemView.apply {
            holder.binding.tasDescriptionItemTV.text = task.description
            holder.binding.tasDescriptionItemStatusCB.isChecked = task.status

        }

        holder.binding.floatingMenuOnTaskItem.setOnClickListener {
            onTaskMenuItemClickListener?.invoke(it,task)

        }


    }

    private  var onTaskMenuItemClickListener :((View,TaskItem)->Unit)? = null

    fun setOnTaskMenuItemClickListener(listener : (View, TaskItem)->Unit){
        onTaskMenuItemClickListener = listener
    }



    private  val  differCallback =object: DiffUtil.ItemCallback<TaskItem>(){

        override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return  oldItem == newItem
        }
    }

    val differ  = AsyncListDiffer(this,differCallback)


    inner  class  TaskViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root)

}