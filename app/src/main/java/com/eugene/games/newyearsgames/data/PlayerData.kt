package com.eugene.games.newyearsgames.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PlayerData(
    val id: Int = 0,
    val namePlayer: String = "",
    var score: Int = 0
) : Parcelable
