package com.example.acalculator.ui.utils

import android.content.Context
import android.net.ConnectivityManager

class VerificarInternet {
    companion object {
        fun temInternet(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ligacaoActiva = cm.activeNetworkInfo
            return ligacaoActiva != null && ligacaoActiva.isConnected
        }
    }
}