package oxxy.kero.roiaculte.team7.calcmoy.utils.extension

import android.content.Context
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.domain.models.FacultyType
import oxxy.kero.roiaculte.team7.domain.models.School

fun School.toInt() : Int{
    val school: Int

    when (this) {
        School.CEM -> school = 1
        School.PRIMAIRE -> school = 0
        School.LYCEE -> school = 2
        School.UNIVERSITE -> school = 3
        else -> school = 0
    }
    return school
}

fun Int.toSChool() : School {
    val enumSchool: School

    when (this) {
        0 -> enumSchool = School.PRIMAIRE
        1 -> enumSchool = School.CEM
        2 -> enumSchool = School.LYCEE
        3 -> enumSchool = School.UNIVERSITE
        else -> enumSchool = School.PRIMAIRE
    }

    return enumSchool
}

fun String.mapToFaculty(context: Context) :  FacultyType?{

    var faculty :FacultyType? = null

    when(this){
        context.getString(R.string.scien) -> faculty = FacultyType.SCIENCE
        context.getString(R.string.lettre)-> faculty = FacultyType.LETTRE
        context.getString(R.string.math) -> faculty = FacultyType.MATH
        context.getString(R.string.scien) -> faculty = FacultyType.SCIENCE
        context.getString(R.string.economi) -> faculty = FacultyType.GESTION
        context.getString(R.string.tm_electrique) -> faculty = FacultyType.TM_ELECT
        context.getString(R.string.tm_mecanique) -> faculty = FacultyType.TM_MECAN
        context.getString(R.string.tm_civil) ->faculty =FacultyType.TM_CIVIL
        context.getString(R.string.tm_tar) ->faculty =FacultyType.TM_METH
        context.getString(R.string.language) ->faculty =FacultyType.LANGUE
        context.getString(R.string.philo) ->faculty =FacultyType.PHILO
    }
    return faculty
}
