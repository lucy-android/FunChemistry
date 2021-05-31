package site.budanitskaya.chemistryquiz.fine.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Int,
    val name: String,
    val drawable: Int
) : Parcelable