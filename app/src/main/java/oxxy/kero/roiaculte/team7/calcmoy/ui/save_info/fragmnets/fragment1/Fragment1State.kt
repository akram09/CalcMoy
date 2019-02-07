package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1

import android.net.Uri
import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.domain.models.School

class Fragment1State (var name : String,var prename : String , var school: School , var year : Int, var image :Image?): State


sealed class Image{
    class ImageUrl(val url : String) : Image()
    class ImageUri(val uri: Uri) : Image()
}