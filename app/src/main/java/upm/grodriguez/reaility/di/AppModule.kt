package upm.grodriguez.reaility.di

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.client.OpenAI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import upm.grodriguez.reaility.ReailityApp
import upm.grodriguez.reaility.data.repository.AuthRepositoryImpl
import upm.grodriguez.reaility.data.repository.CreationRepositoryImpl
import upm.grodriguez.reaility.data.repository.LocationRepositoryImpl
import upm.grodriguez.reaility.data.repository.OpenAIRepositoryImpl
import upm.grodriguez.reaility.domain.repository.AuthRepository
import upm.grodriguez.reaility.domain.repository.CreationRepository
import upm.grodriguez.reaility.domain.repository.LocationRepository
import upm.grodriguez.reaility.domain.repository.OpenAIRepository
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl(
        auth = Firebase.auth
    )

    @Provides
    fun provideLocationRepository(): LocationRepository = LocationRepositoryImpl()

    @Provides
    fun provideOpenAIRepo(): OpenAIRepository = OpenAIRepositoryImpl(
        OpenAI(
            token = ReailityApp.applicationContext.packageManager.getApplicationInfo(
                ReailityApp.applicationContext.packageName,
                PackageManager.GET_META_DATA
            ).metaData.getString("com.aallam.openai.api_key")!!,
            timeout = Timeout(socket = 60.seconds)
        )
    )

    @Provides
    fun provideCreationRepo(): CreationRepository = CreationRepositoryImpl()
}