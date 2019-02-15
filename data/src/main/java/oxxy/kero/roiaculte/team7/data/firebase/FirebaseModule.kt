package oxxy.kero.roiaculte.team7.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import oxxy.kero.roiaculte.team7.data.repositories.AuthentificationRepositoryImpl
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideUserId(auth :FirebaseAuth):UserId{
        return UserId(auth.uid ?: "")
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
    @Provides
    @Singleton
    fun provideFirebaseDatabase():FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }
    @Provides
    @Singleton
    fun provideFirebaseStorage():FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

}