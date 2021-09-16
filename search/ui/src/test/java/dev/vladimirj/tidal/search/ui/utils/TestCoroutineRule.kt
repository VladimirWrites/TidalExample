package dev.vladimirj.tidal.search.ui.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class TestCoroutineRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
    val testScope: TestCoroutineScope = TestCoroutineScope(testDispatcher)
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        testScope.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        testScope.runBlockingTest(block)
    }
}