package com.gmail.orlandroyd.todo.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gmail.orlandroyd.todo.core.util.TestTags
import com.gmail.orlandroyd.todo.di.AppModule
import com.gmail.orlandroyd.todo.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.gmail.orlandroyd.todo.feature_note.presentation.notes.NotesScreen
import com.gmail.orlandroyd.todo.feature_note.presentation.util.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    // To inject dependencies using Hilt:
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    // To use compose
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.NotesScreen.route
            ) {
                composable(route = Screen.NotesScreen.route) {
                    NotesScreen(navController = navController)
                }
                composable(
                    route = Screen.AddEditNoteScreen.route +
                            "?noteId={noteId}&noteColor={noteColor}",
                    arguments = listOf(
                        navArgument(
                            name = "noteId"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                        navArgument(
                            name = "noteColor"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                    )
                ) {
                    val color = it.arguments?.getInt("noteColor") ?: -1
                    AddEditNoteScreen(
                        navController = navController,
                        noteColor = color
                    )
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        // Click on FAB to get to add note screen
        composeRule.onNodeWithContentDescription("Añadir nota").performClick()

        // Enter texts in title and content text fields
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("test-title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput("test-content")
        // Save the note
        composeRule.onNodeWithContentDescription("Guardar nota").performClick()

        // Make sure that is a note in the list with our title and content
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        // Click on note to edit it
        composeRule.onNodeWithText("test-title").performClick()

        // Make sure title and content contains note title and content
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).assertTextEquals("test-title")
        // Add text "2" to the title text field
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("2")
        // Update the note
        composeRule.onNodeWithContentDescription("Guardar nota").performClick()

        // Make suer that the change was apply to the list
        composeRule.onNodeWithText("2test-title").assertIsDisplayed()
    }

    @Test
    fun saveNewNote_orderByTitleDescending() {
        for (i in 1..3) {
            // Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Añadir nota").performClick()

            // Enter texts in title and content text fields
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("$i")
            composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput("$i")
            // Save the note
            composeRule.onNodeWithContentDescription("Guardar nota").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Ordenar").performClick()
        composeRule.onNodeWithContentDescription("Título").performClick()
        composeRule.onNodeWithContentDescription("Descendente").performClick()

        composeRule.mainClock.autoAdvance = false
        composeRule.mainClock.advanceTimeBy(5000L)
        composeRule.mainClock.autoAdvance = true

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextContains("3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
            .assertTextContains("1")

    }

    @Test
    fun saveNote_isSave() {
        // Click on FAB to get to add note screen
        composeRule.onNodeWithContentDescription("Añadir nota").performClick()
        // Enter texts in title and content text fields
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput("content")
        // Save the note
        composeRule.onNodeWithContentDescription("Guardar nota").performClick()

        composeRule.onNodeWithTag(TestTags.NOTE_ITEM).assertTextContains("title")

    }
}