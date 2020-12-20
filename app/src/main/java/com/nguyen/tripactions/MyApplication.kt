package com.nguyen.tripactions

import android.app.Application

class MyApplication : Application() {
    val appComponent = DaggerAppComponent.create()
}