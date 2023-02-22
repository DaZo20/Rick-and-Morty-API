package da.zo.rickandmortyapi.common.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import da.zo.rickandmortyapi.common.db.ApplicationDatabase
import da.zo.rickandmortyapi.common.utils.getRetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Singleton
    @Provides
    fun provideRetrofit(converterFactory: Converter.Factory): Retrofit =
        getRetrofitInstance(converterFactory = converterFactory)

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create()


    @Singleton
    @Provides
    fun providesRoomDataBaseInstance(@ApplicationContext applicationContext: Context) : ApplicationDatabase = Room.databaseBuilder(
        applicationContext,
        ApplicationDatabase::class.java,"rick-and-morty-db"
    ).build()

    @Singleton
    @Provides
    fun providesFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()
}