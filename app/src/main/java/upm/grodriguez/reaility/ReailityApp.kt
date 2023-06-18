package upm.grodriguez.reaility

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.HiltAndroidApp
import java.io.IOException
import java.time.format.DateTimeFormatter


@HiltAndroidApp
class ReailityApp : Application()
{
    init {
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: ReailityApp
            private set

        val applicationContext: Context get() { return INSTANCE.applicationContext }
        @RequiresApi(Build.VERSION_CODES.O)
        val formatter_date = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        @RequiresApi(Build.VERSION_CODES.O)
        val formatter_time = DateTimeFormatter.ofPattern("HH:mm:ss")
    }
}