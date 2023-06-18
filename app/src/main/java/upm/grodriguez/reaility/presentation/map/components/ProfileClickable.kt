package upm.grodriguez.reaility.presentation.map.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.ui.theme.ReailityTheme

@Composable
fun ProfileClickableWrapper(
    clickProfilePictureCallback: () -> Unit = {},
    signOut: () -> Unit = {}
)
{
    val isPressed = remember { mutableStateOf<Boolean>(false) }
    ProfileClickable(clickProfilePictureCallback, signOut, isPressed)
}

@Composable
fun ProfileClickable(
    clickProfilePictureCallback: () -> Unit = {},
    signOut: () -> Unit = {},
    isPressed: MutableState<Boolean> = mutableStateOf<Boolean>(false)
)
{
    Column (
        horizontalAlignment = Alignment.End
    )
    {
        val shape = if (isPressed.value)
        {
            RoundedCornerShape(10.dp, 10.dp, 10.dp, 0.dp)
        }
        else
        {
            RoundedCornerShape(10.dp)
        }
        FloatingActionButton(
            onClick = {
                isPressed.value = !isPressed.value
            },
            shape = shape,
            backgroundColor = MaterialTheme.colorScheme.background
        )
        {
            Image(
                painter = painterResource(id = R.drawable.usuario),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        if (isPressed.value)
        {
            Box(
                modifier = Modifier
                    .offset(x = -16.dp, y = -16.dp)
                    .padding(16.dp)
            )
            {
                Column (
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp, 0.dp, 16.dp, 16.dp))
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.End
                )
                {
                    Text(
                        text = stringResource(id = R.string.view_profile),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                clickProfilePictureCallback()
                            },
                        textAlign = TextAlign.Right,
                    )
                    Text(
                        text = stringResource(id = R.string.sign_out),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                signOut()
                            },
                        textAlign = TextAlign.Right
                    )
                }
            }
        }
    }
}

@Preview("Clicked")
@Composable
fun ProfileClickablePreviewClicked()
{
    val isPressed = remember { mutableStateOf<Boolean>(true) }
    ReailityTheme {
        ProfileClickable(isPressed = isPressed)
    }
}

@Preview("Collapsed")
@Composable
fun ProfileClickablePreview()
{
    ReailityTheme {
        ProfileClickable()
    }
}