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
import com.capstone.siapabisa.ui.auth.LoginRegister
import com.capstone.siapabisa.ui.usaha.viewmodel.UsahaViewModel
import com.capstone.siapabisa.ui.user.BiodataActivity
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



        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, LoginRegister::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
            finish()
        }
    }

    private fun setupBiodata(){
        val preferences = LoginPreferences(this)
        val userId = preferences.getUserId()
        val username = preferences.getUsername()
        val email = preferences.getUserEmail()

        if (userId != null) {
            viewModel.getProfilUsaha(userId)
            Log.d("userId", userId)
        }

        viewModel.responseProfilUsaha.observe(this){profil -> when(profil){
            is Result.Success -> {
                binding.tvName.text = profil.data.data?.namaUsaha
                binding.tvAddress.text = profil.data.data?.alamat
                binding.tvBidang.text = profil.data.data?.bidangUsaha
                binding.tvDeskripsi.text = profil.data.data?.deskripsiUsaha



                binding.tvUsername.text = username
                binding.tvEmail.text = email

                binding.btnProfile.setOnClickListener{
                    val intent = Intent(this, UsahaBiodataActivity::class.java)
                    intent.putExtra("action", "edit")
                    intent.putExtra("biodataId", profil.data.data?.id)
                    startActivity(intent)
                }


            }
            is Result.Loading -> {
                binding.progressBar.visibility = View.VISIBLE

            }
            is Result.Error -> {
                Toast.makeText(this, "anda belum mengisi profil", Toast.LENGTH_SHORT).show()
                Log.d("Error", profil.errorMessage.toString())

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val intent = Intent(this, UsahaBiodataActivity::class.java)
                intent.putExtra("action","submit")
                startActivity(intent)

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