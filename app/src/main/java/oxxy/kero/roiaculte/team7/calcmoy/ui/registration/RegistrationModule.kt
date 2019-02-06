package oxxy.kero.roiaculte.team7.calcmoy.ui.registration

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import dagger.Module
import dagger.Provides
import oxxy.kero.roiaculte.team7.calcmoy.AndroidApplication
import oxxy.kero.roiaculte.team7.calcmoy.R
import javax.inject.Singleton

@Module
class RegistrationModule {

    @Provides @RegistrationScoop
    fun providGoogleSigneInOption(context : RegistrationActivity): GoogleSignInOptions {

        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    @Provides @RegistrationScoop
    fun provideGoogleApiClient(gso: GoogleSignInOptions, context: RegistrationActivity): GoogleApiClient {
        return GoogleApiClient.Builder(context)
            .enableAutoManage(context, null)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()
    }

    @Provides
    fun provideCallbackManager() = CallbackManager.Factory.create()

    @Provides
    fun provideLoginManager()= LoginManager.getInstance()
}