package upm.grodriguez.reaility.presentation.forgot_password

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.core.Utils.Companion.showMessage
import upm.grodriguez.reaility.presentation.forgot_password.components.ForgotPassword
import upm.grodriguez.reaility.presentation.forgot_password.components.ForgotPasswordContent
import upm.grodriguez.reaility.presentation.forgot_password.components.ForgotPasswordTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ForgotPasswordTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            ForgotPasswordContent(
                padding = padding,
                sendPasswordResetEmail = { email ->
                    viewModel.sendPasswordResetEmail(email)
                }
            )
        }
    )

    val text: String = stringResource(id = R.string.reset_password_message)
    ForgotPassword(
        navigateBack = navigateBack,
        showResetPasswordMessage = {
            showMessage(context, text)
        },
        showErrorMessage = { errorMessage ->
            showMessage(context, errorMessage)
        }
    )
}