package com.capstone.siapabisa.ui.usaha

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.capstone.siapabisa.R
import com.capstone.siapabisa.data.remote.model.enumJenis
import com.capstone.siapabisa.data.remote.model.enumPendidikan
import com.capstone.siapabisa.data.remote.model.enumPengalaman
import com.capstone.siapabisa.databinding.ActivityUsahaNewLokerBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.util.uriToFile
import java.io.File

class UsahaNewLoker : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityUsahaNewLokerBinding
    private lateinit var factory: ViewModelFactory

    private var selectedPendidikan = ""
    private var selectedPengalaman = ""
    private var selectedJenis = ""

    private var getFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsahaNewLokerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        binding.btnSubmit.isEnabled = false

        setupSpinners()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnGallery.setOnClickListener(){
            startGallery()
        }

        binding.etLokerDeskripsi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etLokerRole.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun checkValid(){
        val role = binding.etLokerRole.text?.trim().toString()
        val description = binding.etLokerDeskripsi.text?.trim().toString()
        val file = getFile

        if (description.isNotEmpty() && file != null && role.isNotEmpty()){
            binding.btnSubmit.isEnabled = true}
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
        if (adapter?.getId() == R.id.spinner_jenis){
            val jenis = enumJenis[position]
            if (jenis != null) {
                selectedJenis = jenis
            }

        }


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun setupSpinners(){
        val pendidikanSpinner = binding.spinnerPendidikan
        pendidikanSpinner.onItemSelectedListener = this
        val pendidikanAdapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, enumPendidikan)
        pendidikanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pendidikanSpinner.adapter = pendidikanAdapter

        val pengalamanSpinner = binding.spinnerPengalaman
        pengalamanSpinner.onItemSelectedListener = this
        val pengalamanAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, enumPengalaman)
        pengalamanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pengalamanSpinner.adapter = pengalamanAdapter

        val jenisSpinner = binding.spinnerJenis
        jenisSpinner.onItemSelectedListener = this
        val jenisAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, enumJenis)
        jenisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jenisSpinner.adapter = jenisAdapter


    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this)
                getFile = myFile
                binding.ivPreview.setImageURI(uri)
                checkValid()
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}