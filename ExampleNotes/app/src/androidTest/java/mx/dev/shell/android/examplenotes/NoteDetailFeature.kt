package mx.dev.shell.android.examplenotes

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import mx.dev.shell.android.examplenotes.utils.BaseUiTest
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class NoteDetailFeature: BaseUiTest() {

    @Test
    fun test001_createNewNote() {
        onView(withId(R.id.notes_add_btn)).perform(click())
        onView(withId(R.id.noteDetail_title_edt)).perform(clearText(), typeText("Test title 1"))
        onView(withId(R.id.noteDetail_content_edt)).perform(clearText(), typeText("Test content 1"), closeSoftKeyboard())
        onView(withId(R.id.noteDetail_save_btn)).perform(click())
        assertDisplayed("Test title 1")
    }

    @Test
    fun test002_createSecondNote() {
        onView(withId(R.id.notes_add_btn)).perform(click())
        onView(withId(R.id.noteDetail_title_edt)).perform(clearText(), typeText("Test title 2"))
        onView(withId(R.id.noteDetail_content_edt)).perform(clearText(), typeText("Test content 2"), closeSoftKeyboard())
        onView(withId(R.id.noteDetail_save_btn)).perform(click())
        assertDisplayed("Test title 1")
        assertDisplayed("Test title 2")
    }

    @Test
    fun test003_updateFirstNote() {
        onView(withId(R.id.notes_notes_rec)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )

        onView(withId(R.id.noteDetail_title_edt)).perform(clearText(), typeText("Test title 2 updated"))
        onView(withId(R.id.noteDetail_content_edt)).perform(clearText(), typeText("Test content 2 updated"), closeSoftKeyboard())
        onView(withId(R.id.noteDetail_save_btn)).perform(click())
        assertDisplayed("Test title 2 updated")
    }
}