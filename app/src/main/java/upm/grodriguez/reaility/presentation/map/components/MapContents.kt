package upm.grodriguez.reaility.presentation.map.components

import android.location.Location
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.ReailityApp
import upm.grodriguez.reaility.dto.Creation
import upm.grodriguez.reaility.dto.CreationWithAuthorName
import upm.grodriguez.reaility.ui.theme.ReailityTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapContents(
    locPermissionEnabled: Boolean = false,
    addNewCreation: () -> Unit = {},
    clickProfilePictureCallback: () -> Unit = {},
    signOut: () -> Unit = {},
    currentLocation: Location? = null,
    creations: List<CreationWithAuthorName> = emptyList(),
    onMapLoadedCallback: () -> Unit = {},
    navigateToCreationCallback: (String) -> Unit = {},
    mapStyle: String = ""
)
{
    val startPoint = LatLng(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPoint, 10f)
    }
    cameraPositionState.position = CameraPosition.fromLatLngZoom(startPoint, 15f)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = addNewCreation,
                shape = RoundedCornerShape(10.dp),
                backgroundColor = MaterialTheme.colorScheme.background
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.camara),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {contentPadding ->
        Box (modifier = Modifier.padding(contentPadding))
        {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                    onMapLoadedCallback()
                },
                properties = MapProperties(
                    isMyLocationEnabled = locPermissionEnabled,
                    mapStyleOptions = MapStyleOptions(mapStyle)
                ),
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = true,
                    compassEnabled = true
                ),
                contentPadding = PaddingValues(top = 100.dp),
            ) {
                creations.forEach{ creation ->
                    CreationInfoWindow(
                        creation = creation,
                        onClickCallback = navigateToCreationCallback)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                ProfileClickableWrapper(
                    clickProfilePictureCallback = clickProfilePictureCallback,
                    signOut = signOut
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapContentsPreview()
{
    ReailityTheme() {
        MapContents()
    }
}