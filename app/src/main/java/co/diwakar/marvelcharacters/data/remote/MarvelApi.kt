package co.diwakar.marvelcharacters.data.remote

import co.diwakar.marvelcharacters.domain.model.MarvelCharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nameStartsWith") query: String?,
    ): MarvelCharactersResponse

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: Int
    ): MarvelCharactersResponse

    companion object {
        const val BASE_URL = "http://gateway.marvel.com"
    }
}