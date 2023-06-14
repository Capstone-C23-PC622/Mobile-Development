package com.capstone.siapabisa.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.capstone.siapabisa.R
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.remote.model.DetailLoker
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.databinding.ActivityDetailLokerBinding
import com.capstone.siapabisa.databinding.ActivityMainBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.user.adapter.ListJobAdapter
import com.capstone.siapabisa.ui.user.viewmodel.DetailViewModel
import com.capstone.siapabisa.ui.user.viewmodel.MainViewModel

class DetailLokerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLokerBinding
    private lateinit var factory: ViewModelFactory

    private val viewModel: DetailViewModel by viewModels {factory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLokerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        setupBottomNav()

        val loker = intent?.getParcelableExtra<Job>("data")

        val lokerId = loker?.id

        if (lokerId != null) {
            viewModel.getJob(lokerId).observe(this){ job->
                when(job){
                    is Result.Success->{
                        showDetail(job.data.image,job.data.namaPerusahaan,job.data.deskripsi,job.data.lokasi,job.data.jenisLowongan, job.data.pendidikan, job.data.pengalaman, job.data.createdAt)
                        binding.progressBar.visibility = View.GONE
                    }
                    is Result.Error->{
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                    }
                    is Result.Loading->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }




    }

    private fun showDetail(url:String?,namaUsaha:String?,brief:String?,lokasi:String?,deskripsi:String?,pendidikan:String?,pengalaman:String?,posted:String?){
        Glide.with(this)
            .load(url)
            .into(binding.ivJobPic)
        binding.tvUsaha.text = namaUsaha
        binding.tvBrief.text = brief
        binding.tvLokasi.text = lokasi
        binding.tvDescription.text = deskripsi
        binding.tvPendidikan.text = pendidikan
        binding.tvPengalaman.text = pengalaman
        binding.tvPostedAt.text = posted
    }

    private fun setupBottomNav(){

        val bottomNav = binding.bottomNavigationView


        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

                R.id.saved -> {
                    startActivity(Intent(this, SavedActivity::class.java))
                    true
                }

                R.id.notification -> {
                    startActivity(Intent(this, NotificationActivity::class.java))
                    true
                }

                R.id.akun -> {
                    startActivity(Intent(this, UserActivity::class.java))
                    true
                }

                else -> false
            }
        }

    }


}