package upm.grodriguez.reaility.presentation.new_creation.components

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.components.SmallSpacer
import upm.grodriguez.reaility.core.Utils.Companion.trimString
import upm.grodriguez.reaility.ui.theme.ReailityTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCreationContents(
    padding: PaddingValues = PaddingValues(0.dp),
    sendButtonCallback: () -> Unit = {},
    buttonEnabled: Boolean = true,
    currentLocation: String? = null,
    loading: Boolean = false,
    prompt: String = "",
    promptChanged: (String) -> Unit = {},
    errorProcessingPrompt: Boolean = false,
    errorProcessingPromptMessage: String = ""
) {
    val promptTextInputHeight = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE)
    {
        100.dp
    }
    else{
        250.dp
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(PaddingValues(start=20.dp, end=20.dp))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.prompt_description_text)
        )
        SmallSpacer()
        TextField(
            value = prompt,
            onValueChange = promptChanged,
            label = {  },
            modifier = Modifier
                .height(promptTextInputHeight)
                .fillMaxWidth()
        )
        SmallSpacer()
        Text(text = stringResource(R.string.prompt_description_text_2))
        SmallSpacer()
        Text(text = stringResource(R.string.current_location)
                    + " ${trimString(currentLocation, 50) 
            ?: stringResource(R.string.location_unknown)}")
        SmallSpacer()
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End)
        {
            Button(
                onClick = {
                    sendButtonCallback()
                },
                enabled = buttonEnabled && (currentLocation != null) && (prompt.length > 5)
            )
            {
                Icon(imageVector = Icons.Outlined.Send, contentDescription = null)
                Text(text = "Send")
            }
        }
        SmallSpacer()
        SmallSpacer()
        if (loading)
        {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        else if (errorProcessingPrompt)
        {
            Row(
                horizontalArrangement = Arrangement.End
            )
            {
                Icon(imageVector = Icons.Outlined.Error, contentDescription = "error",
                    tint = MaterialTheme.colorScheme.error)
                Text(text =
                "${stringResource(R.string.error_processing_prompt)}: $errorProcessingPromptMessage",
                    modifier = Modifier.padding(start = 5.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewCreationContentsPreview()
{
    ReailityTheme {
        NewCreationContents(padding = PaddingValues(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun NewCreationContentsPreviewLoading()
{
    ReailityTheme {
        NewCreationContents(padding = PaddingValues(20.dp), loading = true)
    }
}