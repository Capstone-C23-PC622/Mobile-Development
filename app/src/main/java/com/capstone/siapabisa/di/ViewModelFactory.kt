package com.capstone.siapabisa.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.ui.auth.viewmodel.LoginViewModel
import com.capstone.siapabisa.ui.auth.viewmodel.RegisterViewModel
import com.capstone.siapabisa.ui.usaha.viewmodel.NewLokerViewModel
import com.capstone.siapabisa.ui.usaha.viewmodel.UsahaMainViewModel
import com.capstone.siapabisa.ui.usaha.viewmodel.UsahaViewModel
import com.capstone.siapabisa.ui.user.viewmodel.BiodataViewModel
import com.capstone.siapabisa.ui.user.viewmodel.DetailViewModel
import com.capstone.siapabisa.ui.user.viewmodel.MainViewModel
import com.capstone.siapabisa.ui.user.viewmodel.UserViewModel

class ViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                return MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                return UserViewModel(repository) as T
            }
            modelClass.isAssignableFrom(BiodataViewModel::class.java) -> {
                return BiodataViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UsahaMainViewModel::class.java) -> {
                return UsahaMainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NewLokerViewModel::class.java) -> {
                return NewLokerViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UsahaViewModel::class.java) -> {
                return UsahaViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }

}