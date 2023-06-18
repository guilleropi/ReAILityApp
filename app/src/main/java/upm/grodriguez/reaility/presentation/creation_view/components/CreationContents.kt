package upm.grodriguez.reaility.presentation.creation_view.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.components.DateTimeBlock
import upm.grodriguez.reaility.core.Utils.Companion.trimString
import upm.grodriguez.reaility.domain.model.GeoCodeResponse
import upm.grodriguez.reaility.dto.Creation
import upm.grodriguez.reaility.dto.CreationWithAuthorName
import upm.grodriguez.reaility.presentation.map.components.CreationInfoWindow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreationContents(
    padding: PaddingValues = PaddingValues(0.dp),
    creation: CreationWithAuthorName? = null,
    loading: Boolean = false,
    imageClickCallback: () -> Unit = {},
    mapStyle: String = ""
)
{
    val cameraPositionState = rememberCameraPositionState {}
    cameraPositionState.position = CameraPosition.fromLatLngZoom(
        LatLng(creation?.locationInfo?.lat?.toDouble() ?: 0.0,
            creation?.locationInfo?.lon?.toDouble()  ?: 0.0), 18f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(PaddingValues(20.dp, 0.dp, 20.dp, 0.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loading)
        {
            LinearProgressIndicator()
        }
        else if (creation != null)
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween)
            {
                AsyncImage(
                    model = creation.resultURL,
                    contentDescription = creation.prompt,
                    modifier = Modifier
                        .clickable { imageClickCallback() }
                        .shadow(4.dp, RoundedCornerShape(4.dp))
                        .clip(RoundedCornerShape(10.dp))
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = creation.prompt,
                        color = MaterialTheme.typography.headlineLarge.color,
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize)
                    Spacer(modifier = Modifier.height(10.dp))
                    (if (creation.authorName == "") stringResource(R.string.author_name_unknown) else creation.authorName)?.let {
                        Text(text = it,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                    )
                    {
                        DateTimeBlock(date = creation.timestamp, modifier = Modifier.weight(1.2f))
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primaryContainer)
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = trimString(creation.locationInfo?.display_name, 50)
                            ?: stringResource(R.string.location_unknown),
                            modifier = Modifier.weight(2f))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(14.dp)
                            ),
                    )
                    {
                        GoogleMap(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            cameraPositionState = cameraPositionState,
                            properties = MapProperties(
                                mapStyleOptions = MapStyleOptions(mapStyle)
                            ),
                        )
                        {
                            CreationInfoWindow(creation, onlyMarker = true)
                        }
                    }
                }
            }
        }
        else
        {
            Text(text = stringResource(R.string.no_creation_found_with_the_given_id))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CreationContentsPreviewLoading()
{
    CreationContents(
        loading = true
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CreationContentsPreviewNone()
{
    CreationContents(
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CreationContentsPreview()
{
    val loc = GeoCodeResponse(
        display_name = "Calle de la Princesa, 3, 28008 Madrid, Spain",
        lat = "40.4225661",
        lon = "-3.7149435",
    )
    CreationContents(
        creation = CreationWithAuthorName(
            creation = Creation(
                creationID = "123",
                prompt = "This is a prompt",
                userID = "clover",
                locationInfo = loc)
        )
    )
}