package com.example.rick_and_morty.paging

 import android.util.Log
 import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rick_and_morty.model.Character
import com.example.rick_and_morty.restApi.RickAndMortyApi
 import retrofit2.HttpException
 import java.lang.Exception

 class CharacterPagingSource(private val characterApi:RickAndMortyApi,private val requestType:String,
 private val search:String):PagingSource<Int,Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
            val page=params.key?:1
            val perPage=params.loadSize
        return try {
            if(requestType == "all"){
                val response=characterApi.getCharacters(page,perPage)
                Log.d("type","all")
                   LoadResult.Page(
                    data = response.results,
                    prevKey =if (page > 0) page - 1 else null,
                    nextKey = if(response.results.isEmpty()) null else page+1)
            }else  {
                Log.d("type","search")

                val response=characterApi.getCharacter(page,perPage,search)

                     LoadResult.Page(
                    data = response.results,
                    prevKey =if (page == 1) null  else page-1,
                    nextKey = if(response.results.isEmpty()) null else page+1)
            }



            } catch (e:Exception){
             return LoadResult.Error(e)

        }

        catch (e:HttpException){
             return   LoadResult.Error(e)

        }
    }

     override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
         TODO("Not yet implemented")
     }


 }