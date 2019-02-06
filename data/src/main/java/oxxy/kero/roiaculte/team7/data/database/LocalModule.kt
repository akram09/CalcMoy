package oxxy.kero.roiaculte.team7.data.database

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class LocalModule {

    @Provides
    fun provideDataBase(context : Context):CalcMoyDatabase{
        return CalcMoyDatabase.create(context)!!
    }
}