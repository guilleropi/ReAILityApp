package upm.grodriguez.reaility.presentation.creation_view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import upm.grodriguez.reaility.domain.repository.AuthRepository
import upm.grodriguez.reaility.domain.repository.CreationRepository
import upm.grodriguez.reaility.dto.Creation
import upm.grodriguez.reaility.dto.CreationWithAuthorName
import javax.inject.Inject


@HiltViewModel
class CreationViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val repo: CreationRepository
) : ViewModel()
{
    var loading: Boolean by mutableStateOf(false)

    var creation: CreationWithAuthorName? by mutableStateOf(null)

    fun getCreation(creationID: String) = viewModelScope.launch {
        loading = true
        creation = repo.getCreation(creationID)!!
        loading = false
    }

    fun getCreatorAuthor() = viewModelScope.launch {

    }
}