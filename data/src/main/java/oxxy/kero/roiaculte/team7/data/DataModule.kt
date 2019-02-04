package oxxy.kero.roiaculte.team7.data

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import oxxy.kero.roiaculte.team7.data.firebase.AuthentificationFirebase
import oxxy.kero.roiaculte.team7.data.firebase.FirebaseModule
import oxxy.kero.roiaculte.team7.data.repositories.AuthentificationRepositoryImpl
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthentificationFirebase(auth : FirebaseAuth) = AuthentificationFirebase(auth)

    @Provides
    @Singleton
    fun provideRepo(auth : AuthentificationFirebase): AuthentificationRepository = AuthentificationRepositoryImpl(auth)

}