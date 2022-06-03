package mx.dev.shell.android.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.dev.shell.android.core.usecase.NoteDetailUseCase
import mx.dev.shell.android.core.usecase.NoteDetailUseCaseImpl
import mx.dev.shell.android.core.usecase.NotesUseCase
import mx.dev.shell.android.core.usecase.NotesUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindsNotesUseCase(impl: NotesUseCaseImpl): NotesUseCase

    @Binds
    abstract fun bindsNoteDetailUseCase(impl: NoteDetailUseCaseImpl): NoteDetailUseCase
}