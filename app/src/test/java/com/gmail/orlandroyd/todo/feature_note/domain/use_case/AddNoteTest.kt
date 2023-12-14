package com.gmail.orlandroyd.todo.feature_note.domain.use_case

import com.gmail.orlandroyd.todo.feature_note.data.repository.FakeNoteRepository
import com.gmail.orlandroyd.todo.feature_note.domain.model.InvalidNoteException
import com.gmail.orlandroyd.todo.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteTest {

    private lateinit var addNote: AddNote
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        addNote = AddNote(fakeRepository)
    }

    @Test(expected = InvalidNoteException::class)
    fun `GIVEN a note WHEN insert into db with no title THEN throws exception`() = runBlocking {
        val note = Note("", "Content", 1, 1, 1)
        addNote(note)
    }

    @Test(expected = InvalidNoteException::class)
    fun `GIVEN a note WHEN insert into db with no content THEN throws exception`() = runBlocking {
        val note = Note("Title", "", 1, 1, 1)
        addNote(note)
    }

    @Test
    fun `GIVEN a note WHEN insert into db THEN inserted`() = runBlocking {
        val note = Note("Title", "Content", 1, 1, 1)
        addNote(note)
        val insertedNote = fakeRepository.getNoteById(1)
        assertThat(insertedNote).isEqualTo(note)
    }
}