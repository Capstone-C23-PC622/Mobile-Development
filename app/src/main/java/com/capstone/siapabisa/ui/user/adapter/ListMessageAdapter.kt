package com.capstone.siapabisa.ui.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.siapabisa.databinding.ItemJobsBinding
import com.capstone.siapabisa.dummy.Messages

class ListMessageAdapter(
    private val data: MutableList<Messages> = mutableListOf()
) :
    RecyclerView.Adapter<ListMessageAdapter.UserViewHolder>() {

    fun setData(data: List<Messages>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val binding: ItemJobsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Messages) {
            Glide.with(itemView.context)
                .load(message.imageUrl)
                .into(binding.ivJobPic)
            binding.tvName.text = message.company
            binding.tvDescription.text = message.messages
            binding.tvDate.text = message.date


            binding.cardView.setOnClickListener {
//                 val intent = Intent(itemView.context, DetailActivity::class.java)
//                 intent.putExtra(DetailActivity.EXTRA_DATA, job)
//                 itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemJobsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size
}