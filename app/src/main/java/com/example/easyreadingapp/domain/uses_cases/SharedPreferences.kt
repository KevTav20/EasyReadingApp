package com.example.easyreadingapp.domain.uses_cases

import android.content.Context

class SharedPref(
    context: Context
) {
    private val sharedPref = context.getSharedPreferences("myPref",Context.MODE_PRIVATE)

    fun saveUserSharedPref(
        userId : Int,
        isLogged : Boolean
    ){
        val editor = sharedPref.edit()
        editor.putInt("userId", userId)
        editor.putBoolean("isLogged",isLogged)
        editor.apply()
    }

    fun getUserIdSharedPref() : Int{
        return sharedPref.getInt("userId",0)
    }

    fun getIsLoggedSharedPref() : Boolean{
        return sharedPref.getBoolean("isLogged",false)
    }
    fun removeUserSharedPref(){
        val editor = sharedPref.edit()
        editor.remove("isLogged")
        editor.remove("userId")
        editor.apply()
    }
}