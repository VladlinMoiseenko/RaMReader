package ru.vladlin.ram_reader.data.character.repo

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.vladlin.ram_reader.data.source.retrofit.ApiService
import ru.vladlin.ram_reader.domain.model.CharacterModel

class CharacterListPagingSource(
    private val apiService: ApiService,
    var name: String?,
    var gender: String?,
    var status: String?
) : PagingSource<Int, CharacterModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
        val pageNumber = params.key ?: 1

        val response = apiService.getAllCharactersFilter(name,status,gender,pageNumber)

        if (!response.isSuccessful) {
            return LoadResult.Error(Throwable("Can't retrieve data!"))
        }

        val nextPageNumber = checkNextOrPrevPage(response.body()!!.info.next)
        val prevPageNumber = checkNextOrPrevPage(response.body()!!.info.prev)

        return LoadResult.Page(
            data = response.body()?.results!!,
            prevKey = prevPageNumber,
            nextKey = nextPageNumber
        )
    }

    private fun checkNextOrPrevPage(item:String?): Int? {
        if(item != null) {
            val uri = Uri.parse(item)
            val nextPageQuery = uri.getQueryParameter("page")
            return nextPageQuery?.toInt()
        }else{
            return null
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {

        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}