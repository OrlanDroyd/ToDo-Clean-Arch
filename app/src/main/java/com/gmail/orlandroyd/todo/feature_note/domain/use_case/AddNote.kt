package com.gmail.orlandroyd.todo.feature_note.domain.use_case

import com.gmail.orlandroyd.todo.feature_note.domain.model.InvalidNoteException
import com.gmail.orlandroyd.todo.feature_note.domain.model.Note
import com.gmail.orlandroyd.todo.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("El título no puede estar vacío")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("El contenido no puede estar vacío")
        }
        repository.insertNote(note)
    }

}