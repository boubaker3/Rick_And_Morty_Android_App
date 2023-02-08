package com.example.rick_and_morty.ui

import MyViewModelFactory
import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rick_and_morty.R
import com.example.rick_and_morty.adapter.CharactersAdapter
import com.example.rick_and_morty.databinding.FragmentSearchBinding
import com.example.rick_and_morty.restApi.RetrofitBuilder
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

lateinit var binding:FragmentSearchBinding
var searchFor:String=""
var adapter:CharactersAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)
        val service= RetrofitBuilder.CharacterApiSingleton.rickAndMortyApi
        val myViewModel = ViewModelProvider(this@SearchFragment, MyViewModelFactory(service,"search")).
        get(CharactersViewModel::class.java)
        binding.searchBar.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
             }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.searchFor.text="search for "+p0.toString()
                myViewModel.updateSearchQuery(p0.toString())
                myViewModel.updateSearchQuery(p0.toString()).observe(viewLifecycleOwner ){  pagingData ->
                    viewLifecycleOwner.lifecycleScope.launch {
                          adapter = CharactersAdapter(requireContext(),   ViewModelStoreOwner { ViewModelStore() } )
                        adapter?.submitData(viewLifecycleOwner.lifecycle,pagingData)
                        binding.searchRecV.adapter=adapter
                        binding.searchRecV.layoutManager = LinearLayoutManager(requireContext(),
                            LinearLayoutManager.VERTICAL,false)

                    }

                }
             }

            override fun afterTextChanged(p0: Editable?) {
          }

        })

        return binding.root
    }

}