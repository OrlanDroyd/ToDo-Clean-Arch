package com.gmail.orlandroyd.todo.feature_note.presentation.notes

import app.cash.turbine.test
import com.gmail.orlandroyd.todo.MainDispatcherRule
import com.gmail.orlandroyd.todo.feature_note.domain.model.Note
import com.gmail.orlandroyd.todo.feature_note.domain.use_case.NoteUseCases
import com.gmail.orlandroyd.todo.feature_note.domain.util.NoteOrder
import com.gmail.orlandroyd.todo.feature_note.domain.util.OrderType
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NotesViewModelTest {

    // Mocks
    @RelaxedMockK
    private lateinit var noteUseCases: NoteUseCases

    // ViewModel
    private lateinit var viewModel: NotesViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = NotesViewModel(noteUseCases)
    }

    @Test
    fun `getNotes should update state when invoked`() = runTest {
        val noteOrder = NoteOrder.Date(OrderType.Descending)
        val notes = listOf(
            Note(title = "1", content = "duis", timestamp = 1, color = 5768, id = 1),
            Note(title = "2", content = "facilis", timestamp = 2, color = 8060, id = 2),
            Note(title = "3", content = "eum", timestamp = 3, color = 7038, id = 3)
        )
        coEvery { noteUseCases.getNotes(noteOrder) } returns flowOf(notes)

        viewModel.state.test {
            assertFalse(awaitItem().isOrderSectionVisible)
            viewModel.getNotes(noteOrder)
            assertTrue(awaitItem().notes == notes)
            cancelAndIgnoreRemainingEvents()
        }

    }

//    @Test
//    fun `onEvent with NotesEvent Order should invoke getNotes`() {
//        val noteOrder = NoteOrder.Title(OrderType.Ascending)
//        val currentState = NotesState(noteOrder = NoteOrder.Date(OrderType.Descending))
//        val notes = listOf(
//            Note(title = "1", content = "duis", timestamp = 1, color = 5768, id = 1),
//            Note(title = "2", content = "facilis", timestamp = 2, color = 8060, id = 2),
//            Note(title = "3", content = "eum", timestamp = 3, color = 7038, id = 3)
//        )
//        coEvery { noteUseCases.getNotes(noteOrder) } returns flowOf(notes)
//
//        viewModel.setState(currentState)
//
//        viewModel.onEvent(NotesEvent.Order(noteOrder))
//
//        verify { viewModel.getNotes(noteOrder) }
//    }
//
//    @Test
//    fun `onEvent with NotesEvent Order should not invoke getNotes if order is the same`() {
//        val noteOrder = NoteOrder.Date(OrderType.Descending)
//        val currentState = NotesState(noteOrder = noteOrder)
//        viewModel.setState(currentState)
//
//        viewModel.onEvent(NotesEvent.Order(noteOrder))
//
//        verify { viewModel.getNotes(noteOrder) }
//    }

    @Test
    fun `onEvent with NotesEvent DeleteNote should delete note and update recentlyDeletedNote`() =
        runTest {
            val note = Note(
                title = "eum",
                content = "curabitur",
                timestamp = 9269,
                color = 5338,
                id = 1
            )
            coEvery { noteUseCases.deleteNote(note) } just Runs
            val event = NotesEvent.DeleteNote(note)

            viewModel.onEvent(event)

            coVerify { noteUseCases.deleteNote(note) }
            assert(viewModel.recentlyDeletedNote == note)
        }

    @Test
    fun `onEvent with NotesEvent RestoreNote should add note and clear recentlyDeletedNote`() =
        runTest {
            val note = Note(
                title = "mollis",
                content = "omittantur",
                timestamp = 4064,
                color = 9182,
                id = 1
            )
            coEvery { noteUseCases.addNote(note) } just Runs
            viewModel.recentlyDeletedNote = note
            val event = NotesEvent.RestoreNote

            viewModel.onEvent(event)

            coVerify { noteUseCases.addNote(note) }
            assert(viewModel.recentlyDeletedNote == null)
        }

    @Test
    fun `onEvent with NotesEvent ToggleOrderSection should toggle isOrderSectionVisible`() {
        val currentState = NotesState(isOrderSectionVisible = false)
        viewModel.setState(currentState)
        val event = NotesEvent.ToggleOrderSection

        viewModel.onEvent(event)

        assert(viewModel.state.value.isOrderSectionVisible)
    }
}