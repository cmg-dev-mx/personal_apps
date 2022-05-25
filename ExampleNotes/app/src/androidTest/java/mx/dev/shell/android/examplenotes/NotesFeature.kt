package mx.dev.shell.android.examplenotes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import mx.dev.shell.android.examplenotes.utils.BaseUiTest
import org.junit.Test

class NotesFeature: BaseUiTest() {

    @Test
    fun test001_displayScreenTitle() {
        assertDisplayed("Notes")
    }

    @Test
    fun test002_displayEmptyMessage() {
        assertNotDisplayed(R.id.notes_notes_rec)
        assertDisplayed("Empty notes")
    }

    @Test
    fun test003_navigateToNewNoteScreen() {
        onView(withId(R.id.notes_add_btn)).perform(click())
        assertDisplayed(R.id.note_detail_container)
    }
}