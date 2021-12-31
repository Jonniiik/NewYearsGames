package com.eugene.games.newyearsgames.ui.fragments.crocodile_new_year

import androidx.lifecycle.ViewModel
import com.eugene.games.newyearsgames.data.PlayerData
import java.util.ArrayList

class SharedViewModel : ViewModel() {

    val players: ArrayList<PlayerData> = arrayListOf()

    fun getPlayer(): ArrayList<PlayerData> {
        return players
    }

    fun loadPlayer(player: PlayerData) {
        players.add(player)
    }

}
