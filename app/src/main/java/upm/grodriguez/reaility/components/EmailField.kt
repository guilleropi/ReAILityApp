package upm.grodriguez.reaility.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import upm.grodriguez.reaility.R
import kotlinx.coroutines.job
import upm.grodriguez.reaility.ui.theme.ReailityTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    email: TextFieldValue,
    onEmailValueChange: (newValue: TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = FocusRequester()

    OutlinedTextField(
        value = email,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon")
        },
        placeholder = { Text(text = stringResource(R.string.enter_your_e_mail)) },
        onValueChange = { newValue ->
            onEmailValueChange(newValue)
        },
        label = {
            Text(
                text = stringResource(id = R.string.email_label)
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        modifier = modifier.focusRequester(focusRequester)
    )

    LaunchedEffect(Unit) {
        coroutineContext.job.invokeOnCompletion {
            focusRequester.requestFocus()
        }
    }
}

@Preview
@Composable
fun EmailFieldPreview(){
    ReailityTheme() {
        EmailField(
            email = TextFieldValue(""),
            onEmailValueChange = {}
        )
    }
}