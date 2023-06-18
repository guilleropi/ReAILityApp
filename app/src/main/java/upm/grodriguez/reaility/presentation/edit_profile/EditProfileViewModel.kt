package upm.grodriguez.reaility.presentation.edit_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import upm.grodriguez.reaility.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repo: AuthRepository
    ) : ViewModel()
{
    var loading by mutableStateOf(false)
    var updateSuccess by mutableStateOf(false)
    var updateFail by mutableStateOf(false)

    var displayName by mutableStateOf(repo.currentUser?.displayName ?: "")

    fun updateUser(displayName: String, email: String?, password: String?) = viewModelScope.launch{
        loading = true
        updateSuccess = false
        updateFail = false
        if (repo.updateUser(name = displayName, email = email, password = password))
        {
            updateSuccess = true
        }
        else
        {
            updateFail = true
        }
        loading = false
    }
}