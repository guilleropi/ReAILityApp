package upm.grodriguez.reaility.presentation.profile.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import upm.grodriguez.reaility.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import upm.grodriguez.reaility.components.ProgressBar
import upm.grodriguez.reaility.core.Utils.Companion.print
import upm.grodriguez.reaility.core.Utils.Companion.showMessage
import upm.grodriguez.reaility.domain.model.Response.*
import upm.grodriguez.reaility.presentation.profile.ProfileViewModel

@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel = hiltViewModel(),
    scaffoldState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    signOut: () -> Unit,
) {
    val context = LocalContext.current

    val message: String = stringResource(id = R.string.revoke_access_message)
    val label: String = stringResource(id = R.string.sign_out)
    fun showRevokeAccessMessage() = coroutineScope.launch {
        val result = scaffoldState.showSnackbar(
            message = message,
            actionLabel = label
        )
        if (result == SnackbarResult.ActionPerformed) {
            signOut()
        }
    }

    val sensitiveOpMsg = stringResource(id = R.string.sensitive_operation_message)
    val accessRevokedMsg = stringResource(id = R.string.access_revoked_message)
    when(val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Loading -> ProgressBar()
        is Success -> {
            val isAccessRevoked = revokeAccessResponse.data
            LaunchedEffect(isAccessRevoked) {
                if (isAccessRevoked) {
                    showMessage(context, accessRevokedMsg)
                }
            }
        }
        is Failure -> revokeAccessResponse.apply {
            LaunchedEffect(e) {
                print(e)
                if (e.message == sensitiveOpMsg) {
                    showRevokeAccessMessage()
                }
            }
        }
    }
}