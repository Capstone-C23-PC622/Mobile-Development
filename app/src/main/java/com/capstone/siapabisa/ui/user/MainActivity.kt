package com.capstone.siapabisa.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivityMainBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.user.adapter.ListJobAdapter
import com.capstone.siapabisa.ui.user.viewmodel.MainViewModel
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.remote.model.Job
import androidx.core.util.Pair
import com.capstone.siapabisa.data.local.LoginPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var adapter: ListJobAdapter

    private val viewModel: MainViewModel by viewModels {factory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        checkBiodata()

        viewModel.getAllJobs().observe(this){ jobs->
            when(jobs){
                is Result.Success->{
                    setupListJobs(jobs.data)
                    binding.progressBar.visibility = View.GONE
                }
                is Result.Error->{
                    binding.progressBar.visibility = View.GONE
                }
                is Result.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

    binding.btnSearch.setOnClickListener {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

        setupBottomNav()

    }

    private fun setupBottomNav(){

        val bottomNav = binding.bottomNavigationView
        bottomNav.selectedItemId = R.id.home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
//                R.id.home -> {
//                    startActivity(Intent(this, MainActivity::class.java))
//                    true
//                }

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

    private fun setupListJobs(listJobs: List<Job>){
        val listAdapter = ListJobAdapter(listJobs, this, object : ListJobAdapter.JobsListener {
            override fun onItemClicked(job: Job, ivJobPic: ImageView, tvName: TextView, tvDescription: TextView, tvDate : TextView) {
                val intent = Intent(this@MainActivity, DetailLokerActivity::class.java)
                intent.putExtra("data", job)

                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@MainActivity,
                    Pair(ivJobPic, "ivJobPic"),
                    Pair(tvName, "tvUsaha"),
                    Pair(tvDate, "tvPostedAt"),
                    Pair(tvDescription, "tvDescription")
                )
                startActivity(intent, optionsCompat.toBundle())


            }
        })
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = listAdapter
        binding.rvRekomendasi.adapter = adapter
        binding.rvRekomendasi.layoutManager = layoutManager
    }

    private fun checkBiodata(){
        val preferences = LoginPreferences(this)
        val userId = preferences.getUserId()

        if (userId != null) {
            viewModel.getBiodata(userId)
            viewModel.responseBiodata.observe(this){biodata -> when(biodata){
                is Result.Success->{
                    Toast.makeText(this, "Selamat Datang", Toast.LENGTH_SHORT).show()
                }
                is Result.Error->{
                    Toast.makeText(this, "Anda belum mengisi biodata", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, BiodataActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                    finish()
                }
                is Result.Loading->{

                }
            }
        }
    }
}}