package com.example.rick_and_morty.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterRoom(
    @PrimaryKey val id:Int,
    val name:String,
    val status:String,
    val species:String,
    val gender:String,
    val image:String

)
