package upm.grodriguez.reaility.dto

import android.content.Context
import androidx.compose.ui.res.stringResource
import com.google.firebase.firestore.ServerTimestamp
import upm.grodriguez.reaility.R
import upm.grodriguez.reaility.core.Utils.Companion.trimString
import upm.grodriguez.reaility.domain.model.GeoCodeResponse
import java.util.Date

interface ICreationBase {
    var creationID: String
    var prompt: String
    var resultURL: String
    var timestamp: Date
    var userID: String
    var locationInfo: GeoCodeResponse?
}

interface ICreationAuthor : ICreationBase {
    var authorName: String?
}

data class Creation(
    override var creationID: String = "-1", // This is a placeholder value, the real ID will be assigned by Firebase
    override var prompt: String = "",
    override var resultURL: String = "",
    @ServerTimestamp override var timestamp: Date = Date(),
    override var userID: String = "",
    override var locationInfo: GeoCodeResponse? = null) : ICreationBase
{

    override fun toString(): String {
        return "\"${trimString(prompt, 30)}\" at ${trimString(locationInfo.toString(), 30)}"
    }
}

data class CreationWithAuthorName(
    override var authorName: String? = null,
    val creation: Creation
) : ICreationAuthor, ICreationBase by creation
{
    override fun toString(): String {
        return "\"${trimString(prompt, 30)}\" by ${trimString(authorName, 30)} at ${trimString(locationInfo.toString(), 30)}"
    }
}