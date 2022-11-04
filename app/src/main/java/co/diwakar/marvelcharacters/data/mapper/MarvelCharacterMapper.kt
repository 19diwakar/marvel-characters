package co.diwakar.marvelcharacters.data.mapper

import co.diwakar.marvelcharacters.data.local.MarvelCharactersListingEntity
import co.diwakar.marvelcharacters.domain.model.MarvelCharacter


fun MarvelCharactersListingEntity.toMarvelCharactersListing(): MarvelCharacter {
    return MarvelCharacter(
        id = id,
        name = name,
        description = description,
        resourceURI = resourceURI,
        modified = modified
    )
}

fun MarvelCharacter.toMarvelCharactersListingEntity(): MarvelCharactersListingEntity {
    return MarvelCharactersListingEntity(
        id = id,
        name = name,
        description = description,
        resourceURI = resourceURI,
        modified = modified
    )
}