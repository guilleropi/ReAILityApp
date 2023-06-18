package upm.grodriguez.reaility.presentation.map.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.components.DateTimeBlock
import upm.grodriguez.reaility.components.SmallSpacer
import upm.grodriguez.reaility.domain.model.GeoCodeResponse
import upm.grodriguez.reaility.dto.Creation
import upm.grodriguez.reaility.dto.CreationWithAuthorName


fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreationInfoWindow(
    creation: CreationWithAuthorName,
    onClickCallback: (String) -> Unit = {},
    onlyMarker: Boolean = false
)
{
    val icon = bitmapDescriptorFromVector(
        LocalContext.current, R.drawable.marker
    )
    MarkerInfoWindowContent(
        state = MarkerState(position = LatLng(
            creation.locationInfo?.lat?.toDouble() ?: 0.0,
            creation.locationInfo?.lon?.toDouble() ?: 0.0)
        ),
        icon = icon,
        onInfoWindowClick = { onClickCallback(creation.creationID) },
        onClick = { onlyMarker }
    ) { _ ->
        Column {
            Text(
                text = creation.prompt,
                style = MaterialTheme.typography.titleLarge)
            (if (creation.authorName == "") stringResource(R.string.author_name_unknown) else creation.authorName)?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            }
            SmallSpacer()
            DateTimeBlock(date = creation.timestamp, sameLine = true)
        }
    }
}