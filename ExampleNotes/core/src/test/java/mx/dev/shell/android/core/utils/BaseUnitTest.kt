package mx.dev.shell.android.core.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mx.dev.shell.android.examplenotes.utils.MainCoroutineScopeRule
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseUnitTest {

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
}