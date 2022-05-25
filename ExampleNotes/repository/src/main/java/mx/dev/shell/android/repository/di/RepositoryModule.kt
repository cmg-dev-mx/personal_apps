package mx.dev.shell.android.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.dev.shell.android.core.repository.NotesRepository
import mx.dev.shell.android.repository.implementation.NotesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsNotesRepository(impl: NotesRepositoryImpl): NotesRepository
}