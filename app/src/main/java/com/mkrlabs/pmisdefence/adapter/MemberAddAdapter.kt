package com.mkrlabs.pmisdefence.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mkrlabs.pmisdefence.databinding.UserItemBinding
import com.mkrlabs.pmisdefence.model.Project
import com.mkrlabs.pmisdefence.model.Student

class MemberAddAdapter : RecyclerView.Adapter<MemberAddAdapter.MemberAddViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAddViewHolder {

        return  MemberAddViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: MemberAddViewHolder, position: Int) {

        var student = differ.currentList.get(position)

        holder.itemView.apply {
            holder.binding.userItemNameTV.text = student.name
            holder.binding.userItemIdTV.text = student.id
        }

        holder.binding.userItemSelectionStatus.setOnCheckedChangeListener { compoundButton, b ->
            OnCheckStatusItemClickListener?.let { it(student,b,holder.binding.userItemSelectionStatus) }
        }

    }

    private  var OnCheckStatusItemClickListener :((Student,Boolean,CheckBox)->Unit)? = null

    fun  setOnCheckStatusItemClickListener (listener: (Student,Boolean,CheckBox)->Unit){
        OnCheckStatusItemClickListener = listener
    }




    private  val  differCallback =object: DiffUtil.ItemCallback<Student>(){

        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return  oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return  oldItem == newItem
        }
    }

    val differ  = AsyncListDiffer(this,differCallback)


    inner  class  MemberAddViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

}