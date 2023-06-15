package com.capstone.siapabisa.ui.usaha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.siapabisa.R
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.local.LoginPreferences
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.databinding.ActivityUsahaBinding
import com.capstone.siapabisa.databinding.ActivityUsahaLokerBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.usaha.adapter.ListJobUsahaAdapter
import com.capstone.siapabisa.ui.usaha.viewmodel.UsahaMainViewModel
import com.capstone.siapabisa.ui.user.viewmodel.MainViewModel

class UsahaLokerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsahaLokerBinding
    private lateinit var factory: ViewModelFactory

    private lateinit var adapter: ListJobUsahaAdapter
    private val viewModel: UsahaMainViewModel by viewModels {factory}

    private var _jobs: List<Job> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsahaLokerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
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

    private fun setupBottomNav(){

        val bottomNav = binding.bottomNavigationView
        bottomNav.selectedItemId = R.id.my_usaha

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, UsahaMainActivity::class.java))
                    true
                }

//                R.id.my_usaha -> {
//                    startActivity(Intent(this, UsahaMainActivity::class.java))
//                    true
//                }

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
                val intent = Intent(this@UsahaLokerActivity, UsahaNewLoker::class.java)
                intent.putExtra("action", "edit")

                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@UsahaLokerActivity,
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

