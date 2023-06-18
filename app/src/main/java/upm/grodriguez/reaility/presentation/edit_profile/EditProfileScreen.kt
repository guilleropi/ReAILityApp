package upm.grodriguez.reaility.presentation.edit_profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.components.BackIcon
import upm.grodriguez.reaility.presentation.edit_profile.components.EditProfileContents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text=stringResource(id = R.string.edit_profile_screen)) },
                navigationIcon = {
                    BackIcon(
                        navigateBack = {
                            navigateBack()
                        }
                    )
                }
            )
        },
        content = { padding ->
            EditProfileContents(
                padding = padding,
                name = viewModel.displayName,
                onNameChange = { viewModel.displayName = it },
                errorSaving = viewModel.updateFail,
                successSaving = viewModel.updateSuccess,
                loadingSaving = viewModel.loading,
                discardChangesCallback = navigateBack,
                saveCallback = {
                    viewModel.updateUser(displayName = viewModel.displayName, null, null)
                }
            )
        },
    )
}
