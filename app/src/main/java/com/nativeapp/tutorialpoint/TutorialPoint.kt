package com.nativeapp.tutorialpoint

import android.app.Application
import com.nativeapp.tutorialpoint.viewmodels.ClassesViewModel
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TutorialPoint: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}