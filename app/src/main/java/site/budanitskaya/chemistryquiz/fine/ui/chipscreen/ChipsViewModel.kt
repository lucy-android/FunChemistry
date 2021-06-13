package site.budanitskaya.chemistryquiz.fine.ui.chipscreen

import android.text.SpannableString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import site.budanitskaya.chemistryquiz.fine.datasource.ChipsDatasource
import site.budanitskaya.chemistryquiz.fine.utils.StringFormatter
import site.budanitskaya.chemistryquiz.fine.domain.mapReactionEntitiesToReactions
import site.budanitskaya.chemistryquiz.fine.models.Reaction
import java.lang.StringBuilder

class ChipsViewModel : ViewModel() {
    val chipsDatasource by lazy {
        ChipsDatasource()
    }

    private val allReactionsList = getAllReactionsList()
    var reactionIndex = (allReactionsList.indices).random()

    var reaction = allReactionsList[reactionIndex]

    private var rawReagents =
        reaction.reagents

    private var rawProducts = reaction.products

    lateinit var currentReaction: Reaction

    var shuffledRawProducts = rawProducts.shuffled()


    var rawCorrectProducts = mutableListOf(rawProducts[0], rawProducts[1])

    var rawFirstReagent = rawReagents[0]
    var rawSecondReagent = rawReagents[1]
    lateinit var rawReagentsString: StringBuilder


    private var _reactionNumber = MutableLiveData(1)
    val reactionNumber: LiveData<Int>
        get() = _reactionNumber

    fun setNewReaction() {

        _reactionNumber.value = _reactionNumber.value?.plus(1)
        reactionIndex = (allReactionsList.indices).random()

        reaction = allReactionsList[reactionIndex]

        rawReagents = reaction.reagents

        rawProducts = reaction.products

        shuffledRawProducts = rawProducts.shuffled()

        rawCorrectProducts = mutableListOf(rawProducts[0], rawProducts[1])
        rawFirstReagent = rawReagents[0]
        rawSecondReagent = rawReagents[1]
        val rawReagentList = mutableListOf<String>()
        rawReagentList.add(rawFirstReagent)
        rawReagentList.add(rawSecondReagent)
        rawReagentsString = StringBuilder(rawReagentList.joinToString(" + ").plus(" = "))


    }

    fun superFunction() {
        val rawReagentList = mutableListOf<String>()
        rawReagentList.add(rawFirstReagent)
        rawReagentList.add(rawSecondReagent)
        rawReagentsString = StringBuilder(rawReagentList.joinToString(" + ").plus(" = "))


        val spannableCorrectProducts = mutableListOf<SpannableString>()
        rawCorrectProducts.forEach {
            spannableCorrectProducts.add(StringFormatter.formatFormula(it))
        }
    }


    fun getAllReactionsList(): List<Reaction> {
        val allReactions: List<Reaction>
        runBlocking {
            allReactions = mapReactionEntitiesToReactions(chipsDatasource.getReactionList())
        }
        return allReactions
    }
}