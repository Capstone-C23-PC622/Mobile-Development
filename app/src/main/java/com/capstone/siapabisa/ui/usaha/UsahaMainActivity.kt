package com.capstone.siapabisa.ui.usaha

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
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.siapabisa.R
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.local.LoginPreferences
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.databinding.ActivityUsahaBinding
import com.capstone.siapabisa.databinding.ActivityUsahaMainBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.usaha.adapter.ListJobUsahaAdapter
import com.capstone.siapabisa.ui.usaha.viewmodel.UsahaMainViewModel
import com.capstone.siapabisa.ui.user.DetailLokerActivity
import com.capstone.siapabisa.ui.user.adapter.ListJobAdapter
import com.capstone.siapabisa.ui.user.viewmodel.MainViewModel


class UsahaMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsahaMainBinding
    private lateinit var factory: ViewModelFactory

    private lateinit var adapter: ListJobUsahaAdapter
    private val viewModel: UsahaMainViewModel by viewModels {factory}

    private var _jobs: List<Job> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsahaMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        checkProfil()
        setupBottomNav()

        binding.btnAddLoker.setOnClickListener {
            startActivity(Intent(this, UsahaNewLoker::class.java))
        }

        val pref = LoginPreferences(this)
        val userId = pref.getUserId()

        viewModel.getAllJobs().observe(this){ jobs->
            when(jobs){
                is Result.Success->{
                    _jobs = jobs.data
                    setupListJobs(jobs.data)
                    if (userId != null) {
                        adapter.filter(userId)
                    }
                    adapter.notifyDataSetChanged()
                    checkIfEmpty()
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

    }

    override fun onResume() {
        super.onResume()
        val pref = LoginPreferences(this)
        val userId = pref.getUserId()
        viewModel.getAllJobs().observe(this){ jobs->
            when(jobs){
                is Result.Success->{
                    _jobs = jobs.data
                    setupListJobs(jobs.data)
                    if (userId != null) {
                        adapter.filter(userId)
                    }
                    adapter.notifyDataSetChanged()
                    checkIfEmpty()
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
    }

    private fun checkProfil(){
        val preferences = LoginPreferences(this)
        val userId = preferences.getUserId()

        Log.d("userId", userId.toString()   )

        if (userId != null) {
            viewModel.getProfilUsaha(userId)
            viewModel.responseProfilUsaha.observe(this){profil -> when(profil){
                is Result.Success->{
                    Toast.makeText(this, "Selamat Datang", Toast.LENGTH_SHORT).show()
                }
                is Result.Error->{
                    Toast.makeText(this, "Anda belum mengisi profil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, UsahaBiodataActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                    finish()
                }
                is Result.Loading->{

                }
            }
            }
        }
    }


    private fun setupBottomNav(){

        val bottomNav = binding.bottomNavigationView
        bottomNav.selectedItemId = R.id.home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
//                R.id.home -> {
//                    startActivity(Intent(this, UsahaActivity::class.java))
//                    true
//                }

                R.id.my_usaha -> {
                    startActivity(Intent(this, UsahaLokerActivity::class.java))
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

    private fun setupListJobs(listJobs: List<Job>){
        val listAdapter = ListJobUsahaAdapter(listJobs, this, object : ListJobUsahaAdapter.JobsListener {
            override fun onItemClicked(job: Job, ivJobPic: ImageView, tvName: TextView, tvDescription: TextView, tvDate : TextView) {
                val intent = Intent(this@UsahaMainActivity, DetailUsahaActivity::class.java)
                intent.putExtra("data", job)

                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@UsahaMainActivity,
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
        binding.rvJoblist.adapter = adapter
        binding.rvJoblist.layoutManager = layoutManager
    }

    private fun checkIfEmpty() {

        if(adapter.getItemCount()>0) {
            binding.rvJoblist.visibility = View.VISIBLE
            binding.view.visibility = View.GONE
            binding.tvNoData.visibility = View.GONE
            binding.btnAddLoker.visibility = View.GONE
        }
        if(adapter.getItemCount()==0) {
            binding.rvJoblist.visibility = View.GONE
            binding.view.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.VISIBLE
            binding.btnAddLoker.visibility = View.VISIBLE
        }
    }


}