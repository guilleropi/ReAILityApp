package upm.grodriguez.reaility.presentation.verify_email

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.components.TopBar
import upm.grodriguez.reaility.core.Utils.Companion.showMessage
import upm.grodriguez.reaility.presentation.profile.ProfileViewModel
import upm.grodriguez.reaility.presentation.profile.components.RevokeAccess
import upm.grodriguez.reaility.presentation.verify_email.components.ReloadUser
import upm.grodriguez.reaility.presentation.verify_email.components.VerifyEmailContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyEmailScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToMapScreen: () -> Unit,
    navigateEditProfile: () -> Unit
) {
    val scaffoldState = remember {SnackbarHostState()}
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.verify_email_screen),
                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                },
                showNavigateBack = false,
                navigateBack = {},
                navigateEditProfile = navigateEditProfile
            )
        },
        content = { padding ->
            VerifyEmailContent(
                padding = padding,
                reloadUser = {
                    viewModel.reloadUser()
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = scaffoldState) }
    )

    val text: String = stringResource(id = R.string.email_not_verified_message)
    ReloadUser(
        navigateToProfileScreen = {
            if (viewModel.isEmailVerified) {
                navigateToMapScreen()
            } else {
                showMessage(context, text)
            }
        }
    )

    RevokeAccess(
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope,
        signOut = {
            viewModel.signOut()
        }
    )
}
