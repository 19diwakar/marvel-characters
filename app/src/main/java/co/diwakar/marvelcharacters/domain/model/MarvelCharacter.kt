package co.diwakar.marvelcharacters.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelCharacter(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val thumbnail: ImageData?,
    val resourceURI: String? = null,
    val comics: Contents? = null,
    val series: Contents? = null,
    val stories: Contents? = null,
    val events: Contents? = null,
    val urls: List<CharacterUrl>? = null
) : Parcelable

@Parcelize
data class CharacterUrl(
    val type: String?,
    val url: String?
) : Parcelable

@Parcelize
data class Contents(
    val available: Int?,
    val collectionURI: String?,
    val items: List<Content>?,
    val returned: Int?
) : Parcelable

@Parcelize
data class Content(
    val resourceURI: String?,
    val name: String?,
    val type: String?
) : Parcelable

@Parcelize
data class ImageData(
    val path: String?,
    val extension: String?
) : Parcelable {
    fun getCompletePath(): String? {
        return if (path != null && extension != null) {
            "$path.$extension".replace("http", "https")
        } else {
            null
        }
    }
}
