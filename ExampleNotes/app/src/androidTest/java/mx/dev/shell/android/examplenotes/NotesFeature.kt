package mx.dev.shell.android.examplenotes

import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import mx.dev.shell.android.examplenotes.utils.BaseUiTest
import org.junit.Test

class NotesFeature: BaseUiTest() {

    @Test
    fun test001_displayScreenTitle() {
        assertDisplayed("Notes")
    }
}