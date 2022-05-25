package mx.dev.shell.android.db.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.dev.shell.android.db.source.NoteDataSource
import mx.dev.shell.android.db.source.NoteDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    abstract fun bindsNoteDataSource(impl: NoteDataSourceImpl): NoteDataSource
}