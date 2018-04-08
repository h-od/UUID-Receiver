package com.hod.uuidreceiver.data

import com.hod.uuidreceiver.BuildConfig.BASE_URL
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class DataManagerTest {

    private lateinit var dataManager: DataManager

    @Mock private lateinit var storage: Local
    @Mock private lateinit var network: Remote

    @Before
    fun setUp() {
        dataManager = DataManager(storage, network)
    }

    @Test
    fun givenFetchIsCalledReturnsEntity() {
        val nextPath = "nextPath"
        val responseCode = "responseCode"

        val entity = Entity(nextPath = BASE_URL + nextPath, responseCode = responseCode)
        val result = Observable.just(entity)

        BDDMockito.doReturn(result).`when`(network).fetch()
        BDDMockito.doReturn(result).`when`(network).fetch(nextPath)
        BDDMockito.doReturn(entity).`when`(storage).put(responseCode)

        dataManager.fetch()
                .test()
                .assertValue(entity)
    }

    @Test
    fun givenFetchPreviousIsCalledReturnsPreviousEntity() {
        val entity = Entity(responseCode =  "responseCode", count = 5)

        BDDMockito.doReturn(entity).`when`(storage).fetch()

        dataManager.fetchPrevious()
                .test()
                .assertValue(entity)
    }
}
