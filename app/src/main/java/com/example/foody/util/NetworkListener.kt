package com.example.foody.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

@ExperimentalCoroutinesApi
class NetworkListener : ConnectivityManager.NetworkCallback() {

    private val isNetworkAvailable = MutableStateFlow(false)

    // 단말기가 인터넷 연결이 되어있는지 확인
    fun checkNetworkAvailability(context: Context): MutableStateFlow<Boolean> {
        Timber.d("checkNetworkAvailability() is called")
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        /**
         * registerDefaultNetworkCallback()는 API LEVEL 24 이상부터 사용가능
         * */
        connectivityManager.registerDefaultNetworkCallback(this@NetworkListener)

        var isConnected = false
        connectivityManager.allNetworks.forEach { network ->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isConnected = true
                    return@forEach
                }
            }
        }
        isNetworkAvailable.value = isConnected
        return isNetworkAvailable
    }
    // Network 연결이 되어있을 때
    override fun onAvailable(network: Network) {
        Timber.d("onAvailable() is called")
        isNetworkAvailable.value = true
    }

    // Network 연결이 되어있지 않을 때
    override fun onLost(network: Network) {
        Timber.d("onLost() is called")
        isNetworkAvailable.value = false
    }

}