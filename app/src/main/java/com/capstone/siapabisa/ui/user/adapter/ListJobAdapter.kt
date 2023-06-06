package com.capstone.siapabisa.ui.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.siapabisa.databinding.ItemJobsBinding
import com.capstone.siapabisa.dummy.Jobs

class ListJobAdapter(
    private val data: MutableList<Jobs> = mutableListOf()
) :
    RecyclerView.Adapter<ListJobAdapter.UserViewHolder>() {

    fun setData(data: List<Jobs>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val binding: ItemJobsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Jobs) {
            Glide.with(itemView.context)
                .load(job.imageUrl)
                .into(binding.ivJobPic)
            binding.tvName.text = job.namaPerusahaan
            binding.tvDescription.text = job.jenisLowongan
            binding.tvDate.text = job.datePosted
            binding.tvLocation.text = job.lokasi


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