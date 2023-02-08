package com.example.rick_and_morty.dao

 import androidx.lifecycle.LiveData
 import androidx.paging.PagingData
import androidx.room.Dao
 import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
 import com.example.rick_and_morty.model.Character
 import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
@Insert(onConflict = OnConflictStrategy.IGNORE)
suspend fun saveCharacter(character:Character)

@Query("delete from characters where id=:id")
suspend fun deleteCharacter(id:Int)

@Query("select * from characters")
  fun retrieveCharacters(): LiveData<List<Character>>
 @Query("select * from characters where id=:id")
   suspend fun retrieveCharacter(id:Int):  List<Character>
}