package com.tek.aircraftsoverczechrepublic.domain.use_case.get_aircrafts

import com.tek.aircraftsoverczechrepublic.data.repository.FakeAircraftRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.tek.aircraftsoverczechrepublic.common.Resource
import com.tek.aircraftsoverczechrepublic.common.ResponseType
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toSet

class GetAircraftUseCaseTest {

    private lateinit var getAircraftUseCase: GetAircraftUseCase
    private lateinit var fakeAircraftRepositoryImpl: FakeAircraftRepositoryImpl

    @Test
    fun `Get Aircraft List, correct`(): Unit = runBlocking {
        fakeAircraftRepositoryImpl = FakeAircraftRepositoryImpl(ResponseType.SUCCESS)
        getAircraftUseCase = GetAircraftUseCase(fakeAircraftRepositoryImpl)
        val a = getAircraftUseCase.invoke().take(2).toSet()
        assertThat(a.elementAt(0)).isInstanceOf(Resource.Loading::class.java)
        assertThat(a.elementAt(1)).isInstanceOf(Resource.Success::class.java)

    }

    @Test
    fun `Get Aircraft List, http exception`(): Unit = runBlocking {
        fakeAircraftRepositoryImpl = FakeAircraftRepositoryImpl(ResponseType.HTTP_ERROR)
        getAircraftUseCase = GetAircraftUseCase(fakeAircraftRepositoryImpl)
        val a = getAircraftUseCase.invoke().take(2).toSet()
        assertThat(a.elementAt(0)).isInstanceOf(Resource.Loading::class.java)
        assertThat(a.elementAt(1)).isInstanceOf(Resource.Error::class.java)
        assertThat(a.elementAt(1).message).isEqualTo("HTTP 500 Response.error()")

    }

    @Test
    fun `Get Aircraft List, io exception`(): Unit = runBlocking {
        fakeAircraftRepositoryImpl = FakeAircraftRepositoryImpl(ResponseType.IO_ERROR)
        getAircraftUseCase = GetAircraftUseCase(fakeAircraftRepositoryImpl)
        val a = getAircraftUseCase.invoke().take(2).toSet()
        assertThat(a.elementAt(0)).isInstanceOf(Resource.Loading::class.java)
        assertThat(a.elementAt(1)).isInstanceOf(Resource.Error::class.java)
        assertThat(a.elementAt(1).message).isEqualTo("You look offline. Check your internet connection.")

    }
}