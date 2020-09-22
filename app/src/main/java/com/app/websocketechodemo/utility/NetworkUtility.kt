package com.app.websocketechodemo.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class NetworkUtility(context: Context, lifecycleOwner: LifecycleOwner, networkChanged: (networkAvailable: Boolean) -> Unit): LifecycleObserver {
    private var connectivityManager: ConnectivityManager

    /**
     * Callback for handling network connections
     */
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            networkChanged(true)
        }

        override fun onLost(network: Network) {
            networkChanged(false)
        }

        override fun onUnavailable() {
            networkChanged(false)
        }
    }

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    /**
     * Registers a callback to check for network status when belonging parents enters onStart() state
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun registerNetworkCheck() {
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            networkCallback
        )
    }

    /**
     * UnRegisters network callback when belonging parents enters onStop() state
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun unRegisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}

/**
 * Returns true if network is available, false otherwise
 */
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    if (null != activeNetwork) {
        if (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
            activeNetwork.type == ConnectivityManager.TYPE_MOBILE
        ) return true
    }
    return false
}