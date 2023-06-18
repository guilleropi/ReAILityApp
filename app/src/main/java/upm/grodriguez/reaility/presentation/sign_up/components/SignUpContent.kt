package upm.grodriguez.reaility.presentation.sign_up.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
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
fun SignUpContent(
    padding: PaddingValues,
    signUp: (email: String, password: String) -> Unit,
    navigateBack: () -> Unit
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue("")) }
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue("")) }
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(PaddingValues(20.dp, 20.dp, 20.dp, 0.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Button(
            onClick = {
                keyboard?.hide()
                signUp(email.text, password.text)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
                fontSize = 15.sp
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navigateBack()
            }
        )
        {
            Text(
                text = stringResource(id = R.string.already_user),
                fontSize = 15.sp)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun SignUpContentPreview()
{
    ReailityTheme() {
        SignUpContent(
            padding = PaddingValues(0.dp),
            signUp = { _, _ -> },
            navigateBack = { }
        )
    }
}