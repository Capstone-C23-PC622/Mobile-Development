package com.capstone.siapabisa.ui.usaha.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.databinding.ItemJobsBinding
import com.capstone.siapabisa.util.formatDate

class ListJobUsahaAdapter(private val jobs: List<Job>,
                     val context: Context,
                     private val listener:JobsListener) : RecyclerView.Adapter<ListJobUsahaAdapter.ViewHolder>() {

    private var originalJobs: List<Job> = jobs
    private var listJobs: List<Job> = ArrayList(jobs)

    fun filter(query: String) {
        val lowerCaseQuery = query.lowercase()
        listJobs = originalJobs.filter { job ->
            job.userId == query
        }

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJobsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val job = listJobs[position]
        val owner = job.userId

        holder.binding.apply {
            tvName.text = job.namaPerusahaan
            tvDescription.text = job.jenisLowongan
            tvLocation.text = job.lowongan
            tvDate.text = formatDate(job.createdAt!!)

            Glide.with(context)
                .load(job.image)
                .into(ivJobPic)
        }
        holder.itemView.setOnClickListener {
            listener.onItemClicked(job, holder.binding.ivJobPic, holder.binding.tvName, holder.binding.tvDescription, holder.binding.tvDate)


        }
    }
    override fun getItemCount() = listJobs.size

    class ViewHolder(val binding:ItemJobsBinding): RecyclerView.ViewHolder(binding.root)

    interface JobsListener{fun onItemClicked(job: Job, ivJobPic: ImageView, tvName: TextView, tvDescription: TextView, tvDate: TextView)}
}