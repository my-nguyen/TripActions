package com.nguyen.tripactions.dependencies

import android.app.Application

class MyApplication : Application() {
    val appComponent = DaggerAppComponent.create()
}