package upm.grodriguez.reaility.presentation.sign_in.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import upm.grodriguez.reaility.components.EmailField
import upm.grodriguez.reaility.components.PasswordField
import upm.grodriguez.reaility.components.SmallSpacer
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.ui.theme.ReailityTheme

@Composable
@ExperimentalComposeUiApi
fun SignInContent(
    padding: PaddingValues,
    signIn: (email: String, password: String) -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(padding))
    {
        val configuration = LocalConfiguration.current
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
                {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                        fontSize = 40.sp
                    )
                    SmallSpacer()
                    SignInForm(
                        signIn = signIn,
                        navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
                        navigateToSignUpScreen = navigateToSignUpScreen)
                }
            }
            else -> {
                Image(
                    painter = painterResource(id = R.drawable.cover),
                    contentDescription = null
                )
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingValues(0.dp, 0.dp, 0.dp, 0.dp))
                    .offset(y = (380).dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SignInForm(
                        signIn = signIn,
                        navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
                        navigateToSignUpScreen = navigateToSignUpScreen)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable()
fun SignInForm(
    signIn: (email: String, password: String) -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit
)
{
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue("")) }
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue("")) }
    val keyboard = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(PaddingValues(20.dp, 40.dp, 20.dp, 0.dp)),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            text = stringResource(R.string.welcome_message),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        SmallSpacer()
        EmailField(
            email = email,
            onEmailValueChange = { newValue ->
                email = newValue
            },
            modifier = Modifier.fillMaxWidth()
        )
        SmallSpacer()
        PasswordField(
            password = password,
            onPasswordValueChange = { newValue ->
                password = newValue
            },
            modifier = Modifier.fillMaxWidth()
        )
        SmallSpacer()
        Text(
            modifier = Modifier.clickable {
                navigateToForgotPasswordScreen()
            },
            text = stringResource(R.string.forgot_password),
            fontSize = 15.sp
        )
        SmallSpacer()
        Button(
            onClick = {
                keyboard?.hide()
                signIn(email.text, password.text)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                fontSize = 15.sp
            )
        }
        Button(
            onClick = navigateToSignUpScreen,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.no_account),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
@ExperimentalComposeUiApi
fun SignInContentPreview()
{
    ReailityTheme {
        SignInContent(
            padding = PaddingValues(0.dp),
            signIn = { _, _ -> },
            navigateToForgotPasswordScreen = {},
            navigateToSignUpScreen = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.ORIENTATION_LANDSCAPE)
@Composable
@ExperimentalComposeUiApi
fun SignInContentPreviewLandscape()
{
    ReailityTheme {
        SignInContent(
            padding = PaddingValues(0.dp),
            signIn = { _, _ -> },
            navigateToForgotPasswordScreen = {},
            navigateToSignUpScreen = {}
        )
    }
}
