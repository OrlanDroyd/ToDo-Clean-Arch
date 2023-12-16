package com.gmail.orlandroyd.todo.feature_note.presentation.notes

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelectable
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gmail.orlandroyd.todo.core.util.TestTags
import com.gmail.orlandroyd.todo.di.AppModule
import com.gmail.orlandroyd.todo.feature_note.presentation.MainActivity
import com.gmail.orlandroyd.todo.feature_note.presentation.util.Screen
import com.gmail.orlandroyd.todo.ui.theme.NotasNowTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest {

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
            NotasNowTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController, state = NotesState())
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Ordenar").performClick()

        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }

    @Test
    fun clickToggleOrderSection_saveSelection() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Ordenar").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Título").performClick()
        composeRule.onNodeWithContentDescription("Ascendente").performClick()
        composeRule.onNodeWithContentDescription("Ordenar").performClick()
        composeRule.onNodeWithContentDescription("Ordenar").performClick()

        composeRule.onNodeWithContentDescription("Título").assertIsSelected()
        composeRule.onNodeWithContentDescription("Ascendente").assertIsSelected()

    }
}