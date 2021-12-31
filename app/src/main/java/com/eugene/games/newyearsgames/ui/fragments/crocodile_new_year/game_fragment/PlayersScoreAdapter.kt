package com.eugene.games.newyearsgames.ui.fragments.crocodile_new_year.game_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eugene.games.newyearsgames.R
import com.eugene.games.newyearsgames.data.PlayerData
import com.eugene.games.newyearsgames.databinding.PlayersScoreItemBinding

class PlayersScoreAdapter : RecyclerView.Adapter<PlayersScoreAdapter.PlayerHolderGame>() {

    private val playerList = mutableListOf<PlayerData>()

    class PlayerHolderGame(view: View): RecyclerView.ViewHolder(view) {
        private val binding = PlayersScoreItemBinding.bind(view)
        private lateinit var playerItem: PlayerData

        fun bind(playerItem: PlayerData) {
            this.playerItem = playerItem
            binding.apply {
                playerName.text = playerItem.namePlayer
                playerScore.text = playerItem.score.toString()
            }
        }
    }

    fun updateData(playerList: List<PlayerData>) {
        this.playerList.clear()
        this.playerList.addAll(playerList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolderGame {
        return PlayerHolderGame(LayoutInflater.from(parent.context).inflate(R.layout.players_score_item, parent, false))
    }

    override fun onBindViewHolder(holder: PlayerHolderGame, position: Int) {
        holder.bind(playerList[position])
    }

    override fun getItemCount() = playerList.size
}
