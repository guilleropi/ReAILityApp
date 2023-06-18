package upm.grodriguez.reaility.data.repository

import android.util.Log
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.image.ImageCreation
import com.aallam.openai.api.image.ImageSize
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.delay
import upm.grodriguez.reaility.domain.repository.OpenAIAnswer
import upm.grodriguez.reaility.domain.repository.OpenAIRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class OpenAIRepositoryImpl @Inject constructor(
    private val openAIAPI : OpenAI
) : OpenAIRepository {


    @OptIn(BetaOpenAI::class)
    override suspend fun sendPrompt(prompt: String): OpenAIAnswer {
        return try {
            val images = openAIAPI.imageURL(creation = ImageCreation(
                prompt = prompt,
                n = 1,
                size = ImageSize.is1024x1024)
            )
            OpenAIAnswer(images[0].url as String,
                true,
                "")
        }
        catch (e: Exception) {
            Log.e("OpenAIRepositoryImpl", "Error sending prompt: $e")
            OpenAIAnswer("", false, e.toString())
        }
    }
}