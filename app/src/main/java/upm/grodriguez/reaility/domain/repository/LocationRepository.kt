package upm.grodriguez.reaility.domain.repository

import android.content.Context
import android.location.Location

interface LocationRepository {
    suspend fun getCurrentLocation(context: Context): Location?
}