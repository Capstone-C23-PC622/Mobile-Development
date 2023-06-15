package com.capstone.siapabisa.ui.usaha

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
import com.capstone.siapabisa.data.local.LoginPreferences
import com.capstone.siapabisa.databinding.ActivityMainBinding
import com.capstone.siapabisa.databinding.ActivityUsahaBinding
import com.capstone.siapabisa.databinding.ActivityUsahaNewLokerBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.auth.LoginActivity
import com.capstone.siapabisa.ui.usaha.viewmodel.UsahaViewModel
import com.capstone.siapabisa.ui.user.MainActivity
import com.capstone.siapabisa.ui.user.NotificationActivity
import com.capstone.siapabisa.ui.user.SavedActivity
import com.capstone.siapabisa.ui.user.UserActivity
import com.capstone.siapabisa.ui.user.viewmodel.UserViewModel

class UsahaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsahaBinding
    private lateinit var factory: ViewModelFactory

    private val viewModel: UsahaViewModel by viewModels {factory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsahaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        setupBottomNav()
        setupBiodata()

        binding.btnProfile.setOnClickListener{
            startActivity(Intent(this, UsahaBiodataActivity::class.java))
        }

        binding.btnLogout.setOnClickListener{
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupBiodata(){
        val preferences = LoginPreferences(this)
        val userId = preferences.getUserId()
        val username = preferences.getUsername()
        val email = preferences.getUserEmail()

        if (userId != null) {
            viewModel.getProfilUsaha(userId)
        }

        viewModel.responseProfilUsaha.observe(this){profil -> when(profil){
            is Result.Success -> {
                binding.tvName.text = profil.data.data?.namaUsaha
                binding.tvAddress.text = profil.data.data?.alamat
                binding.tvBidang.text = profil.data.data?.bidangUsaha
                binding.tvDeskripsi.text = profil.data.data?.deskripsiUsaha

                Glide.with(this)
                    .load(profil.data.data?.image)
                    .into(binding.ivProfile)

                binding.tvUsername.text = username
                binding.tvEmail.text = email


            }
            is Result.Loading -> {
                binding.progressBar.visibility = View.VISIBLE

            }
            is Result.Error -> {
                Toast.makeText(this, profil.errorMessage, Toast.LENGTH_SHORT).show()
                Log.d("Error", profil.errorMessage.toString())

            }
        } }


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

//                R.id.akun -> {
//                    startActivity(Intent(this, UsahaActivity::class.java))
//                    true
//                }

                else -> false
            }
        }

    }
}