package com.capstone.siapabisa.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.siapabisa.R
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.databinding.ActivitySavedBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.dummy.jobList
import com.capstone.siapabisa.ui.user.adapter.ListJobAdapter
import com.capstone.siapabisa.ui.user.adapter.ListMessageAdapter
import com.capstone.siapabisa.ui.user.viewmodel.MainViewModel

class SavedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var adapter: ListJobAdapter

    private val viewModel: MainViewModel by viewModels {factory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        viewModel.getAllJobs().observe(this){ jobs->
            when(jobs){
                is Result.Success->{
                    setupListJobs(jobs.data)
                    binding.progressBar.visibility = View.GONE
                }
                is Result.Error->{
                    Log.e("STORIES",jobs.errorMessage)
                    binding.progressBar.visibility = View.GONE
                }
                is Result.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        setupBottomNav()


    }

    private fun setupBottomNav(){

        val bottomNav = binding.bottomNavigationView
        bottomNav.selectedItemId = R.id.akun

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

                R.id.search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }

                R.id.notification -> {
                    startActivity(Intent(this, NotificationActivity::class.java))
                    true
                }

//                R.id.akun -> {
//                    startActivity(Intent(this, UserActivity::class.java))
//                    true
//                }

                else -> false
            }
        }

    }

    private fun setupListJobs(listJobs: List<Job>){
        val listAdapter = ListJobAdapter(listJobs, this, object : ListJobAdapter.JobsListener {
            override fun onItemClicked(story: Job, ivStory: ImageView, tvName: TextView, tvDescription: TextView, tvDate : TextView) {
//                val intent = Intent(this@MainActivity, DetailActivity::class.java)
//                intent.putExtra("data", story)

//                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    this@MainActivity,
//                    Pair(ivStory, "imageViewStory"),
//                    Pair(tvName, "nameStory"),
//                    Pair(tvDate, "dateStory"),
//                    Pair(tvDescription, "descStory")
//                )
//                startActivity(intent, optionsCompat.toBundle())
            }
        })
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvJoblist.adapter = listAdapter
        binding.rvJoblist.layoutManager = layoutManager
    }
}