package com.capstone.siapabisa.ui.user

import android.app.DatePickerDialog
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
import com.capstone.siapabisa.data.remote.model.Birthday
import com.capstone.siapabisa.data.remote.model.enumKeterampilan
import com.capstone.siapabisa.data.remote.model.enumPeminatan
import com.capstone.siapabisa.data.remote.model.enumPendidikan
import com.capstone.siapabisa.data.remote.model.enumPengalaman
import com.capstone.siapabisa.databinding.ActivityBiodataBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.user.viewmodel.BiodataViewModel
import com.capstone.siapabisa.util.checkUsername
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BiodataActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityBiodataBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: BiodataViewModel by viewModels {factory}

    private var selectedPendidikan = ""
    private var selectedPengalaman = ""
    private var selectedKeterampilan = ""
    private var selectedPeminatan = ""

    private var userId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiodataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        binding.btnSubmit.isEnabled = false

        setupSpinners()
        addListener()

        val action = intent.getStringExtra("action")
        val biodataId = intent.getStringExtra("biodataId")

        val preferences = LoginPreferences(this)
        val userId = preferences.getUserId().toString()

        binding.btnDatePicker.setOnClickListener{
            datePicker()
        }

        binding.btnSubmit.setOnClickListener{
            val name = binding.etBiodataName.text?.trim().toString()
            val alamat = binding.etBiodataAddress.text?.trim().toString()
            val deskripsi = binding.etBiodataDeskripsi.text?.trim().toString()
            val birthdate = binding.tvBirthdateField.text?.trim().toString()

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = dateFormat.parse(birthdate)

            val calendar = Calendar.getInstance()
            if (date != null) {
                calendar.time = date
            }

            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH) + 1 // Months are zero-based, so add 1 to get the actual month value
            val year = calendar.get(Calendar.YEAR)

            val birthday = Birthday(day,month,year)



            binding.progressBar.visibility = View.VISIBLE

            if(action == "submit"){
                viewModel.submitBiodata(userId, name,birthday, alamat, deskripsi, selectedPendidikan, selectedPengalaman, selectedKeterampilan, selectedPeminatan)
                try{
                    submitBiodata()
                }
                catch(e: Exception){

                    Toast.makeText(this, "Biodata Failed", Toast.LENGTH_SHORT).show()
                }

            }
            if(action == "edit"){
                viewModel.editBiodata(biodataId!!, name,birthday, alamat, deskripsi, selectedPendidikan, selectedPengalaman, selectedKeterampilan, selectedPeminatan)
                try{
                    editBiodata()
                }
                catch(e: Exception){

                    Toast.makeText(this, "Biodata Failed", Toast.LENGTH_SHORT).show()
                }


            }
            else{
                viewModel.submitBiodata(userId, name,birthday, alamat, deskripsi, selectedPendidikan, selectedPengalaman, selectedKeterampilan, selectedPeminatan)
                try{
                    submitBiodata()
                }
                catch(e: Exception){

                    Toast.makeText(this, "Biodata Failed", Toast.LENGTH_SHORT).show()
                }
                Log.d("BiodataActivity", "-ACTION-: $action")
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

    private fun datePicker(){
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)
                binding.tvBirthdateField.text = formattedDate
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
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
        val peminatanAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, enumPeminatan)
        peminatanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        peminatanSpinner.adapter = peminatanAdapter

    }

    private fun addListener(){

        binding.etBiodataName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {

                checkValid()
            }

            override fun afterTextChanged(string: Editable?) {}
        })

        binding.etBiodataAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                checkValid()
            }

            override fun afterTextChanged(string: Editable?) {}
        })

        binding.etBiodataDeskripsi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                checkValid()
            }

            override fun afterTextChanged(string: Editable?) {}
        })

    }

    private fun checkValid(){
        val name = binding.etBiodataName.text.toString()
        val birthday = binding.tvBirthdateField.text.toString()
        val alamat = binding.etBiodataAddress.text.toString()
        val deskripsi = binding.etBiodataDeskripsi.text.toString()

        if(name.isEmpty() || birthday.isEmpty() || alamat.isEmpty() || deskripsi.isEmpty()){
            binding.btnSubmit.isEnabled = false
        }
        else{
            binding.btnSubmit.isEnabled = true
        }
    }

    private fun submitBiodata(){
        viewModel.responseSubmitBiodata.observe(this){ submitBiodata->
            when(submitBiodata){
                is Result.Success->{
                    Toast.makeText(this,"Biodata Berhasil Disimpan",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, UserActivity::class.java)
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

    private fun editBiodata(){
        viewModel.responseEditBiodata.observe(this){ editBiodata->
            when(editBiodata){
                is Result.Success->{
                    Toast.makeText(this,"Biodata Berhasil Disimpan",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, UserActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    binding.progressBar.visibility = View.GONE
                    startActivity(intent)
                }
                is Result.Error->{
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Kesalahan ${editBiodata.errorMessage}" , Toast.LENGTH_SHORT).show()
                }
                is Result.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

}