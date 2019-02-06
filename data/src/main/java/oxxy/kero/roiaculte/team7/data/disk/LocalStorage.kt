package oxxy.kero.roiaculte.team7.data.disk

import oxxy.kero.roiaculte.team7.domain.models.FacultyType
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.School
import oxxy.kero.roiaculte.team7.domain.models.Semestre
 typealias  year = Array<DataMatter>
object LocalStorage {
    val modulesWithoutFaculte = emptyMap<School , Array<year>>()
    val modulesSecondYear = mutableMapOf<FacultyType, Array<DataMatter>>()
    val moduleThirdYear = mutableMapOf<FacultyType, Array<DataMatter>>()
}