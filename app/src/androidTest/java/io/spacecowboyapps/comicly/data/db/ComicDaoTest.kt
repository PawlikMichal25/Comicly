package io.spacecowboyapps.comicly.data.db

import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import org.assertj.core.api.Assertions.assertThat
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import io.reactivex.functions.Consumer
import io.spacecowboyapps.comicly.data.network.model.Image
import org.junit.After
import org.junit.Before
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class ComicDaoTest {

    private lateinit var database: Database
    private lateinit var comicDao: ComicDao

    private val comics = listOf(
        Comic(1, "title1", Image("path1", "ext1")),
        Comic(2, "title2", Image("path2", "ext2"))
    )

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, Database::class.java).build()
        comicDao = database.comicDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun whenTableNotEmpty_thenGetAllReturnsAllComics() {
        comicDao.insertAll(comics)
        assertThat(comicDao.getAll()).isEqualTo(comics)
    }

    @Test
    fun whenTableEmpty_thenGetAllReturnsEmptyList() {
        assertThat(comicDao.getAll().size).isEqualTo(0)
    }

    @Test
    fun whenTableNotEmpty_thenGetAllSingleReturnsAllComics() {
        comicDao.insertAll(comics)
        comicDao.getAllSingle().subscribe(Consumer {
            assertThat(it).isEqualTo(comics)
        })
    }

    @Test
    fun whenTableEmpty_thenGetAllSingleReturnsEmptyList() {
        comicDao.getAllSingle().subscribe(Consumer {
            assertThat(it.size).isEqualTo(0)
        })
    }

    @Test
    fun whenTableNotEmpty_thenDeleteAllDeletesAllComics() {
        comicDao.insertAll(comics)
        assertThat(comicDao.getAll()).isEqualTo(comics)

        comicDao.deleteAll()
        assertThat(comicDao.getAll().size).isEqualTo(0)
    }
}