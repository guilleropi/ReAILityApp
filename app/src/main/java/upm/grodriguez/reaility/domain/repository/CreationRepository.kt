package upm.grodriguez.reaility.domain.repository

import upm.grodriguez.reaility.dto.Creation
import upm.grodriguez.reaility.dto.CreationWithAuthorName

interface CreationRepository {
    suspend fun getCreations(): HashMap<String, CreationWithAuthorName>
    suspend fun getCreation(creationID: String): CreationWithAuthorName?
    suspend fun uploadCreation(creation: Creation) : String?
    suspend fun getUserDisplayName(uid: String): String?
}
