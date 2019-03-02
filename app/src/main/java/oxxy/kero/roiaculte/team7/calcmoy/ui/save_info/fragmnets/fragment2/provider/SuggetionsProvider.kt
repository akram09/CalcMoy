package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2.provider

import android.app.SearchManager
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import dagger.android.DaggerContentProvider
import kotlinx.coroutines.*
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.saveinfo.ProvideSuggestions
import oxxy.kero.roiaculte.team7.domain.interactors.saveinfo.Suggestions
import javax.inject.Inject

class SuggetionsProvider : DaggerContentProvider() {


    @Inject lateinit var provideSuggestions: ProvideSuggestions
     var  scope: CoroutineScope= CoroutineScope(Dispatchers.Main)



    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor?  {


        val query = uri.lastPathSegment
        val job = scope.async {
            val either = provideSuggestions(query)
            if(either is Either.Right ) either.b
            else emptyList()
        }
        Log.v("fucking_provider","excute searching (query) $query")
        var list :List<Suggestions>?= null
        runBlocking {
            list = job.await()
        }

        return getCursorFromList(list!! )
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int =0
    override fun getType(uri: Uri): String? = null

    private fun  getCursorFromList(suggestions : List<Suggestions> ) : Cursor{
         val cursor =  MatrixCursor(
             arrayOf("_id", SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_TEXT_2,SearchManager.SUGGEST_COLUMN_INTENT_DATA)
        )
        for ( suggestion in suggestions ) {
            cursor.newRow()
                .add("_id", suggestion.id)
                .add(SearchManager.SUGGEST_COLUMN_TEXT_1, suggestion.nameFR)
                .add(SearchManager.SUGGEST_COLUMN_TEXT_2, suggestion.nameAR)
                .add(SearchManager.SUGGEST_COLUMN_INTENT_DATA,suggestion.id)
        }

        return cursor
    }
}