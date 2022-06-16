package org.androdevlinux.spectrum.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import info.guardianproject.netcipher.proxy.OrbotHelper

@HiltAndroidApp
class BaseApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        OrbotHelper.get(this).init()
    }
}