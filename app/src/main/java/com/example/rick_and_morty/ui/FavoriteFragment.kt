package com.example.rick_and_morty.ui

import RoomViewModel
import RoomViewModelFactory
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rick_and_morty.R
import com.example.rick_and_morty.adapter.CharactersAdapter
import com.example.rick_and_morty.adapter.FavoritesAdapter
import com.example.rick_and_morty.databinding.FragmentFavoriteBinding
import com.example.rick_and_morty.databinding.FragmentHomeBinding
import com.example.rick_and_morty.room.AppDb
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
          binding= DataBindingUtil.inflate(inflater,R.layout.fragment_favorite, container, false)
        val room= AppDb.getRoomInstance(requireContext())
        val myViewModel = ViewModelProvider(this, RoomViewModelFactory(room.characterDao())).get(RoomViewModel::class.java)
viewLifecycleOwner.lifecycleScope.launch{


        myViewModel.retrieveData().observe(viewLifecycleOwner ){  pagingData ->
                val adapter = FavoritesAdapter(pagingData, requireContext(),   ViewModelStoreOwner { ViewModelStore() })

                binding.favRecV.adapter=adapter
                binding.favRecV.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL,false, )


        }}

        return binding.root
    }


}