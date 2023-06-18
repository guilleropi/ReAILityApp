package upm.grodriguez.reaility.presentation.new_creation

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import upm.grodriguez.reaility.domain.model.GeoCodeResponse
import upm.grodriguez.reaility.domain.repository.AuthRepository
import upm.grodriguez.reaility.domain.repository.CreationRepository
import upm.grodriguez.reaility.domain.repository.LocationRepository
import upm.grodriguez.reaility.domain.repository.OpenAIRepository
import upm.grodriguez.reaility.dto.Creation
import upm.grodriguez.reaility.networking.GeoCodeApiConfig
import javax.inject.Inject


@HiltViewModel
class NewCreationViewModel @Inject constructor(
    private val locRepo: LocationRepository,
    private val openAIrepo: OpenAIRepository,
    private val authRepo: AuthRepository,
    private val creationRepo: CreationRepository
): ViewModel() {

    var buttonEnabled by mutableStateOf(true)
    var loadingPrompt by mutableStateOf(false)
    var errorProcessingPrompt by mutableStateOf(false)
    var errorProcessingPromptMessage by mutableStateOf("")
    var errorSavingResult by mutableStateOf(false)
    var loadingSaveResult: Boolean by mutableStateOf(false)
    var currentLocation by mutableStateOf<Location?>(Location(""))
        private set
    var lastPromptResult by mutableStateOf("")
    var prompt by mutableStateOf<String>("")
    var currentLocationName by mutableStateOf(GeoCodeResponse())

    fun updateCurrentLocation(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        // Retrieve current lat lon
        try {
            val previousLatitude = currentLocation?.latitude
            val previousLongitude = currentLocation?.longitude
            currentLocation = locRepo.getCurrentLocation(context)

            // Generate inverse Geocoding info
            if ((currentLocation != null) && (previousLatitude != currentLocation!!.latitude) &&
                (previousLongitude != currentLocation!!.longitude))
            {
                val geoCodeClient = GeoCodeApiConfig.getApiService().getLocationInfo(
                    lat = currentLocation!!.latitude, lon = currentLocation!!.longitude)
                val response = geoCodeClient.execute()
                val data = response.body()
                if ((data != null) && response.isSuccessful)
                {
                    currentLocationName = data
                }
            }
        } catch (e: Exception) {
            Log.e("NewCreationViewModel", "Error retrieving location: ${e.message}")
        }
    }

    fun sendPrompt(promptCompleteCallback: () -> Unit)= viewModelScope.launch {
        loadingPrompt = true
        buttonEnabled = false
        errorProcessingPrompt = false

        val promptResult = openAIrepo.sendPrompt(prompt)
        if (promptResult.successs)
        {
            lastPromptResult = promptResult.resultURL
            promptCompleteCallback()
        }
        else
        {
            errorProcessingPrompt = true
            errorProcessingPromptMessage = promptResult.error
            Log.e("NewCreationViewModel", "Error processing prompt ${promptResult.error}")
        }

        loadingPrompt = false
        buttonEnabled = true
    }

    fun savePromptResult(navigateToCreationCallback: (creationID: String) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            errorSavingResult = false
            loadingSaveResult = true
            try {
                val creation = generateCreationObject()
                val creationID = creationRepo.uploadCreation(creation)
                if (creationID != null) {
                    loadingSaveResult = false
                    withContext(Dispatchers.Main) {
                        navigateToCreationCallback(creationID)
                    }
                }
                else
                {
                    errorSavingResult = true
                    loadingSaveResult = false
                }
            }
            catch (e: Exception)
            {
                errorSavingResult = true
                loadingSaveResult = false
                Log.e("NewCreationViewModel", "Error saving creation: ${e.message}")
            }
        }

    private fun generateCreationObject() : Creation {
        return Creation(
            prompt = prompt,
            resultURL = lastPromptResult,
            userID = authRepo.currentUser?.uid ?: "",
            locationInfo = currentLocationName
        )
    }
}