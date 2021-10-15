package com.gmail.orlandroyd.todo.feature_note.domain.use_case

import com.gmail.orlandroyd.todo.feature_note.data.repository.FakeNoteRepository
import com.gmail.orlandroyd.todo.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteTest {
    private lateinit var deleteNote: DeleteNote
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeRepository)
    }

    @Test
    fun `search existing note, correct`() = runBlocking {
        val note = Note("Title", "Content", 1, 1, 1)
        fakeRepository.insertNote(note)
        assertThat(fakeRepository.getNoteById(1)).isEqualTo(note)
    }

    @Test
    fun `search non-existing note, is null`() = runBlocking {
        val note = Note("Title", "Content", 1, 1, 1)
        fakeRepository.insertNote(note)
        assertThat(fakeRepository.getNoteById(2)).isNull()
    }

}