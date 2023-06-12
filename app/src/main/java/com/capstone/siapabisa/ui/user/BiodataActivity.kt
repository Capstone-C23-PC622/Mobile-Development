package com.capstone.siapabisa.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.capstone.siapabisa.R
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.local.LoginPreferences
import com.capstone.siapabisa.data.remote.model.Birthday
import com.capstone.siapabisa.data.remote.model.enumKeterampilan
import com.capstone.siapabisa.data.remote.model.enumPendidikan
import com.capstone.siapabisa.data.remote.model.enumPengalaman
import com.capstone.siapabisa.databinding.ActivityBiodataBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.user.viewmodel.BiodataViewModel
import com.capstone.siapabisa.util.checkUsername

class BiodataActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityBiodataBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: BiodataViewModel by viewModels {factory}

    private var selectedPendidikan = ""
    private var selectedPengalaman = ""
    private var selectedKeterampilan = ""
    private var selectedPeminatan = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiodataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        binding.btnSubmit.isEnabled = false

        setupSpinners()
        checkValid()

        binding.btnSubmit.setOnClickListener{
            val name = binding.etBiodataName.text?.trim().toString()
            val alamat = binding.etBiodataAddress.text?.trim().toString()
            val deskripsi = binding.etBiodataDeskripsi.text?.trim().toString()

            val birthday = Birthday(11,9,2001)

            binding.progressBar.visibility = View.VISIBLE
            viewModel.submitBiodata(name,birthday, alamat, deskripsi, selectedPendidikan, selectedPengalaman, selectedKeterampilan, selectedPeminatan)

            try{
                submitBiodata()
            }
            catch(e: Exception){

                Toast.makeText(this, "Biodata Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {


        if (adapter?.getId() == R.id.spinner_pendidikan){
            val pendidikan = enumPendidikan[position]
            if (pendidikan != null) {
                selectedPendidikan = pendidikan
            }


        }
        if (adapter?.getId() == R.id.spinner_pengalaman){
            val pengalaman = enumPengalaman[position]
            if (pengalaman != null) {
                selectedPengalaman = pengalaman
            }


        }
        if (adapter?.getId() == R.id.spinner_keterampilan){
            val keterampilan = enumKeterampilan[position]
            if (keterampilan != null) {
                selectedKeterampilan = keterampilan
            }


        }
        if (adapter?.getId() == R.id.spinner_peminatan){
            val peminatan = enumPengalaman[position]
            if (peminatan != null) {
                selectedPeminatan = peminatan
            }


        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
    private fun setupSpinners(){
        val pendidikanSpinner = binding.spinnerPendidikan
        pendidikanSpinner.onItemSelectedListener = this
        val pendidikanAdapter: ArrayAdapter<*> =ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, enumPendidikan)
        pendidikanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pendidikanSpinner.adapter = pendidikanAdapter

        val pengalamanSpinner = binding.spinnerPengalaman
        pengalamanSpinner.onItemSelectedListener = this
        val pengalamanAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, enumPengalaman)
        pengalamanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pengalamanSpinner.adapter = pengalamanAdapter

        val keterampilanSpinner = binding.spinnerKeterampilan
        keterampilanSpinner.onItemSelectedListener = this
        val keterampilanAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, enumKeterampilan)
        keterampilanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        keterampilanSpinner.adapter = keterampilanAdapter

        val peminatanSpinner = binding.spinnerPeminatan
        peminatanSpinner.onItemSelectedListener = this
        val peminatanAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, enumPengalaman)
        peminatanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        peminatanSpinner.adapter = peminatanAdapter

    }

    private fun checkValid(){

        binding.etBiodataName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                checkUsername(string?.trim().toString()).let {
                    binding.btnSubmit.isEnabled = it
                }
            }

            override fun afterTextChanged(string: Editable?) {}
        })

        binding.etBiodataAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                checkUsername(string?.trim().toString()).let {
                    binding.btnSubmit.isEnabled = it
                }
            }

            override fun afterTextChanged(string: Editable?) {}
        })

        binding.etBiodataDeskripsi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                checkUsername(string?.trim().toString()).let {
                    binding.btnSubmit.isEnabled = it
                }
            }

            override fun afterTextChanged(string: Editable?) {}
        })

    }

    private fun submitBiodata(){
        viewModel.responseSubmitBiodata.observe(this){ submitBiodata->
            when(submitBiodata){
                is Result.Success->{
                    Toast.makeText(this,"Biodata Berhasil Disimpan",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    binding.progressBar.visibility = View.GONE
                    startActivity(intent)
                }
                is Result.Error->{
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Kesalahan ${submitBiodata.errorMessage}" , Toast.LENGTH_SHORT).show()
                }
                is Result.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

}