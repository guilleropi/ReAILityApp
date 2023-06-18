package upm.grodriguez.reaility.presentation.map

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.ReailityApp
import upm.grodriguez.reaility.presentation.map.components.MapContents
import java.io.IOException

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    navigateToNewCreation: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToCreation: (String) -> Unit)
{
    val context = LocalContext.current

    viewModel.checkLocationPermissionGranted(context)

    var isFirstTime by remember { mutableStateOf(true) }
    var mapStyleJson by remember { mutableStateOf("") }

    if (isFirstTime)
    {
        if (viewModel.locationPermissionGranted && viewModel.currentLocation == null)
        {
            viewModel.updateCurrentLocation(context = LocalContext.current)
        }
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

    val openDialog = remember { mutableStateOf(false) }

    MapContents(
        locPermissionEnabled = viewModel.locationPermissionGranted,
        addNewCreation = {
            viewModel.askForLocationPermission(context)
            if (viewModel.locationPermissionGranted)
            {
                navigateToNewCreation()
            }
            else
            {
                openDialog.value = true
            }
        },
        signOut = {
            viewModel.signOut()
            navigateToLogin()
        },
        clickProfilePictureCallback = navigateToProfile,
        currentLocation = viewModel.currentLocation,
        creations = viewModel.creations,
        onMapLoadedCallback = {
            viewModel.getCreations()
        },
        navigateToCreationCallback = navigateToCreation,
        mapStyle = mapStyleJson
    )
    if (openDialog.value && !viewModel.locationPermissionGranted)
    {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = stringResource(id = R.string.permissions_dialog_title)) },
            text = {
                Text(text = stringResource(id = R.string.permissions_dialog_text))
            },
            confirmButton = {},
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }
                )
                {
                    Text(text = stringResource(id = R.string.dismiss_ok))
                }
            }
        )
    }
}