package com.example.proyectofct.domain

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.MockitoAnnotations
class LoginUseCaseTest {

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loginUseCase: LoginUseCase

    @Mock
    private lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        loginUseCase = LoginUseCase(firebaseAuth)
    }

    @Test
    fun iniciarUsuario_SuccessfulLogin() {
        val email = "test@example.com"
        val password = "test123"
        val taskMock: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>
        `when`(taskMock.isSuccessful).thenReturn(true)
        `when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(taskMock)

        loginUseCase.iniciarUsuario(email, password, context)

        assertTrue(taskMock.isSuccessful)
    }

    @Test
    fun iniciarUsuario_FailedLogin() {
        val email = "test@example.com"
        val password = "invalidPassword"
        val taskMock: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>
        `when`(taskMock.isSuccessful).thenReturn(false)
        `when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(taskMock)

        loginUseCase.iniciarUsuario(email, password, context)

        // Verificar que no se haya iniciado la actividad de inicio
        verify(context, never()).startActivity(any(Intent::class.java))
    }

    @Test
    fun iniciarUsuario_EmptyFields() {
        val email = ""
        val password = ""
        loginUseCase.iniciarUsuario(email, password, context)

        // Verificar que no se intente realizar la autenticación
        verify(firebaseAuth, never()).signInWithEmailAndPassword(email, password)

        verify(context, never()).startActivity(any(Intent::class.java))
    }


}
