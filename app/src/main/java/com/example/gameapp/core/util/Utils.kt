package com.example.gameapp.core.util

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections

suspend inline fun <T> safeApiCall(responseFunction: () -> T): T?{
    return try{
        responseFunction.invoke()//Or responseFunction()
    }catch (e: Exception){
        e.printStackTrace()
        null
    }
}

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}