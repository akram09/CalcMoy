package oxxy.kero.roiaculte.team7.data.disk

import oxxy.kero.roiaculte.team7.domain.models.FacultyType
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.School
import oxxy.kero.roiaculte.team7.domain.models.Semestre
 typealias  year = Array<DataMatter>
object LocalStorage {
    val modulesWithoutFaculte = mutableMapOf<School , Array<year>>(
        Pair(School.PRIMAIRE ,
            arrayOf(
                 arrayOf(
                    DataMatter("اللغة العربية", 1 , "#007ACC")
                            , DataMatter("الرياضيات" , 1 , "#CBC13F")
                            ,  DataMatter("التربية الاسلامية" , 1 , "#CBC13F")
                            ,  DataMatter("التربية المدنية" , 1, "#CBC13F")
                            ,  DataMatter("التربية العلمية و التكنولوجية", 1, "#CBC13F")
                            ,  DataMatter(color= "#CBC13F", coifficient = 1,name ="التربية الموسيقية" )
                            ,  DataMatter(color= "#CBC13F", coifficient = 1,name = "التربية التشكيلية")
                            , DataMatter(color=  "#CBC13F", coifficient = 1, name = "التربية البدنيةو الرياضية")
                 )
             , arrayOf(
                    DataMatter("اللغة العربية", 1 , "#007ACC")
                    , DataMatter("الرياضيات" , 1 , "#CBC13F")
                    ,  DataMatter("التربية الاسلامية" , 1 , "#CBC13F")
                    ,  DataMatter("التربية المدنية" , 1, "#CBC13F")
                    ,  DataMatter("التربية العلمية و التكنولوجية", 1, "#CBC13F")
                    ,  DataMatter(color= "#CBC13F", coifficient = 1,name ="التربية الموسيقية" )
                    ,  DataMatter(color= "#CBC13F", coifficient = 1,name = "التربية التشكيلية")
                    , DataMatter(color=  "#CBC13F", coifficient = 1, name = "التربية البدنيةو الرياضية")
                ),
                arrayOf(
                    DataMatter("اللغة العربية", 1 , "#007ACC")
                    , DataMatter("الرياضيات" , 1 , "#CBC13F")
                    ,  DataMatter("التربية الاسلامية" , 1 , "#CBC13F")
                    ,  DataMatter("التربية المدنية" , 1, "#CBC13F")
                    ,  DataMatter("التربية العلمية و التكنولوجية", 1, "#CBC13F"),
                    DataMatter(color= "#CBC13F", coifficient = 1,name = "التربية الفنية و التشكيلية")
                    , DataMatter(color=  "#CBC13F", coifficient = 1, name = "التربية البدنيةو الرياضية")
                , DataMatter(name = "التاريخ و الجغرافيا", coifficient = 1 , color = "#CBC13F")
                )
               , arrayOf(
                    DataMatter("اللغة العربية", 1 , "#007ACC")
                    , DataMatter("الرياضيات" , 1 , "#CBC13F")
                    ,  DataMatter("التربية الاسلامية" , 1 , "#CBC13F")
                    ,  DataMatter("التربية المدنية" , 1, "#CBC13F")
                    ,  DataMatter("التربية العلمية و التكنولوجية", 1, "#CBC13F"),
                     DataMatter(name = "التاريخ و الجغرافيا", coifficient = 1 , color = "#CBC13F")
                    ,   DataMatter(color= "#CBC13F", coifficient = 1,name = "التربية الفنية و المحفوظات")
                    , DataMatter("اللغة الفرنسية" , 1 , "#CBC13F")
                    ,  DataMatter("اللغة الامازيغية" , 1 , "#CBC13F")
                )
            ,
                arrayOf(
                    DataMatter("اللغة العربية", 1 , "#007ACC")
                    , DataMatter("الرياضيات" , 1 , "#CBC13F")
                    ,  DataMatter("التربية الاسلامية" , 1 , "#CBC13F")
                    ,  DataMatter("التربية المدنية" , 1, "#CBC13F")
                    ,  DataMatter("التربية العلمية و التكنولوجية", 1, "#CBC13F"),
                    DataMatter(name = "التاريخ و الجغرافيا", coifficient = 1 , color = "#CBC13F")
                    ,   DataMatter(color= "#CBC13F", coifficient = 1,name = "التربية الفنية والموسيقية")
                    , DataMatter("اللغة الفرنسية" , 1 , "#CBC13F")
                    ,  DataMatter("اللغة الامازيغية" , 1 , "#CBC13F")

                )
                )
        )
    , Pair(School.CEM,
            arrayOf(
              arrayOf(
                  DataMatter("اللغة العربية", 2, "#007ACC")
                  , DataMatter("الرياضيات" , 2 , "#CBC13F")
                  , DataMatter("اللغة الفرنسية" , 1 , "#CBC13F")
                  ,  DataMatter("اللغة الامازيغية" , 2 , "#CBC13F")
                  ,  DataMatter("التربية الاسلامية" , 1 , "#CBC13F")
                  ,  DataMatter("التربية المدنية" , 1, "#CBC13F")
              ,  DataMatter(name = "التاريخ", coifficient = 1 , color = "#CBC13F")
              , DataMatter("الجغرافيا", 1, "#CBC13F")
                  ,  DataMatter("الغلوم الفيزيائية والنكنولوجية" , 1 , "#CBC13F")
                  ,  DataMatter("علوم الطبيعية و الحياة" , 1 , "#CBC13F")
                  ,  DataMatter("التربية البدنيةو الرياضية" , 1 , "#CBC13F")
                  ,  DataMatter("التربية الفنية و التشكيلية" , 1 , "#CBC13F")
                  ,  DataMatter("المغلوماتية" , 1 , "#CBC13F")
              ),
            arrayOf(
                DataMatter("اللغة العربية", 3, "#007ACC")
                , DataMatter("الرياضيات" , 2 , "#CBC13F")
                ,  DataMatter("اللغة الامازيغية" , 2 , "#CBC13F")
                , DataMatter("اللغة الفرنسية" , 2 , "#CBC13F")
                ,  DataMatter("الغلوم الفيزيائية والنكنولوجية" , 1 , "#CBC13F")
                ,  DataMatter("التربية الاسلامية" , 1 , "#CBC13F")
                ,  DataMatter("اللغة الانجليزية" , 1 , "#CBC13F")
                ,  DataMatter(name = "التاريخ", coifficient = 1 , color = "#CBC13F")
                , DataMatter("الجغرافيا", 1, "#CBC13F")
                ,  DataMatter("علوم الطبيعية و الحياة" , 1 , "#CBC13F")
                ,  DataMatter("التربية البدنيةو الرياضية" , 1 , "#CBC13F")
                ,  DataMatter("التربية الفنية و التشكيلية" , 1 , "#CBC13F")
                ,  DataMatter("المغلوماتية" , 1 , "#CBC13F")
                ,  DataMatter("التربية المدنية" , 1, "#CBC13F")
            )
            , arrayOf(
                    DataMatter("اللغة العربية", 3, "#007ACC")
                    , DataMatter("الرياضيات" , 3 , "#CBC13F")
                    ,  DataMatter("اللغة الامازيغية" , 2 , "#CBC13F")
                    , DataMatter("اللغة الفرنسية" , 2 , "#CBC13F")
                    ,  DataMatter("الغلوم الفيزيائية والنكنولوجية" , 1 , "#CBC13F")
                    ,  DataMatter("التربية الاسلامية" , 1 , "#CBC13F")
                    ,  DataMatter("اللغة الانجليزية" , 1 , "#CBC13F")
                    ,  DataMatter(name = "التاريخ", coifficient = 1 , color = "#CBC13F")
                    , DataMatter("الجغرافيا", 1, "#CBC13F")
                    ,  DataMatter("علوم الطبيعية و الحياة" , 1 , "#CBC13F")
                    ,  DataMatter("التربية البدنيةو الرياضية" , 1 , "#CBC13F")
                    ,  DataMatter("التربية الفنية و التشكيلية" , 1 , "#CBC13F")
                    ,  DataMatter("المغلوماتية" , 1 , "#CBC13F")
                    ,  DataMatter("التربية المدنية" , 1, "#CBC13F")
                )
            , arrayOf(
                    DataMatter("اللغة العربية", 5, "#007ACC")
                    , DataMatter("الرياضيات" , 4, "#CBC13F")
                    ,  DataMatter("اللغة الامازيغية" , 2 , "#CBC13F")
                    , DataMatter("اللغة الفرنسية" , 3 , "#CBC13F")
                    ,  DataMatter("الغلوم الفيزيائية والنكنولوجية" , 2 , "#CBC13F")
                    ,  DataMatter("التربية الاسلامية" , 2 , "#CBC13F")
                    ,  DataMatter("اللغة الانجليزية" , 2 , "#CBC13F")
                    , DataMatter("التاريخ و الجغرافيا", 3, "#CBC13F")
                    ,  DataMatter("علوم الطبيعية و الحياة" , 2 , "#CBC13F")
                    ,  DataMatter("التربية البدنيةو الرياضية" , 1 , "#CBC13F")
                    ,  DataMatter("التربية الفنية و التشكيلية" , 1 , "#CBC13F")
                    ,  DataMatter("المغلوماتية" , 1 , "#CBC13F")
                    ,  DataMatter("التربية المدنية" , 1, "#CBC13F")
                )

            )

            )
    )
   val lyceeArray = arrayOf(
       mutableMapOf(
           Pair(FacultyType.SCIENCE , arrayOf(
               DataMatter("اللغة العربية", 3, "#007ACC")
               , DataMatter("الرياضيات" , 5, "#CBC13F")
               ,  DataMatter("اللغة الامازيغية" , 2 , "#CBC13F")
               , DataMatter("اللغة الفرنسية" , 2 , "#CBC13F")
               ,  DataMatter("الغلوم الفيزيائية والنكنولوجية" , 4 , "#CBC13F")
               ,  DataMatter("التربية الاسلامية" , 2 , "#CBC13F")
               ,  DataMatter("اللغة الانجليزية" , 2 , "#CBC13F")
               , DataMatter("التاريخ و الجغرافيا", 2, "#CBC13F")
               ,  DataMatter("علوم الطبيعية و الحياة" , 4 , "#CBC13F")
               ,  DataMatter("الاعلام الآلي" , 2 , "#CBC13F")
               ,  DataMatter("التربية البدنية" , 1 , "#CBC13F")
               ,  DataMatter("التربية الفنية" , 1 , "#CBC13F")
               ,  DataMatter("التكنولوجية" , 2 , "#CBC13F")



           ))
           , Pair(FacultyType.LETTRE, arrayOf(
               DataMatter("اللغة العربية", 5, "#007ACC")
               , DataMatter("الرياضيات" , 2, "#CBC13F")
               ,  DataMatter("اللغة الامازيغية" , 2 , "#CBC13F")
               , DataMatter("اللغة الفرنسية" , 3 , "#CBC13F")
               ,  DataMatter("الغلوم الفيزيائية والنكنولوجية" , 2 , "#CBC13F")
               ,  DataMatter("التربية الاسلامية" , 2 , "#CBC13F")
               ,  DataMatter("اللغة الانجليزية" , 3 , "#CBC13F")
               ,  DataMatter("الاعلام الآلي" , 2 , "#CBC13F")
               , DataMatter("التاريخ و الجغرافيا", 2, "#CBC13F")
               ,  DataMatter("علوم الطبيعية و الحياة" , 2 , "#CBC13F")
               ,  DataMatter("التربية البدنية" , 1 , "#CBC13F")
               ,  DataMatter("التربية الفنية" , 1 , "#CBC13F")
           ))
       )
   , mutableMapOf(
           Pair(FacultyType.PHILO, arrayOf(
               DataMatter("اللغة العربية", 5, "#007ACC")
               , DataMatter("الرياضيات" , 2, "#CBC13F")
               ,  DataMatter("اللغة الامازيغية" , 2 , "#CBC13F")
               , DataMatter("اللغة الفرنسية" , 3 , "#CBC13F")
               ,  DataMatter("الغلوم الفيزيائية" , 2 , "#CBC13F")
               ,  DataMatter("التربية الاسلامية" , 2 , "#CBC13F")
               ,  DataMatter("اللغة الانجليزية" , 3 , "#CBC13F")
               , DataMatter("التاريخ و الجغرافيا", 2, "#CBC13F")
               ,  DataMatter("علوم الطبيعية و الحياة" , 2 , "#CBC13F")
               ,   DataMatter("الفلسفة", 5, "#007ACC")
               ,  DataMatter("التربية البدنية" , 1 , "#CBC13F")
               ,  DataMatter("التربية الفنية" , 1 , "#CBC13F")
           ))
       , Pair(FacultyType.GESTION, arrayOf(
               DataMatter("اللغة العربية", 2, "#007ACC")
               , DataMatter("الرياضيات" , 3, "#CBC13F")
               ,  DataMatter("اللغة الامازيغية" , 2 , "#CBC13F")
               , DataMatter("اللغة الفرنسية" , 2 , "#CBC13F")
               , DataMatter("لاقتصاد" , 4 , "#CBC13F")
               ,  DataMatter("التربية الاسلامية" , 2 , "#CBC13F")
               ,  DataMatter("اللغة الانجليزية" , 2 , "#CBC13F")
               , DataMatter("التاريخ و الجغرافيا", 2, "#CBC13F")
               , DataMatter("التسيير المحاسبي", 5, "#007ACC")
               ,  DataMatter("القانون" , 2 , "#CBC13F")
               ,  DataMatter("التربية البدنية" , 1 , "#CBC13F")
               ,  DataMatter("التربية الفنية" , 1 , "#CBC13F")
           )),
           Pair(FacultyType.LETTRE, arrayOf(
               DataMatter("اللغة العربية", 4, "#007ACC")
               , DataMatter("الرياضيات" , 2, "#CBC13F")
               ,  DataMatter("اللغة الامازيغية" , 2 , "#CBC13F")
               , DataMatter("اللغة الفرنسية" , 4 , "#CBC13F")
               , DataMatter("اللغة الأجنبية الثالثة" , 4 , "#CBC13F")
               ,  DataMatter("التربية الاسلامية" , 2 , "#CBC13F")
               ,  DataMatter("اللغة الانجليزية" , 4 , "#CBC13F")
               , DataMatter("التاريخ و الجغرافيا", 4, "#CBC13F")
               ,  DataMatter("التربية البدنية" , 1 , "#CBC13F")
               ,  DataMatter("التربية الفنية" , 1 , "#CBC13F")
           )),
           Pair(FacultyType.MATH , arrayOf(
               DataMatter("اللغة العربية", 2, "#007ACC")
               , DataMatter("الرياضيات" , 7, "#CBC13F")
               ,  DataMatter("اللغة الامازيغية" , 2 , "#CBC13F")
               , DataMatter("اللغة الفرنسية" , 2 , "#CBC13F")
               , DataMatter("الغلوم الفيزيائية" , 6 , "#CBC13F")
               ,  DataMatter("التربية الاسلامية" , 2 , "#CBC13F")
               ,  DataMatter("اللغة الانجليزية" , 2 , "#CBC13F")
               , DataMatter("التاريخ و الجغرافيا", 2, "#CBC13F")
               ,  DataMatter("التربية البدنية" , 1 , "#CBC13F")
               ,  DataMatter("التربية الفنية" , 1 , "#CBC13F")
               ,  DataMatter("علوم الطبيعية و الحياة" , 2 , "#CBC13F")
           ))
          //7best f math techniqe 2eme annes b9atli 3eme annes o hdi
       ),
       mutableMapOf()
   )
}