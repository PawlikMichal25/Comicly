package io.spacecowboyapps.comicly.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface ComicDao {

    @Insert
    fun insertAll(comics: List<Comic>)

    @Query("DELETE FROM comic")
    fun deleteAll()

    @Query("SELECT * FROM comic")
    fun getAll(): List<Comic>

    @Query("SELECT * FROM comic")
    fun getAllSingle(): Single<List<Comic>>
}