package com.example.rick_and_morty.adapter

import RoomViewModel
import RoomViewModelFactory
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.rick_and_morty.R
import com.example.rick_and_morty.databinding.RickMortyItemBinding
import com.example.rick_and_morty.model.Character
import com.example.rick_and_morty.room.AppDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CharactersAdapter(private val context:Context, private val viewModelStoreOwner: ViewModelStoreOwner ):
    PagingDataAdapter<Character, CharactersAdapter.CharacterViewHolder>(CharacterDiffCallback())  {
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character=getItem(position)
        val room=AppDb.getRoomInstance(context)
        val viewModel = ViewModelProvider(viewModelStoreOwner, RoomViewModelFactory(room.characterDao() )).get(
            RoomViewModel::class.java)

         if(character!==null){
             holder.check(character,viewModel)

             holder.bind(character) {
                viewModel.viewModelScope.launch {

                        holder.action(character,viewModel)
                        notifyItemChanged(position)



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
        fun action(character: Character,  viewModel:RoomViewModel) {
            GlobalScope.launch(Dispatchers.IO){
                val isExist=viewModel.retrieveCharacter(character.id)
                if(isExist){

                    Log.d("res","deleted")

                    viewModel.deleteData(character.id)
                    binding.favImage.load(R.drawable.ic_baseline_favorite_border_24)

                }else{
                    viewModel.saveData(character)
                    binding.favImage.load(R.drawable.ic_baseline_favorite_24)
                    Log.d("res","added")

                }
            }


        }


          fun check(character: Character,  viewModel:RoomViewModel) {
              GlobalScope.launch(Dispatchers.IO){
                  val isExist=viewModel.retrieveCharacter(character.id)
                  if(isExist){
                       binding.favImage.load(R.drawable.ic_baseline_favorite_24)


                  }else{

                       binding.favImage.load(R.drawable.ic_baseline_favorite_border_24)
                  }
              }


          }
          fun bind(character: Character,onClick: (Character) -> Unit){
              binding.name.text=character.name
              binding.gender.text=character.gender
              binding.specie.text=character.species
              binding.status.text=character.status
if(character.status=="Alive"){
    binding.status.setTextColor(Color.GREEN)
}else{
    binding.status.setTextColor(Color.RED)
}
              val imageHolder= binding.image
             val imageUrl=character.image
              imageHolder.load(imageUrl){
                  crossfade(true)
                  placeholder(R.drawable.image_placeholder)
                  transformations(CircleCropTransformation())
              }
              binding.favImage.setOnClickListener {
                  onClick(character)
              }
          }

    }
    class CharacterDiffCallback:DiffUtil.ItemCallback<Character>(){
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

    }




}