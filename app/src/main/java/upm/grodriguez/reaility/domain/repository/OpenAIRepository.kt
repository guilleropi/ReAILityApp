package upm.grodriguez.reaility.domain.repository

data class OpenAIAnswer(
    val resultURL: String,
    val successs: Boolean,
    val error: String
)

interface
OpenAIRepository {
    suspend fun sendPrompt(prompt: String): OpenAIAnswer
}