package com.capstone.siapabisa.ui.usaha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.capstone.siapabisa.R
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.databinding.ActivityDetailLokerBinding
import com.capstone.siapabisa.databinding.ActivityDetailUsahaBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.user.MainActivity
import com.capstone.siapabisa.ui.user.NotificationActivity
import com.capstone.siapabisa.ui.user.SavedActivity
import com.capstone.siapabisa.ui.user.UserActivity
import com.capstone.siapabisa.ui.user.viewmodel.DetailViewModel

class DetailUsahaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUsahaBinding
    private lateinit var factory: ViewModelFactory

    private val viewModel: DetailViewModel by viewModels {factory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUsahaBinding.inflate(layoutInflater)
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
        bottomNav.selectedItemId = R.id.akun

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, UsahaMainActivity::class.java))
                    true
                }

                R.id.my_usaha -> {
                    startActivity(Intent(this, UsahaMainActivity::class.java))
                    true
                }

                R.id.notification -> {
                    startActivity(Intent(this, UsahaNotificationActivity::class.java))
                    true
                }

                R.id.akun -> {
                    startActivity(Intent(this, UsahaActivity::class.java))
                    true
                }

                else -> false
            }
        }

    }


}