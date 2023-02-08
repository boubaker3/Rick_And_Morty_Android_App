package com.example.rick_and_morty.adapter

import RoomViewModel
import RoomViewModelFactory
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.rick_and_morty.R
import com.example.rick_and_morty.databinding.RickMortyItemBinding
import com.example.rick_and_morty.model.Character
import com.example.rick_and_morty.model.CharacterRoom
import com.example.rick_and_morty.room.AppDb
import com.example.rick_and_morty.ui.CharactersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoritesAdapter(private val characters: List<Character> ,
                       private val context:Context, private val viewModelStoreOwner: ViewModelStoreOwner):
    RecyclerView.Adapter<FavoritesAdapter.CharacterViewHolder>() {
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character=characters[position]
        val room=AppDb.getRoomInstance(context)
        val viewModel = ViewModelProvider(viewModelStoreOwner, RoomViewModelFactory(room.characterDao() )).get(
            RoomViewModel::class.java)
         if(character!==null){
             holder.bind(character) {
                viewModel.viewModelScope.launch {
                    viewModel.deleteData(character.id)
                   notifyItemChanged(position)
                    notifyItemRemoved(position)

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view=DataBindingUtil.inflate<RickMortyItemBinding>(LayoutInflater.from(context),R.layout.rick_morty_item,parent,false)
        return CharacterViewHolder(view)
    }


    class CharacterViewHolder(private val binding: RickMortyItemBinding):RecyclerView.ViewHolder(binding.root)
    {

          fun bind(character: Character  ,onClick: (Character) -> Unit){
              binding.name.text=character.name
              binding.gender.text=character.gender
              binding.specie.text=character.species
              binding.status.text=character.status

              val imageHolder= binding.image
             val imageUrl=character.image
              imageHolder.load(imageUrl){
                  crossfade(true)
                  placeholder(R.drawable.image_placeholder)
                  transformations(CircleCropTransformation())
              }
              binding.favImage.load(R.drawable.ic_baseline_favorite_24)

              binding.favImage.setOnClickListener {
                  onClick(character)
              }
          }

    }

    override fun getItemCount(): Int {
        return characters.size!!
    }


}