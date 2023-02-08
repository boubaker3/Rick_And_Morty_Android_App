import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rick_and_morty.restApi.RickAndMortyApi
import com.example.rick_and_morty.ui.CharactersViewModel

class MyViewModelFactory(private val charactersApi: RickAndMortyApi,private val type:String ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            return CharactersViewModel(charactersApi,type  ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}