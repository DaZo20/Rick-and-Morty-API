package da.zo.rickandmortyapi.login.domain.usecase

import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.login.domain.LoginDomainLayerContract
import da.zo.rickandmortyapi.login.domain.model.LoginUser
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

//
// Created by DaZo20 on 21/02/2023.
//
class LoginUserWithEmailAndPassUCTest {

    private lateinit var sut: LoginDomainLayerContract.PresentationLayer.UseCase<LoginUser>
    private lateinit var mockLoginRepository: LoginDomainLayerContract.DataLayer.LoginRepository


    @Before
    fun setUp() {
        mockLoginRepository =
            mock(LoginDomainLayerContract.DataLayer.LoginRepository::class.java)
        sut = LoginUserWithEmailAndPassUC(loginRepository = mockLoginRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `When 'Email' and 'Password' are valid, 'loginUser' is invoked`() = runTest {
        //Given
        val validEmail = Email(value = "test@test.com")
        val validPass = Password(value = "test1234")

        //When
        sut(email = validEmail, password = validPass)

        //Then
        verify(mockLoginRepository).loginUser(email = validEmail, password = validPass)
    }
}