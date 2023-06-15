package com.capstone.siapabisa.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.siapabisa.R
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.databinding.ActivitySearchBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.user.adapter.ListJobAdapter
import com.capstone.siapabisa.ui.user.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationBarView
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var adapter: ListJobAdapter

    private val viewModel: MainViewModel by viewModels {factory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)


        setupBottomNav()

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

            adapter.search(binding.etSearch.text.toString())
            binding.rvJoblist.adapter = adapter

        }


    }



    private fun setupBottomNav(){

        NavigationBarView.OnItemSelectedListener { item ->
            when(item.itemId) {
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
        adapter = ListJobAdapter(listJobs, this, object : ListJobAdapter.JobsListener {
            override fun onItemClicked(job: Job, ivJobPic: ImageView, tvName: TextView, tvDescription: TextView, tvDate : TextView) {
                val intent = Intent(this@SearchActivity, DetailLokerActivity::class.java)
                intent.putExtra("data", job)

                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@SearchActivity,
                    Pair(ivJobPic, "ivJobPic"),
                    Pair(tvName, "tvUsaha"),
                    Pair(tvDate, "tvPostedAt"),
                    Pair(tvDescription, "tvDescription")
                )
                startActivity(intent, optionsCompat.toBundle())


            }
        })
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvJoblist.adapter = adapter
        binding.rvJoblist.layoutManager = layoutManager
    }

    companion object{
        const val EXTRA_KEYWORD = "extra_keyword"
    }
}