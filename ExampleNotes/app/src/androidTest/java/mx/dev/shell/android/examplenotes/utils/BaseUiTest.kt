package mx.dev.shell.android.examplenotes.utils

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import mx.dev.shell.android.examplenotes.flow.notes.MainActivity
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUiTest {

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)
}
