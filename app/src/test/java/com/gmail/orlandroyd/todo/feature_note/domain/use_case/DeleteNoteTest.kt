package com.gmail.orlandroyd.todo.feature_note.domain.use_case

import com.gmail.orlandroyd.todo.feature_note.data.repository.FakeNoteRepository
import com.gmail.orlandroyd.todo.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class DeleteNoteTest {

    private lateinit var deleteNote: DeleteNote
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeRepository)
    }

    @Test
    fun `GIVEN a note WHEN delete existing note THEN correct`() = runBlocking {

        val note = Note("Title", "Content", 1, 1, 1)
        fakeRepository.insertNote(note)

        deleteNote(note)

        assertThat(fakeRepository.getNoteById(1)).isNotEqualTo(note)

    }

    @Test
    fun `GIVEN a note WHEN delete non existing note THEN correct`() = runBlocking {

        val note = Note("Title", "Content", 1, 1, 1)
        fakeRepository.deleteNote(note)

        deleteNote(note)

        assertThat(fakeRepository.getNoteById(1)).isNotEqualTo(note)

    }

}