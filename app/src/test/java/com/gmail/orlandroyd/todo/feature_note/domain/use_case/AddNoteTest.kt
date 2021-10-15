package com.gmail.orlandroyd.todo.feature_note.domain.use_case

import com.gmail.orlandroyd.todo.feature_note.data.repository.FakeNoteRepository
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

    @Test
    fun `insert note into DB - NO TITLE, throws exception`() = runBlocking {
        val note = Note("", "Content", 1, 1, 1)
        try {
            assertThat(addNote(note)).isNull()
        } catch (e: Exception){
            println(e.message)
        }
    }

    @Test
    fun `insert note into DB - NO CONTENT, throws exception`() = runBlocking {
        val note = Note("Title", "", 1, 1, 1)
        try {
            assertThat(addNote(note)).isNull()
        } catch (e: Exception){
            println(e.message)
        }
    }

    @Test
    fun `insert note into DB - CORRECT`() = runBlocking {
        val note = Note("Title", "Content", 1, 1, 1)
        try {
            addNote(note)
        } catch (e: Exception){
            println(e.message)
        }
        val insertedNote: Note? = fakeRepository.getNoteById(1)
        assertThat(insertedNote).isEqualTo(note)
    }
}