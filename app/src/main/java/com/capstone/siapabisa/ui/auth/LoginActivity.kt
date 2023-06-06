package com.capstone.siapabisa.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.capstone.siapabisa.databinding.ActivityLoginBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.auth.viewmodel.LoginViewModel
import com.capstone.siapabisa.util.checkPassword
import com.capstone.siapabisa.util.checkUsername
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.local.LoginPreferences
import com.capstone.siapabisa.ui.user.MainActivity
import com.capstone.siapabisa.ui.user.UserActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: LoginViewModel by viewModels {factory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        binding.btnLogin.isEnabled = false

        val loginPref = LoginPreferences(this)
        loginPref.clearToken()

        checkValid()

        binding.btnLogin.setOnClickListener{

            binding.progressBar.visibility = View.VISIBLE
            val username = binding.etLoginUsername.text?.trim().toString()
            val password = binding.etLoginPassword.text?.trim().toString()

            try{
                viewModel.login(username, password)
                login()
            }
            catch(e: Exception){

                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun login(){
        viewModel.responseLogin.observe(this){login->
            when(login){
                is Result.Success->{
                    val loginPref = LoginPreferences(this)
                    login.data.token?.let { loginPref.setToken(it) }
                    Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    binding.progressBar.visibility = View.GONE
                    startActivity(intent)
                }
                is Result.Error->{
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Login ${login.errorMessage}" , Toast.LENGTH_SHORT).show()
                }
                is Result.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun checkValid(){
        binding.etLoginUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                checkUsername(string?.trim().toString()).let {
                    binding.btnLogin.isEnabled = it
                }
            }

            override fun afterTextChanged(string: Editable?) {}
        })

        binding.etLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                checkPassword(string?.trim().toString()).let {
                    binding.btnLogin.isEnabled = it
                }
            }

            override fun afterTextChanged(string: Editable?) {}
        })
    }

}

