package io.spacecowboyapps.comicly.data

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.spacecowboyapps.comicly.commons.rx.AppSchedulersTest
import io.spacecowboyapps.comicly.data.network.MarvelClientAdapter
import io.spacecowboyapps.comicly.data.preferences.Preferences
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Single
import io.spacecowboyapps.comicly.commons.TimeProvider
import io.spacecowboyapps.comicly.data.ComicsRepository.Companion.EXPIRATION_TIME
import io.spacecowboyapps.comicly.data.db.Comic
import io.spacecowboyapps.comicly.data.db.ComicDao
import io.spacecowboyapps.comicly.data.network.model.DataContainer
import io.spacecowboyapps.comicly.data.network.model.DataWrapper
import io.spacecowboyapps.comicly.data.network.model.Image
import org.junit.Before
import org.junit.Rule

@RunWith(JUnit4::class)
class ComicsRepositoryTest {

    private val time1 = System.currentTimeMillis()
    private val time2 = time1 + 500
    private val comics = listOf(
        Comic(1, "title1", Image("path1", "ext1")),
        Comic(2, "title2", Image("path2", "ext2"))
    )

    private val clientAdapter: MarvelClientAdapter = mock()
    private val comicDao: ComicDao = mock()
    private val preferences: Preferences = mock()
    private val schedulers = AppSchedulersTest.immediateSchedulers()
    private val timeProvider: TimeProvider = mock()

    private val repository = ComicsRepository(clientAdapter, comicDao, preferences, schedulers, timeProvider)

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        whenever(timeProvider.now()).thenReturn(time1, time2)
    }

    @Test
    fun `when getComics first called, then comics loaded from network`() {
        whenever(clientAdapter.getComics()).thenReturn(Single.never())
        whenever(preferences.getLastUpdate()).thenReturn(0)

        repository.getComics()

        verify(clientAdapter).getComics()
    }

    @Test
    fun `when data expired, then comics loaded from network`() {
        val expired = time1 - EXPIRATION_TIME - 1
        whenever(clientAdapter.getComics()).thenReturn(Single.never())
        whenever(preferences.getLastUpdate()).thenReturn(expired)

        repository.getComics()

        verify(clientAdapter).getComics()
    }

    @Test
    fun `when data not expired, then comics loaded from database`() {
        val expired = time1 - EXPIRATION_TIME + 1
        whenever(comicDao.getAllSingle()).thenReturn(Single.never())
        whenever(preferences.getLastUpdate()).thenReturn(expired)

        repository.getComics()

        verify(comicDao).getAllSingle()
    }

    @Test
    fun `when comics loaded from network successfully, then comics stored to database`() {
        val expired = time1 - EXPIRATION_TIME - 1
        whenever(clientAdapter.getComics()).thenReturn(Single.just(DataWrapper(DataContainer(comics))))
        whenever(preferences.getLastUpdate()).thenReturn(expired)

        repository.getComics().subscribe()

        verify(comicDao).deleteAll()
        verify(comicDao).insertAll(comics)
    }

    @Test
    fun `when comics loaded from network successfully, then update time stored to preferences`() {
        val expired = time1 - EXPIRATION_TIME - 1
        whenever(clientAdapter.getComics()).thenReturn(Single.just(DataWrapper(DataContainer(comics))))
        whenever(preferences.getLastUpdate()).thenReturn(expired)

        repository.getComics().subscribe()

        verify(preferences).putLastUpdate(time2)
    }
}