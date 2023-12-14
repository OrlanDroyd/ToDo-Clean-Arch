package com.gmail.orlandroyd.todo.feature_note.domain.use_case

import com.gmail.orlandroyd.todo.feature_note.data.repository.FakeNoteRepository
import com.gmail.orlandroyd.todo.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteTest {
    private lateinit var getNoteTest: GetNote
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        getNoteTest = GetNote(fakeRepository)
    }

    @Test
    fun `GIVEN a note WHEN search THEN found is correct`() = runBlocking {

        val note = Note("Title", "Content", 1, 1, 1)
        fakeRepository.insertNote(note)

        val findNote = getNoteTest(1)

        assertThat(findNote?.id).isEqualTo(note.id)

    }

    @Test
    fun `GIVEN a note WHEN search THEN not found is correct`() = runBlocking {

        val note = Note("Title", "Content", 1, 1, 1)
        fakeRepository.insertNote(note)

        val findNote = getNoteTest(0)

        assertThat(findNote).isNull()

    }

}