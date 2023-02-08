package com.example.rick_and_morty.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rick_and_morty.dao.CharacterDao
import com.example.rick_and_morty.model.Character

@Database(entities = [Character::class], version = 1)
abstract class AppDb : RoomDatabase(){

   abstract fun characterDao(): CharacterDao
   companion object{
      @Volatile
      private var INSTANCE: AppDb? = null

      fun getRoomInstance(context: Context): AppDb {
         // if the INSTANCE is not null, then return it,
         // if it is, then create the database
         return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
               context.applicationContext,
               AppDb::class.java,
               "character_database"
            ).build()
            INSTANCE = instance
            // return instance
            instance
      }

   }
   }}