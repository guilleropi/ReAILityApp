package upm.grodriguez.reaility.presentation.map

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import upm.grodriguez.reaility.domain.repository.AuthRepository
import upm.grodriguez.reaility.domain.repository.CreationRepository
import upm.grodriguez.reaility.domain.repository.LocationRepository
import upm.grodriguez.reaility.dto.Creation
import upm.grodriguez.reaility.dto.CreationWithAuthorName
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locRepo: LocationRepository,
    private val creationRepo: CreationRepository,
    private val auth: AuthRepository
) : ViewModel() {
    var locationPermissionGranted by mutableStateOf<Boolean>(false)
        private set

    var currentLocation: Location? by mutableStateOf(null)
        private set

    var creations by mutableStateOf(listOf<CreationWithAuthorName>())
        private set

    fun checkLocationPermissionGranted(context: Context) {
        locationPermissionGranted = (ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    fun updateCurrentLocation(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        currentLocation = locRepo.getCurrentLocation(context)
    }

    fun getCreations() = viewModelScope.launch(Dispatchers.Main) {
        creations = creationRepo.getCreations().values.toList()
    }

    fun askForLocationPermission(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), 1)
    }

    fun signOut() {
        auth.signOut()
    }

}
