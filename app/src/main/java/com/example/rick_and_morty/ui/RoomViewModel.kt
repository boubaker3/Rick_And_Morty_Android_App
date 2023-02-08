
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.example.rick_and_morty.dao.CharacterDao
import com.example.rick_and_morty.model.Character


class RoomViewModel(private val cDao:CharacterDao) : ViewModel( ) {

      suspend fun saveData(character: Character){
        cDao.saveCharacter(character)
    }
    suspend fun retrieveData():LiveData<List<Character>>{
       return cDao.retrieveCharacters()
    }
    suspend fun deleteData(id:Int){
        cDao.deleteCharacter(id)
    }
     suspend  fun retrieveCharacter(id:Int):Boolean{
        val isExist=cDao.retrieveCharacter(id)
         Log.d("isempty", isExist.count().toString())
           if (isExist.count()>0){
               return true
           }
           return false
    }
}