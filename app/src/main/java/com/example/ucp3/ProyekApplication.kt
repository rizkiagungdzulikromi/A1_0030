package com.example.ucp3

import android.app.Application
import com.example.ucp3.Container.AppContainer
import com.example.ucp3.Container.KontakContainer


class ProyekApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = KontakContainer()
    }
}