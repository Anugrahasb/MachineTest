package com.example.machinetest.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkUtilReceiver(private var listener: NetworkListener? = null) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            listener?.onNetWorkStateChange(isNetworkAvailable(it))
        }
    }

    fun setListener(listener: NetworkListener?) {
        this.listener = listener
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectionManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    interface NetworkListener {
        fun onNetWorkStateChange(isNetworkAvailable: Boolean)
    }
}


object NetworkUtil {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
