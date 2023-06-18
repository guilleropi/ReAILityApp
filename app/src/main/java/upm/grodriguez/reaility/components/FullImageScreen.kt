package upm.grodriguez.reaility.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable


@Composable
fun FullImageScreen(
    imageURL: String?
)
{
    val zoomState = rememberZoomState()

    Surface(color = androidx.compose.ui.graphics.Color.Black)
    {
        if ((imageURL ?: "").isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = null
                )
                SmallSpacer()
                ProgressBar()
            }
        }
        else
        {
            AsyncImage(
                model = imageURL,
                contentDescription = "",
                modifier = Modifier
                    .zoomable(zoomState)
                    .fillMaxSize(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FullImageScreen()
{
    FullImageScreen(imageURL = "")
}