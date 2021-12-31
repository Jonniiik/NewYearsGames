package com.eugene.games.newyearsgames.ui.fragments.crocodile_new_year.game_fragment

import androidx.lifecycle.ViewModel
import com.eugene.games.newyearsgames.data.PlayerData
import com.eugene.games.newyearsgames.data.WorldStorage
import com.eugene.games.newyearsgames.data.WorldThemesData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class GameViewModel : ViewModel() {

    private val phrasesLevelOne: List<WorldThemesData> = WorldStorage.phraseListLevelOne

    private val phrasesLevelTwo: List<WorldThemesData> = WorldStorage.phraseListLevelTwo

    private val phrasesLevelThree: List<WorldThemesData> = WorldStorage.phraseListLevelThree

    val themesOne: ArrayList<WorldThemesData> = arrayListOf()
    val themesTwo: ArrayList<WorldThemesData> = arrayListOf()
    val themesThree: ArrayList<WorldThemesData> = arrayListOf()
    val playerList: ArrayList<PlayerData> = arrayListOf()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            themesOne.addAll(phrasesLevelOne)
            themesTwo.addAll(phrasesLevelTwo)
            themesThree.addAll(phrasesLevelThree)
        }
    }
}
