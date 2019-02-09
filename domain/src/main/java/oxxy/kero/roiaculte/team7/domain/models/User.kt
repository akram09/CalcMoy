package oxxy.kero.roiaculte.team7.domain.models

data class User (var id :String,
            val name :String,
            val prename :String,
            val school :School,
            val year :String,
            val semstre :Int,
            var imageUrl :String,
            val moyenneGenerale :Double )