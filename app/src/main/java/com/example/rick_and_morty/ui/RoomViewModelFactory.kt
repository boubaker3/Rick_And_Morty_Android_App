import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rick_and_morty.dao.CharacterDao
import com.example.rick_and_morty.restApi.RickAndMortyApi
import com.example.rick_and_morty.ui.CharactersViewModel

class RoomViewModelFactory(private val cDao: CharacterDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return RoomViewModel( cDao ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}