package com.hod.uuidreceiver.ui

import com.hod.uuidreceiver.data.DataContract
import com.hod.uuidreceiver.data.Entity
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MainPresenterTest {

    private lateinit var presenter: MainPresenter

    private val dataManager = mock<DataContract>()
    private val view = mock<MainPresenter.View>()

    private val fetchClicks: PublishSubject<Unit> = PublishSubject.create()
    private val dismissError: PublishSubject<Unit> = PublishSubject.create()

    @Before
    fun setUp() {
        presenter = MainPresenter(dataManager, Schedulers.trampoline(), Schedulers.trampoline())

        Mockito.doReturn(fetchClicks).`when`(view).fetchClicked()
        Mockito.doReturn(dismissError).`when`(view).errorDismissed()
    }

    @Test
    fun `when onViewAttached called fetches previous`() {
        val responseCode = "response code"
        val count = 5
        val previousEntity = Single.just(Entity(responseCode = responseCode, count = count))

        Mockito.doReturn(previousEntity).`when`(dataManager).fetchPrevious()

        presenter.onViewAttached(view)

        Mockito.verify(view).showCount(eq(count))
        Mockito.verify(view).showResponseCode(eq(responseCode))
    }

    @Test
    fun `when onViewAttached called with blank responseCode and non positive count calls showNoResponseCode`() {
        val responseCode = ""
        val count = 0
        val previousEntity = Single.just(Entity(responseCode = responseCode, count = count))

        Mockito.doReturn(previousEntity).`when`(dataManager).fetchPrevious()

        presenter.onViewAttached(view)

        Mockito.verify(view).showNoResponseCode()
        Mockito.verify(view, never()).showCount(eq(count))
        Mockito.verify(view, never()).showResponseCode(eq(responseCode))
    }

    @Test
    fun `when error occurs calls showError`() {
        val errorMessage = "errorMessage"
        val previousEntity = Single.error<Exception>(Exception(errorMessage))

        Mockito.doReturn(previousEntity).`when`(dataManager).fetchPrevious()

        presenter.onViewAttached(view)

        Mockito.verify(view).showError(eq(errorMessage))
    }

    @Test
    fun `when fetch clicked returns Entity`() {

        val previousResponseCode = "previousResponseCode"
        val previousCount = 5
        val previousEntity = Single.just(Entity(responseCode = previousResponseCode, count = previousCount))

        val responseCode = "response code"
        val count = 6
        val entity = Observable.just(Entity(responseCode = responseCode, count = count))

        Mockito.doReturn(previousEntity).`when`(dataManager).fetchPrevious()
        Mockito.doReturn(entity).`when`(dataManager).fetch()

        presenter.onViewAttached(view)

        fetchClicks.onNext(Unit)

        Mockito.verify(dataManager).fetch()
        Mockito.verify(view).showCount(eq(previousCount))
        Mockito.verify(view).showResponseCode(eq(previousResponseCode))
        Mockito.verify(view).showCount(eq(count))
        Mockito.verify(view).showResponseCode(eq(responseCode))
    }

    @Test
    fun `when error dismissed resubscribes for clicks`() {

        val previousResponseCode = "previousResponseCode"
        val previousCount = 5
        val previousEntity = Single.just(Entity(responseCode = previousResponseCode, count = previousCount))

        Mockito.doReturn(previousEntity).`when`(dataManager).fetchPrevious()

        presenter.onViewAttached(view)

        dismissError.onNext(Unit)
        fetchClicks.onNext(Unit)

        Mockito.verify(dataManager, Mockito.times(2)).fetch()
    }
}
