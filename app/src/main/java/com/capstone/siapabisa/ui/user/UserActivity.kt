package com.capstone.siapabisa.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ActivityUserBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.user.viewmodel.UserViewModel
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.local.LoginPreferences
import com.capstone.siapabisa.ui.auth.LoginActivity
import com.capstone.siapabisa.ui.auth.LoginRegister
import com.capstone.siapabisa.ui.usaha.UsahaBiodataActivity
import com.capstone.siapabisa.ui.usaha.UsahaMainActivity

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: UserViewModel by viewModels {factory}

    private lateinit var biodataId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
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

        binding.btnBiodata.setOnClickListener{
            val intent = Intent(this, BiodataActivity::class.java)
            intent.putExtra("action", "edit")
            intent.putExtra("biodataId", biodataId)
            startActivity(intent)
        }


    }

    private fun setupBiodata(){
        val preferences = LoginPreferences(this)
        val userId = preferences.getUserId()
        val username = preferences.getUsername()
        val email = preferences.getUserEmail()


        if (userId != null) {
            viewModel.getBiodata(userId)
        }

        viewModel.responseBiodata.observe(this){biodata -> when(biodata){
            is Result.Success -> {
                binding.tvName.text = biodata.data.biodata?.nama
                binding.tvAddress.text = biodata.data.biodata?.alamat
                binding.tvPendidikan.text = biodata.data.biodata?.pendidikan
                binding.tvPengalaman.text = biodata.data.biodata?.pengalaman
                binding.tvKeterampilan.text = biodata.data.biodata?.keterampilan
                binding.tvPeminatan.text = biodata.data.biodata?.peminatan
                binding.tvDeskripsi.text = biodata.data.biodata?.deskripsiDiri

                binding.tvUsername.text = username
                binding.tvEmail.text = email

                biodataId = biodata.data.biodata?.id.toString()




            }
            is Result.Loading -> {
                binding.progressBar.visibility = VISIBLE

            }
            is Result.Error -> {
                Toast.makeText(this, "anda belum mengisi biodata", Toast.LENGTH_SHORT).show()
                Log.d("Error", biodata.errorMessage.toString())
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val intent = Intent(this, BiodataActivity::class.java)
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
}