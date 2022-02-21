package com.example.gasstations.presentation


import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest


class InternetConnection(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @SuppressLint("MissingPermission")
    fun listen(listener: ConnectionListener) {
        val networkInfo = connectivityManager.activeNetworkInfo
        val isConnected = networkInfo?.isConnected ?: false
        listener.updateConnected(isConnected)
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) = listener.updateConnected(false)
            override fun onAvailable(network: Network) = listener.updateConnected(true)
        }
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    interface ConnectionListener {

        fun updateConnected(connected: Boolean)
    }
}

