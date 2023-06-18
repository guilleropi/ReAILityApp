package upm.grodriguez.reaility.presentation.edit_profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.components.EmailField
import upm.grodriguez.reaility.components.PasswordField
import upm.grodriguez.reaility.components.SmallSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileContents(
    padding: PaddingValues = PaddingValues(0.dp),
    successSaving: Boolean = false,
    errorSaving: Boolean = false,
    loadingSaving: Boolean = false,
    discardChangesCallback: () -> Unit = {},
    saveCallback: () -> Unit = {},
    onEmailChange: (TextFieldValue) -> Unit = {},
    onPasswordChange: (TextFieldValue) -> Unit = {},
    onNameChange: (String) -> Unit = {},
    email: TextFieldValue = TextFieldValue(""),
    password: TextFieldValue = TextFieldValue(""),
    name: String = ""
) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .weight(1f)) {
            /*SmallSpacer()
            EmailField(email = email, onEmailValueChange = onEmailChange, modifier = Modifier.fillMaxWidth())
            SmallSpacer()
            PasswordField(password = password, onPasswordValueChange = onPasswordChange, modifier = Modifier.fillMaxWidth())*/
            SmallSpacer()
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text(text = stringResource(R.string.name_label)) },
                modifier = Modifier.fillMaxWidth())
        }
        if (errorSaving)
        {
            Row(
                horizontalArrangement = Arrangement.End
            )
            {
                Icon(imageVector = Icons.Outlined.Error, contentDescription = "error",
                    tint = MaterialTheme.colorScheme.error)
                Text(text = stringResource(id = R.string.error_saving_profile),
                    modifier = Modifier.padding(start = 5.dp))
            }
        }
        else if (successSaving)
        {
            Row(
                horizontalArrangement = Arrangement.End
            )
            {
                Icon(imageVector = Icons.Outlined.Check, contentDescription = "success",
                    tint = Color.Green)
                Text(text = stringResource(id = R.string.profile_saved),
                    modifier = Modifier.padding(start = 5.dp))
            }
        }
        else if (loadingSaving)
        {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = discardChangesCallback,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                enabled = !loadingSaving
            )
            {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.dischard)
                )
            }
            Button(
                onClick = saveCallback,
                enabled = !loadingSaving,
                shape = RoundedCornerShape(10.dp)
            )
            {
                Icon(
                    imageVector = Icons.Outlined.Save,
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.save)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditProfileContentsPreview()
{
    EditProfileContents()
}


@Preview(showBackground = true)
@Composable
fun EditProfileContentsLoadingPreview()
{
    EditProfileContents(loadingSaving = true)
}


@Preview(showBackground = true)
@Composable
fun EditProfileContentsErrorPreview()
{
    EditProfileContents(errorSaving = true)
}


@Preview(showBackground = true)
@Composable
fun EditProfileContentsSavedPreview()
{
    EditProfileContents(successSaving = true)
}