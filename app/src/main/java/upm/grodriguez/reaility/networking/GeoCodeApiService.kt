package upm.grodriguez.reaility.networking

import retrofit2.Call
import retrofit2.http.*
import upm.grodriguez.reaility.domain.model.GeoCodeResponse


interface GeoCodeApiService {

    // Get current weather data
    @GET("reverse")
    fun getLocationInfo(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<GeoCodeResponse>
}