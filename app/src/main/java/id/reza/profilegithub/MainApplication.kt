package id.reza.profilegithub

import android.content.Context
import androidx.multidex.MultiDexApplication
import id.reza.profilegithub.di.appRepoModule
import id.reza.profilegithub.di.mainModule
import id.reza.profilegithub.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        startKoin {

            androidContext(this@MainApplication)

            modules(
                networkModule,
                appRepoModule,
                mainModule
            )
        }

    }

    companion object {
        private lateinit var context: Context

        fun getContext(): Context {
            return context
        }

        fun getString(resId: Int): String {
            return context.getString(resId)
        }

        fun getString(resId: Int, vararg format: Any?): String {
            return context.getString(resId, *format)
        }
    }
}