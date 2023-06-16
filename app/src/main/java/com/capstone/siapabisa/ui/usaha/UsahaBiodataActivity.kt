package com.capstone.siapabisa.ui.usaha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.capstone.siapabisa.R
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.local.LoginPreferences
import com.capstone.siapabisa.data.remote.model.enumJenis
import com.capstone.siapabisa.data.remote.model.enumPendidikan
import com.capstone.siapabisa.databinding.ActivityUsahaBiodataBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.usaha.viewmodel.UsahaProfilViewModel
import com.capstone.siapabisa.ui.usaha.viewmodel.UsahaViewModel
import com.capstone.siapabisa.ui.user.UserActivity

class UsahaBiodataActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityUsahaBiodataBinding
    private lateinit var factory: ViewModelFactory

    private val viewModel: UsahaProfilViewModel by viewModels {factory}

    private var selectedBidang = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsahaBiodataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        binding.btnSubmit.isEnabled = false

        val preferences = LoginPreferences(this)
        val userId = preferences.getUserId().toString()

        val action = intent.getStringExtra("action")
        val biodataId = intent.getStringExtra("biodataId")

        addListener()
        setupSpinners()

        binding.btnSubmit.setOnClickListener {
            val name = binding.etProfileName.text?.trim().toString()
            val alamat = binding.etProfileAddress.text?.trim().toString()
            val bidang = selectedBidang
            val deskripsi = binding.etProfileDeskripsi.text?.trim().toString()

            binding.progressBar.visibility = View.VISIBLE

            if(action == "submit"){
                viewModel.submitProfilUsaha(userId, name, alamat, bidang, deskripsi)
                try{
                    submitProfil()
                }
                catch(e: Exception){

                    Toast.makeText(this, "Biodata Failed", Toast.LENGTH_SHORT).show()
                }

            }
            if(action == "edit"){
                viewModel.editProfilUsaha(biodataId!!, userId, name, alamat, bidang, deskripsi)
                try{
                    editProfil()
                }
                catch(e: Exception){

                    Toast.makeText(this, "Biodata Failed", Toast.LENGTH_SHORT).show()
                }


            }
            else{
                viewModel.submitProfilUsaha(userId, name, alamat, bidang, deskripsi)
                try{
                    submitProfil()
                }
                catch(e: Exception){

                    Toast.makeText(this, "Biodata Failed", Toast.LENGTH_SHORT).show()
                }
                Log.d("BiodataActivity", "-ACTION-: $action")
            }

        }

    }

    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {


        if (adapter?.getId() == R.id.spinner_bidang){
            val bidang = enumJenis[position]
            if (bidang != null) {
                selectedBidang = bidang
            }


        }


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun setupSpinners(){
        val bidangSpinner = binding.spinnerBidang
        bidangSpinner.onItemSelectedListener = this
        val bidangAdapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, enumJenis)
        bidangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bidangSpinner.adapter = bidangAdapter

    }

    private fun addListener(){
        binding.etProfileName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {

                checkValid()
            }

            override fun afterTextChanged(string: Editable?) {}
        })
        binding.etProfileAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {

                checkValid()
            }

            override fun afterTextChanged(string: Editable?) {}
        })
        binding.etProfileDeskripsi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {

                checkValid()
            }

            override fun afterTextChanged(string: Editable?) {}
        })

    }

    private fun checkValid(){
        val name = binding.etProfileName.text?.trim().toString()
        val alamat = binding.etProfileAddress.text?.trim().toString()
        val deskripsi = binding.etProfileDeskripsi.text?.trim().toString()
        val valid = name.isNotEmpty() && alamat.isNotEmpty() && deskripsi.isNotEmpty()
        binding.btnSubmit.isEnabled = valid
    }

    private fun submitProfil(){
        viewModel.responseSubmitProfile.observe(this){ submitProfil->
            when(submitProfil){
                is Result.Success->{
                    Toast.makeText(this,"Biodata Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, UsahaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    binding.progressBar.visibility = View.GONE
                    startActivity(intent)
                }
                is Result.Error->{
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Kesalahan ${submitProfil.errorMessage}" , Toast.LENGTH_SHORT).show()
                }
                is Result.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun editProfil(){
        viewModel.responseEditProfile.observe(this){ editProfil->
            when(editProfil){
                is Result.Success->{
                    Toast.makeText(this,"Biodata Berhasil Disimpan",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, UsahaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    binding.progressBar.visibility = View.GONE
                    startActivity(intent)
                }
                is Result.Error->{
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Kesalahan ${editProfil.errorMessage}" , Toast.LENGTH_SHORT).show()
                }
                is Result.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }


}