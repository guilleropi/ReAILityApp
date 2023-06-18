package upm.grodriguez.reaility.presentation.creation_view

import android.util.Log
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.components.BackIcon
import upm.grodriguez.reaility.components.FullImageScreen
import upm.grodriguez.reaility.presentation.creation_view.components.CreationContents
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationScreen(
    viewModel: CreationViewModel = hiltViewModel(),
    creationID: String,
    navigateBack: () -> Unit,
    fullScreenImage: Boolean,
    fullScreenCallback: () -> Unit
)
{
    val context = LocalContext.current

    val scaffoldState = remember { SnackbarHostState() }

    var isFirstTime by remember { mutableStateOf(true) }

    var mapStyleJson by remember { mutableStateOf("") }

    if (isFirstTime) {
        viewModel.getCreation(creationID = creationID)

        mapStyleJson = try {
            context.assets.open("map/style.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.e("MapScreen", ioException.toString())
            ""
        }

        isFirstTime = false
    }

    if (fullScreenImage)
    {
        FullImageScreen(
            imageURL = viewModel.creation?.resultURL
        )
    }
    else
    {
        Scaffold(
            topBar = {
                TopAppBar (
                    title = {
                        Text(
                            text = stringResource(id = R.string.view_creation_screen)
                        )
                    },
                    navigationIcon = {
                        BackIcon(
                            navigateBack = {
                                navigateBack()
                            }
                        )
                    }
                )
            },
            content = { padding ->
                CreationContents(
                    padding = padding,
                    creation = viewModel.creation,
                    imageClickCallback = fullScreenCallback,
                    mapStyle = mapStyleJson,
                    loading = viewModel.loading,
                )
            },
            snackbarHost = { SnackbarHost(hostState = scaffoldState) }
        )
    }
}