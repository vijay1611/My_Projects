package com.example.mailvalidation.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mailvalidation.CartoonItem
import com.example.mailvalidation.databinding.ListItemBinding
import com.example.mailvalidation.databinding.RetrofitListItemBinding

class UsersAdapter(private var dataList: ArrayList<DataX>) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        var view =  RetrofitListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UsersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun setvalue(it: ArrayList<DataX>?) {
        if (it != null) {
            dataList=it
            notifyDataSetChanged()
        }
    }

    inner class UsersViewHolder(private  val binding: RetrofitListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(dataModel: DataX){
            binding.id.text = dataModel.id.toString()
            binding.firstName.text = dataModel.first_name
            binding.lastName.text = dataModel.last_name
            binding.email.text = dataModel.email
        }
    }

}