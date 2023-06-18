package upm.grodriguez.reaility.presentation.new_creation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.components.BackIcon
import upm.grodriguez.reaility.presentation.new_creation.components.NewCreationContents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCreationScreen(
    viewModel: NewCreationViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    viewPromptResult: () -> Unit
) {
    val scaffoldState = remember { SnackbarHostState() }

    var isFirstTime by remember { mutableStateOf(true) }

    if (isFirstTime) {
        viewModel.updateCurrentLocation(context = LocalContext.current)
        isFirstTime = false
    }

    Scaffold(
        topBar = {
            TopAppBar (
                title = {
                    Text(
                        text = stringResource(id = R.string.new_creation_screen)
                    )
                },
                navigationIcon = {
                    BackIcon(
                        navigateBack = {
                            navigateBack()
                            viewModel.buttonEnabled = true
                        }
                    )
                }
            )
        },
        content = { padding ->
            NewCreationContents(
                padding = padding,
                buttonEnabled = viewModel.buttonEnabled,
                sendButtonCallback = {
                    viewModel.sendPrompt(
                        promptCompleteCallback = viewPromptResult
                    )
                },
                currentLocation = viewModel.currentLocationName.display_name,
                loading = viewModel.loadingPrompt,
                prompt = viewModel.prompt,
                promptChanged = { viewModel.prompt = it },
                errorProcessingPrompt = viewModel.errorProcessingPrompt,
                errorProcessingPromptMessage = viewModel.errorProcessingPromptMessage,
            )
        },
        snackbarHost = { SnackbarHost(hostState = scaffoldState) }
    )
}