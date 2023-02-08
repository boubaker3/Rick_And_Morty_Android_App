package com.example.rick_and_morty.ui

import MyViewModelFactory
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rick_and_morty.adapter.CharactersAdapter
import com.example.rick_and_morty.R
import com.example.rick_and_morty.databinding.FragmentHomeBinding
import com.example.rick_and_morty.restApi.RetrofitBuilder
import kotlinx.coroutines.launch

class HomeFragment : Fragment()  {
private lateinit var binding:FragmentHomeBinding
var adapter:CharactersAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        val service=RetrofitBuilder.CharacterApiSingleton.rickAndMortyApi
         val myViewModel = ViewModelProvider(this, MyViewModelFactory(service,"all" )).get(
             CharactersViewModel::class.java)


        myViewModel.characters.observe(viewLifecycleOwner ){  pagingData ->
            viewLifecycleOwner.lifecycleScope.launch {
                  adapter = CharactersAdapter(requireContext(),  ViewModelStoreOwner { ViewModelStore() } )

                adapter?.submitData(viewLifecycleOwner.lifecycle,pagingData)
                binding.homeRecV.adapter=adapter
               binding.homeRecV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                }

        }

        return binding.root
    }

}