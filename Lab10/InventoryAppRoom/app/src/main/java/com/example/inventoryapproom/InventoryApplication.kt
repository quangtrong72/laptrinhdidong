package com.example.inventoryapproom

import android.app.Application
import com.example.inventoryapproom.data.AppContainer
import com.example.inventoryapproom.data.AppDataContainer

class InventoryApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
