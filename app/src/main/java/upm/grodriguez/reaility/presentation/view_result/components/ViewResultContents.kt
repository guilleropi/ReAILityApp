package upm.grodriguez.reaility.presentation.view_result.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.components.SmallSpacer
import upm.grodriguez.reaility.ui.theme.ReailityTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewResultContents(
    prompt: String? = "Test",
    savePromptResult: () -> Unit = {},
    discardPromptResult: () -> Unit = {},
    resultImageURL: String? = "",
    loading: Boolean = false,
    errorSaving: Boolean = false,
    imageClickCallback: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(20.dp))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    )
    {
        AsyncImage(
            model = resultImageURL,
            contentDescription = "prompt result",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    imageClickCallback()
                }
        )
        SmallSpacer()
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End)
        {
            TextField(
                value = prompt?: "",
                onValueChange = {},
                label =  { Text(text = stringResource(id = R.string.prompt)) },
                enabled = false,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
            SmallSpacer()
            if (errorSaving)
            {
                Row(
                    horizontalArrangement = Arrangement.End
                )
                {
                    Icon(imageVector = Icons.Outlined.Error, contentDescription = "error",
                        tint = MaterialTheme.colorScheme.error)
                    Text(text = stringResource(id = R.string.error_saving_result),
                        modifier = Modifier.padding(start = 5.dp))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = discardPromptResult,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                    enabled = !loading)
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
                    onClick = savePromptResult,
                    enabled = !loading,
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
            if (loading)
            {
                SmallSpacer()
                SmallSpacer()
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewResultPreview() {
    ReailityTheme {
        ViewResultContents(errorSaving = false)
    }
}

@Preview(showBackground = true)
@Composable
fun ViewResultPreviewError() {
    ReailityTheme {
        ViewResultContents(errorSaving = true)
    }
}

@Preview(showBackground = true)
@Composable
fun ViewResultPreviewLoading() {
    ReailityTheme {
        ViewResultContents(loading = true)
    }
}