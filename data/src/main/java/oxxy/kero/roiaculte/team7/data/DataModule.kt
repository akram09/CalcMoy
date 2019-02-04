package oxxy.kero.roiaculte.team7.data

import dagger.Module
import oxxy.kero.roiaculte.team7.data.firebase.FirebaseModule

@Module(includes= [FirebaseModule::class])
abstract class DataModule {
}