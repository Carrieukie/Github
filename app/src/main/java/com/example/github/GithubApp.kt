package com.example.github

import android.app.Application
import androidx.annotation.Nullable
import dagger.hilt.android.HiltAndroidApp
import org.jetbrains.annotations.NotNull
import timber.log.Timber

@HiltAndroidApp
class GithubApp : Application(){
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() = when {
        BuildConfig.DEBUG -> {
            Timber.plant(object : Timber.DebugTree() {
                @Nullable
                override fun createStackElementTag(@NotNull element: StackTraceElement): String? {
                    return super.createStackElementTag(element) + ":" + element.lineNumber
                }
            })
        }

        else -> {
            // plant nothing
        }
    }
}