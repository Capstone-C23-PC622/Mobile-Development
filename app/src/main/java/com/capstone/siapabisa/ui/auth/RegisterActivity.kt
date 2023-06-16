package com.capstone.siapabisa.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.capstone.siapabisa.databinding.ActivityRegisterBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.auth.viewmodel.RegisterViewModel
import com.capstone.siapabisa.util.checkEmail
import com.capstone.siapabisa.util.checkPassword
import com.capstone.siapabisa.util.checkUsername
import com.capstone.siapabisa.data.Result

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: RegisterViewModel by viewModels {factory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        binding.btnRegisterUser.isEnabled = false
        binding.btnRegisterUsaha.isEnabled = false
        checkValid()

        binding.btnRegisterUser.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            val username = binding.etRegisterUsername.text?.trim().toString()
            val email = binding.etRegisterEmail.text?.trim().toString()
            val password = binding.etRegisterPassword.text?.trim().toString()

            try{
                viewModel.register(username, email, password, "1")
                register()
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }

        binding.btnRegisterUsaha.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            val username = binding.etRegisterUsername.text?.trim().toString()
            val email = binding.etRegisterEmail.text?.trim().toString()
            val password = binding.etRegisterPassword.text?.trim().toString()

            try{
                viewModel.register(username, email, password, "2")
                register()
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }

    }

    private fun register(){
        viewModel.responseRegister.observe(this){register->
            when(register){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Login ${register.errorMessage}" , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkValid(){
        binding.etRegisterUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                checkUsername(string?.trim().toString()).let {
                    binding.btnRegisterUser.isEnabled = it
                    binding.btnRegisterUsaha.isEnabled = it
                }
            }

            override fun afterTextChanged(string: Editable?) {}
        })

        binding.etRegisterEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                checkEmail(string?.trim().toString()).let {
                    binding.btnRegisterUser.isEnabled = it
                    binding.btnRegisterUsaha.isEnabled = it
                }
            }

            override fun afterTextChanged(string: Editable?) {}
        })
        binding.etRegisterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                checkPassword(string?.trim().toString()).let {
                    binding.btnRegisterUser.isEnabled = it
                    binding.btnRegisterUsaha.isEnabled = it
                }
            }

            override fun afterTextChanged(string: Editable?) {}
        })
    }
}