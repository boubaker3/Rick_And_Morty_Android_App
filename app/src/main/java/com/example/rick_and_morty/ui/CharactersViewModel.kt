package com.example.rick_and_morty.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.example.rick_and_morty.dao.CharacterDao
import com.example.rick_and_morty.model.Character
import com.example.rick_and_morty.model.CharacterRoom
import com.example.rick_and_morty.paging.CharacterPagingSource
import com.example.rick_and_morty.restApi.RickAndMortyApi
import com.example.rick_and_morty.room.AppDb

class CharactersViewModel constructor(private val characterApi:RickAndMortyApi,private val type:String ) : ViewModel() {
        val  searchQuery = MutableLiveData<String>()


      val characters=Pager(config = PagingConfig(pageSize = 10, maxSize = 40, enablePlaceholders = false),
                      pagingSourceFactory = {CharacterPagingSource(characterApi,"all","")}).liveData.cachedIn(viewModelScope)

      fun updateSearchQuery(  searchFor: String):  LiveData<PagingData<Character>> {
          searchQuery.value=searchFor
          return Pager(config = PagingConfig(pageSize = 10, maxSize = 40, enablePlaceholders = false),
              pagingSourceFactory = {CharacterPagingSource(characterApi,type ,searchQuery.value.toString())}).liveData.cachedIn(viewModelScope)

      }


}