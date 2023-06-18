package upm.grodriguez.reaility.presentation.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.components.TopBar
import upm.grodriguez.reaility.presentation.profile.components.ProfileContent
import upm.grodriguez.reaility.presentation.profile.components.RevokeAccess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateEditProfile: () -> Unit
) {
    val scaffoldState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.profile_screen),
                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                },
                navigateBack = navigateBack,
                navigateEditProfile = navigateEditProfile
            )
        },
        content = { padding ->
            ProfileContent(
                padding = padding,
                username = viewModel.username(),
                userid = viewModel.userId()
            )
        },
        snackbarHost = { SnackbarHost(hostState = scaffoldState) }
    )

    RevokeAccess(
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope,
        signOut = {
            viewModel.signOut()
        }
    )
}