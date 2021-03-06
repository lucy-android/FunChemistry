package site.budanitskaya.chemistryquiz.fine.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizItem(
    val text: String,
    val answers: List<String>,
    val topic: String,
    val explanation: String
): Parcelable