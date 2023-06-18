package upm.grodriguez.reaility.core

import android.content.Context
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import upm.grodriguez.reaility.core.Constants.TAG

class Utils {
    companion object {
        fun print(e: Exception) = Log.e(TAG, e.stackTraceToString())

        fun showMessage(
            context: Context,
            message: String?
        ) = makeText(context, message, LENGTH_LONG).show()

        fun trimString(string: String?, maxLength: Int = 50): String? {
            return if ((string?.length?: 0) > maxLength)
            {
                string?.take(maxLength) + "..."
            }
            else
            {
                string
            }
        }
    }
}