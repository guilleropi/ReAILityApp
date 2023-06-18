package upm.grodriguez.reaility.presentation.sign_in

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import upm.grodriguez.reaility.core.Utils.Companion.showMessage
import upm.grodriguez.reaility.presentation.sign_in.components.SignIn
import upm.grodriguez.reaility.presentation.sign_in.components.SignInContent

@Composable
@ExperimentalComposeUiApi
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
) {
    val context = LocalContext.current

    SignInContent(
        padding = PaddingValues(0.dp),
        signIn = { email, password ->
            viewModel.signInWithEmailAndPassword(email, password)
        },
        navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
        navigateToSignUpScreen = navigateToSignUpScreen
    )

    SignIn(
        showErrorMessage = { errorMessage ->
            showMessage(context, errorMessage)
        }
    )
}