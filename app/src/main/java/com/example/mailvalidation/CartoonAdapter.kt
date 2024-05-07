package com.example.mailvalidation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.mailvalidation.databinding.ListItemBinding

class CartoonAdapter(private var dataList: List<CartoonItem>) : RecyclerView.Adapter<CartoonAdapter.CartoonViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartoonViewHolder {
        var view =  ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartoonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CartoonViewHolder, position: Int) {
            holder.bind(dataList[position])
    }

    fun setvalue(it: MutableList<CartoonItem>?) {
        if (it != null) {
            dataList=it
            notifyDataSetChanged()
        }
    }

    inner class CartoonViewHolder(private  val binding:ListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(dataModel:CartoonItem){
            binding.title.text = dataModel.title
            binding.year.text = dataModel.year.toString()
        }
    }

}