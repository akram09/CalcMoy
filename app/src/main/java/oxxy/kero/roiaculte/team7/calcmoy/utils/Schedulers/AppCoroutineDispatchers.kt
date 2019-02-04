package oxxy.kero.roiaculte.team7.calcmoy.utils.Schedulers

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers

class AppCoroutineDispatchers(override val io: CoroutineDispatcher, override val computaion: CoroutineDispatcher,
                              override val main: CoroutineDispatcher): CouroutineDispatchers