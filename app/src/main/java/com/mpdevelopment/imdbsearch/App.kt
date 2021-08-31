package com.mpdevelopment.imdbsearch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    init {
        //TODO ADD TIMBER REPORTING / CRASHLYTICS / RELEASE TIMBER TREE / ANALYTICS
        //Anything that should be initialized on start of the app
        //If things take long it can be used in conjunction with the new splash screen api
        //Which can postpone / delay it finishing to have some time for initialization
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}