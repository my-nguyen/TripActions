package com.nguyen.tripactions.dependencies

import com.nguyen.tripactions.views.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}