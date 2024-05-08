package com.example.proyectofct.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description


class MainDispatcherRule (private val testDispatcher: TestDispatcher = StandardTestDispatcher()):
    TestWatcher(){

    override fun starting (desciption: Description){
        Dispatchers.setMain(testDispatcher)
    }
    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }
}