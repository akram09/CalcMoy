package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2.provider

import android.app.SearchManager
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import dagger.android.AndroidInjection
import dagger.android.DaggerContentProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import oxxy.kero.roiaculte.team7.domain.interactors.ProvideSuggestions
import oxxy.kero.roiaculte.team7.domain.interactors.Suggestions
import javax.inject.Inject

class SuggetionsProvider : DaggerContentProvider() {


    @Inject lateinit var provideSuggestions: ProvideSuggestions

    private val listUni : ArrayList<Suggestions> by lazy {
        val list = ArrayList<Suggestions>()
        for ( i in 1..10){
            list.add(Suggestions(i.toLong(),"univer : $i ","جامعة : $i"))
        }
        list
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {

//        val list : MutableList<Suggestions> = ArrayList() // provideSuggestions.getSuggestions(uri.lastPathSegment)


        val query = uri.lastPathSegment
        Log.v("fucking_provider","excute searching (query) $query")
        return getCursorFromList(listUni.filter {it.nameFR.contains(query) || it.nameFR.contains(query)})

    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int =0
    override fun getType(uri: Uri): String? = null

    private fun  getCursorFromList(suggestions : List<Suggestions> ) : Cursor{
         val cursor =  MatrixCursor(
             arrayOf("_id", SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_TEXT_2)
        )
        SearchManager.SUGGEST_COLUMN_TEXT_1
        for ( suggestion in suggestions ) {
            cursor.newRow()
                .add("_id", suggestion.id)
                .add(SearchManager.SUGGEST_COLUMN_TEXT_1, suggestion.nameFR)
                .add(SearchManager.SUGGEST_COLUMN_TEXT_2, suggestion.nameAR)
        }

        return cursor
    }
}