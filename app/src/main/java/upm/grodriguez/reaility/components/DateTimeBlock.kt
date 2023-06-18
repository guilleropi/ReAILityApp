package upm.grodriguez.reaility.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import upm.grodriguez.reaility.ReailityApp.Companion.formatter_date
import upm.grodriguez.reaility.ReailityApp.Companion.formatter_time
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimeBlock(
    date: Date,
    modifier: Modifier = Modifier,
    sameLine: Boolean = false,
)
{
    Row (
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier)
    {
    Icon(
        imageVector = Icons.Default.CalendarMonth,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primaryContainer)
    Spacer(modifier = Modifier.width(3.dp))
        if (sameLine)
        {
            Text(text = date.toInstant().atZone(ZoneId.systemDefault()).format(formatter_date) +
                    " ${date.toInstant().atZone(ZoneId.systemDefault()).format(formatter_time)}")
        }
        else
        {
            Text(text = date.toInstant().atZone(ZoneId.systemDefault()).format(formatter_date) +
                    "\n${date.toInstant().atZone(ZoneId.systemDefault()).format(formatter_time)}")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DateTimeBlockPreview()
{
    DateTimeBlock(date = Date())
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DateTimeBlockPreviewSameline()
{
    DateTimeBlock(date = Date(), sameLine = true)
}