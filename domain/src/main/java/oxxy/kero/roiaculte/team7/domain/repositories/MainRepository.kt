package oxxy.kero.roiaculte.team7.domain.repositories

interface MainRepository {
 fun addUserSyncer(onDisconnected:()->Unit)
 fun removeUserSyncer(onDisconnected: () -> Unit)
}