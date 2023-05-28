package com.capstone.siapabisa.data.local

import android.content.Context

class LoginPreferences(context: Context) {
    private val preference = context.getSharedPreferences("loginPref", Context.MODE_PRIVATE)

    fun setToken(token:String){
        val edit = preference.edit()
        edit.putString("token",token)
        edit.apply()
    }

    fun getToken(): String? {
        return preference.getString("token", null)
    }

    fun clearToken(){
        val edit = preference.edit().clear()
        edit.apply()
    }

}