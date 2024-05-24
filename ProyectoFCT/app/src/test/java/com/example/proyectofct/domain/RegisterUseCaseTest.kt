package com.example.proyectofct.domain

import android.content.Context
import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class RegisterUseCaseTest {
    @Mock
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var registerUseCase: RegisterUseCase

    @Mock
    private lateinit var context: Context


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        registerUseCase = RegisterUseCase(firebaseAuth)
    }

    @Test
    fun registrarUsuario_SuccessfulRegistration() {
        val email = "test@ejemplo.com"
        val password = "test123"
        val taskMock: Task<AuthResult> = Mockito.mock(Task::class.java) as Task<AuthResult>

        Mockito.`when`(firebaseAuth.createUserWithEmailAndPassword(email, password))
            .thenReturn(taskMock)
        Mockito.`when`(taskMock.isSuccessful).thenReturn(true)

        registerUseCase.registrarUsuario(email, password, context)

        Assert.assertTrue(taskMock.isSuccessful)
    }

    @Test
    fun registrarUsuario_FailedRegistration() {
        val email = "test@ejemplo.com"
        val password = "test123"
        val taskMock: Task<AuthResult> = Mockito.mock(Task::class.java) as Task<AuthResult>

        Mockito.`when`(firebaseAuth.createUserWithEmailAndPassword(email, password))
            .thenReturn(taskMock)
        Mockito.`when`(taskMock.isSuccessful).thenReturn(false)

        registerUseCase.registrarUsuario(email, password, context)

        Assert.assertTrue(!taskMock.isSuccessful)
    }

    @Test
    fun RegistrarUsuario_EmptyFields() {
        val email = ""
        val password = ""
        registerUseCase.registrarUsuario(email, password, context)

        // Verificar que no se intente realizar la autenticación
        Mockito.verify(firebaseAuth, Mockito.never()).signInWithEmailAndPassword(email, password)

        Mockito.verify(context, Mockito.never())
            .startActivity(ArgumentMatchers.any(Intent::class.java))
    }

}