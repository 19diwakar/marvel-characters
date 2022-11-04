package co.diwakar.marvelcharacters.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MarvelCharactersListingEntity(
    @PrimaryKey val id: Int? = null,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
)