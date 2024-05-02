package com.example.coffeelove

import android.app.Application
import com.yandex.mapkit.MapKitFactory


class MainApplication : Application() {
    /**
     * Replace "your_api_key" with a valid developer key.
     */
    private val MAPKIT_API_KEY = "95bd1741-014b-491c-b5db-083242656971"
    override fun onCreate() {
        super.onCreate()
        // Set the api key before calling initialize on MapKitFactory.
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
    }
}
