package upm.grodriguez.reaility.data.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import upm.grodriguez.reaility.domain.repository.CreationRepository
import upm.grodriguez.reaility.dto.Creation
import upm.grodriguez.reaility.dto.CreationWithAuthorName
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CreationRepositoryImpl @Inject constructor(
)
    : CreationRepository
{

    private val db = Firebase.firestore
    private val storageRef = FirebaseStorage.getInstance().reference
    private val functions = Firebase.functions

    override suspend fun getCreations(): HashMap<String, CreationWithAuthorName>{
        val creations = HashMap<String, CreationWithAuthorName>()
        return try {
            db.collection("creations")
                .get().await().forEach { document ->
                    val creation = document.toObject(Creation::class.java)
                    val creationWithAuthor = CreationWithAuthorName(
                        authorName = getUserDisplayName(creation.userID),
                        creation = creation
                    )
                    creations[creation.creationID] = creationWithAuthor
                }
            creations
        } catch (e: Exception) {
            Log.e("CreationRepositoryImpl", "Error getting creations: ${e.message}")
            creations
        }
    }

    override suspend fun getCreation(creationID: String): CreationWithAuthorName? {
        val document = db.collection("creations").document(creationID)
        return try {
            val creation = document.get().await().toObject(Creation::class.java)
            val creationWithAuthor = creation?.let {
                CreationWithAuthorName(
                    authorName = getUserDisplayName(creation.userID),
                    creation = it
                )
            }
            creationWithAuthor
        } catch (e: Exception) {
            Log.e("CreationRepositoryImpl", "Error getting creation: ${e.message}")
            null
        }
    }

    override suspend fun uploadCreation(creation: Creation) : String? {
        val document = db.collection("creations").document()
        creation.creationID = document.id

        val url = URL(creation.resultURL)
        val imageData = url.readBytes()

        val imagesRef: StorageReference = storageRef.child("creations/${creation.creationID}.png")

        val uploadTaskOk: Boolean =
            try {
                val task = imagesRef.putBytes(imageData).await().task
                if (task.isSuccessful && (task.result.metadata?.path != null)) {
                    creation.resultURL = imagesRef.downloadUrl.await().toString()
                    true
                } else {
                    Log.e("NewCreationViewModel", "Error uploading creation image")
                    false
                }
            } catch (e: Exception) {
                Log.e("Location_error", "${e.message}")
                false
            }

        if (uploadTaskOk) {
            return try {
                document.set(creation).await()
                creation.creationID
            } catch (e: Exception) {
                Log.e("NewCreationViewModel", "Error saving creation: ${e.message}")
                null
            }
        }
        return null
    }

    override suspend fun getUserDisplayName(uid: String): String? {
        // Create the arguments to the callable function.
        val data = hashMapOf(
            "uid" to uid
        )

        return try  {
            val result = functions
                .getHttpsCallable("getUserDisplayName")
                .call(data).await()
            result.data as String
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Error obtaining displayName: ${e.toString()}")
            null
        }
    }
}
