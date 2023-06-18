package upm.grodriguez.reaility.presentation.view_result

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import upm.grodriguez.reaility.components.FullImageScreen
import upm.grodriguez.reaility.presentation.new_creation.NewCreationViewModel
import upm.grodriguez.reaility.presentation.view_result.components.ViewResultContents

@Composable
fun ViewResultScreen(
    viewModel: NewCreationViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToCreationCallback: (String) -> Unit,
    imageFullScreen: Boolean = false,
    fullScreenCallback: () -> Unit
) {
    viewModel.updateCurrentLocation(context = LocalContext.current)
    // Disable loading from previous screen
    viewModel.loadingPrompt = false

    if (imageFullScreen)
    {
        FullImageScreen(viewModel.lastPromptResult)
    }
    else
    {
        ViewResultContents(
            prompt = viewModel.prompt,
            loading = viewModel.loadingSaveResult,
            errorSaving = viewModel.errorSavingResult,
            savePromptResult = {
                viewModel.savePromptResult(navigateToCreationCallback)
            },
            resultImageURL = viewModel.lastPromptResult,
            discardPromptResult = {
                viewModel.errorSavingResult = false
                navigateBack()
            },
            imageClickCallback = fullScreenCallback
        )
    }
}