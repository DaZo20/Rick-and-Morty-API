package da.zo.rickandmortyapi.register.domain.usecase


import coil.memory.MemoryCache
import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.register.domain.RegisterDomainLayerContract
import da.zo.rickandmortyapi.register.domain.model.RegisterUser
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

//
// Created by DaZo20 on 21/02/2023.
//
class CreateUserWithEmailAndPassUCTest {

    private lateinit var sut: RegisterDomainLayerContract.PresentationLayer.UseCase<RegisterUser>
    private lateinit var mockRegisterRepository: RegisterDomainLayerContract.DataLayer.RegisterRepository

    @Before
    fun setUp() {
        mockRegisterRepository =
            mock(RegisterDomainLayerContract.DataLayer.RegisterRepository::class.java)
        sut = CreateUserWithEmailAndPassUC(registerRepository = mockRegisterRepository)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun `when 'Email' and 'Password' ar valid, 'registerUser' is invoked`() = runTest {
        //Given
        val validEmail = Email(value = "test@test.com")
        val validPass = Password(value = "test1234")
        //When
        sut(email = validEmail , password = validPass)
        //Then
        verify(mockRegisterRepository).registerUser(validEmail, validPass)
    }


}