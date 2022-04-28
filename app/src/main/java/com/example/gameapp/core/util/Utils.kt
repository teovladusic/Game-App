package com.example.gameapp.core.util

import android.util.Log

suspend inline fun <T> safeApiCall(responseFunction: () -> T): T?{
    return try{
        responseFunction.invoke()//Or responseFunction()
    }catch (e: Exception){
        e.printStackTrace()
        null
    }
}