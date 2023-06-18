package upm.grodriguez.reaility.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.ui.theme.ReailityTheme

@Composable
fun ProfileContent(
    padding: PaddingValues,
    username: String = "",
    userid: String = ""
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(PaddingValues(20.dp, 20.dp, 20.dp, 0.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.user_name) + ": ",
                fontStyle = MaterialTheme.typography.labelMedium.fontStyle
            )
            Text(
                text = username,
                fontStyle = MaterialTheme.typography.labelSmall.fontStyle,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Row {
            Text(
                text = stringResource(id = R.string.user_id) + ": ",
                fontStyle = MaterialTheme.typography.labelMedium.fontStyle
            )
            Text(
                text = userid,
                fontStyle = MaterialTheme.typography.labelSmall.fontStyle,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview()
{
    ReailityTheme() {
        ProfileContent(
            padding = PaddingValues(0.dp)
        )
    }
}