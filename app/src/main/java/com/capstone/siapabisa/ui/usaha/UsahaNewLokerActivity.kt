package com.capstone.siapabisa.ui.usaha

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.capstone.siapabisa.R
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.local.LoginPreferences
import com.capstone.siapabisa.data.remote.model.enumJenis
import com.capstone.siapabisa.data.remote.model.enumPendidikan
import com.capstone.siapabisa.data.remote.model.enumPengalaman
import com.capstone.siapabisa.databinding.ActivityUsahaNewLokerBinding
import com.capstone.siapabisa.di.ViewModelFactory
import com.capstone.siapabisa.ui.usaha.viewmodel.NewLokerViewModel
import com.capstone.siapabisa.util.reduceFileImage
import com.capstone.siapabisa.util.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UsahaNewLoker : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityUsahaNewLokerBinding
    private lateinit var factory: ViewModelFactory

    private val viewModel: NewLokerViewModel by viewModels {factory}

    private var selectedPendidikan = ""
    private var selectedPengalaman = ""
    private var selectedJenis = ""

    private var usahaId = ""
    private var namaPerusahaan = ""
    private var alamatPerusahaan = ""

    private var getFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsahaNewLokerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        binding.btnSubmit.isEnabled = false

        setupSpinners()
        checkProfil()

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

        binding.btnSubmit.setOnClickListener(){
            uploadImage()
            binding.progressBar.visibility = View.VISIBLE
            viewModel._responsePostLoker.observe(this){response ->
                when(response){
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Berhasil menambahkan lowongan kerja", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, response.errorMessage, Toast.LENGTH_SHORT).show()
                        Log.d("UPLOAD", "UPLOAD IMAGE: ${response.errorMessage}")
                    }
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(this, "Uploading", Toast.LENGTH_SHORT).show()
                    }
                }

            }
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

    private fun checkProfil(){
        val preferences = LoginPreferences(this)
        val userId = preferences.getUserId()
        usahaId = userId.toString()


        Log.d("userId", userId.toString()   )

        if (userId != null) {
            viewModel.getProfilUsaha(userId)
            viewModel.responseProfilUsaha.observe(this){profil -> when(profil){
                is Result.Success->{
                    namaPerusahaan = profil.data.data?.namaUsaha.toString()
                    alamatPerusahaan = profil.data.data?.alamat.toString()
                    Toast.makeText(this, "Selamat Datang", Toast.LENGTH_SHORT).show()
                }
                is Result.Error->{
                    Toast.makeText(this, "Anda belum mengisi profil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, UsahaBiodataActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                    finish()
                }
                is Result.Loading->{

                }
            }
            }
        }
    }

    private fun uploadImage() {
        val prefLogin = LoginPreferences(this)
        val userId = prefLogin.getUserId()

        val name = namaPerusahaan
        val alamat = alamatPerusahaan
        val deskripsi = binding.etLokerDeskripsi.text?.trim().toString()
        val lowongan = binding.etLokerRole.text?.trim().toString()
        val jenisLowongan = selectedJenis
        val pendidikan = selectedPendidikan
        val pengalaman = selectedPengalaman


        val file = reduceFileImage(getFile as File)
        val userIdBody = userId?.toRequestBody("text/plain".toMediaType())
        val deskripsiBody = deskripsi.toRequestBody("text/plain".toMediaType())
        val nameBody = name.toRequestBody("text/plain".toMediaType())
        val alamatBody = alamat.toRequestBody("text/plain".toMediaType())
        val lowonganBody = lowongan.toRequestBody("text/plain".toMediaType())
        val jenisLowonganBody = jenisLowongan.toRequestBody("text/plain".toMediaType())
        val pendidikanBody = pendidikan.toRequestBody("text/plain".toMediaType())
        val pengalamanBody = pengalaman.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "image",
            file.name,
            requestImageFile
        )


        if (userIdBody != null) {
            viewModel.postLoker(userIdBody,nameBody,lowonganBody,jenisLowonganBody,pendidikanBody,pengalamanBody,alamatBody,deskripsiBody,imageMultipart)
        }

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