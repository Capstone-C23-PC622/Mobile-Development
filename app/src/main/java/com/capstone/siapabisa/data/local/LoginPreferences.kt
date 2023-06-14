package com.capstone.siapabisa.data.local

import android.content.Context

class LoginPreferences(context: Context) {
    private val preference = context.getSharedPreferences("loginPref", Context.MODE_PRIVATE)

    fun setUserId(userid:String){
        val edit = preference.edit()
        edit.putString("userid",userid)
        edit.apply()
    }

    fun setUserRole(role:Int){
        val edit = preference.edit()
        edit.putInt("role",role)
        edit.apply()
    }

    fun getUserid(): String? {
        return preference.getString("userid", null)
    }

    fun getRole(): Int {
        return preference.getInt("role", 0)
    }

    fun setToken(token:String){
        val edit = preference.edit()
        edit.putString("token",token)
        edit.apply()
    }

    fun getToken(): String? {
        return preference.getString("token", null)
    }

    fun clearData(){
        val edit = preference.edit().clear()
        edit.apply()
    }



}