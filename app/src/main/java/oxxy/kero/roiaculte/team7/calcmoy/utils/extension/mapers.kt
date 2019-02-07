package oxxy.kero.roiaculte.team7.calcmoy.utils.extension

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
