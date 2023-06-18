package upm.grodriguez.reaility.presentation.verify_email.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import upm.grodriguez.reaility.components.SmallSpacer
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.ui.theme.ReailityTheme

@Composable
fun VerifyEmailContent(
    padding: PaddingValues,
    reloadUser: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(), onClick = {
                reloadUser()
            }) {
            Text(
                text = stringResource(R.string.already_verified),
                fontSize = 16.sp
            )
        }
        SmallSpacer()
        Text(
            text = stringResource(R.string.spam_email),
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VerifyEmailContentPreview() {
    ReailityTheme(useDarkTheme = false) {
        VerifyEmailContent(
            padding = PaddingValues(0.dp),
            reloadUser = {}
        )
    }
}
