package com.eugene.games.newyearsgames.ui.fragments.crocodile_new_year.game_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.eugene.games.newyearsgames.R
import com.eugene.games.newyearsgames.data.PlayerData
import com.eugene.games.newyearsgames.databinding.FragmentGameBinding
import com.eugene.games.newyearsgames.ui.fragments.crocodile_new_year.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ThreadLocalRandom

class GameFragment : Fragment(R.layout.fragment_game) {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: GameViewModel by viewModels()

    private val playersScoreAdapter: PlayersScoreAdapter = PlayersScoreAdapter()

    private var playerScore: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        subscribePlayerList()
        initPlayersList()
        showGameView()
        initLevelButton()
        initPhrasesView()
        initPlayersSelectedList()
        initSelectedPlayersView()
        return binding.root
    }

    private fun showGameView() {
         binding.playingFieldView.playersGameLayout.visibility = View.VISIBLE
         binding.selectPlayerFieldView.selectPlayerFieldLayout.visibility = View.GONE
         binding.phraseFieldView.phraseFieldLayout.visibility = View.GONE
         binding.winnerFieldView.winnerLayout.visibility = View.GONE
     }

    private fun showPhraseView() {
        binding.playingFieldView.playersGameLayout.visibility = View.GONE
        binding.selectPlayerFieldView.selectPlayerFieldLayout.visibility = View.GONE
        binding.phraseFieldView.phraseFieldLayout.visibility = View.VISIBLE
        binding.winnerFieldView.winnerLayout.visibility = View.GONE
    }

    private fun showSelectView() {
        binding.playingFieldView.playersGameLayout.visibility = View.GONE
        binding.selectPlayerFieldView.selectPlayerFieldLayout.visibility = View.VISIBLE
        binding.phraseFieldView.phraseFieldLayout.visibility = View.GONE
        binding.winnerFieldView.winnerLayout.visibility = View.GONE
    }

    private fun showWinnerView() {
        binding.playingFieldView.playersGameLayout.visibility = View.GONE
        binding.selectPlayerFieldView.selectPlayerFieldLayout.visibility = View.GONE
        binding.phraseFieldView.phraseFieldLayout.visibility = View.GONE
        binding.winnerFieldView.winnerLayout.visibility = View.VISIBLE
    }

    private fun initPlayersList() {
        binding.playingFieldView.recyclerView.adapter = playersScoreAdapter
        playersScoreAdapter.updateData(viewModel.playerList)
    }

    private fun subscribePlayerList() {
        viewModel.playerList.addAll(sharedViewModel.getPlayer())
    }

    private fun initLevelButton() {
        binding.playingFieldView.firstLevelButton.setOnClickListener {
            playerScore = 0
            selectFirsLevelWorlds()
            showPhraseView()
            playerScore = 1
        }

        binding.playingFieldView.secondLevelButton.setOnClickListener {
            playerScore = 0
            selectSecondLevelWorlds()
            showPhraseView()
            playerScore = 2
        }

        binding.playingFieldView.thirdLevelButton.setOnClickListener {
            playerScore = 0
            selectThirdLevelWorlds()
            showPhraseView()
            playerScore = 3
        }
    }

    private fun selectFirsLevelWorlds() {
        val randomIndex = ThreadLocalRandom.current().nextInt(viewModel.themesOne.size)
        if (viewModel.themesOne.isNotEmpty()) {
            binding.phraseFieldView.gamePhrases.text = viewModel.themesOne[randomIndex].name
            viewModel.themesOne.removeAt(randomIndex)
        }
    }

    private fun selectSecondLevelWorlds() {
        val randomIndex = ThreadLocalRandom.current().nextInt(viewModel.themesTwo.size)
        if (viewModel.themesTwo.isNotEmpty()) {
            binding.phraseFieldView.gamePhrases.text = viewModel.themesTwo[randomIndex].name
            viewModel.themesTwo.removeAt(randomIndex)
        }
    }

    private fun selectThirdLevelWorlds() {
        val randomIndex = ThreadLocalRandom.current().nextInt(viewModel.themesThree.size)
        if (viewModel.themesThree.isNotEmpty()) {
            binding.phraseFieldView.gamePhrases.text = viewModel.themesThree[randomIndex].name
            viewModel.themesThree.removeAt(randomIndex)
        }
    }

    private fun initPhrasesView() {
        binding.phraseFieldView.rightAnswerButton.setOnClickListener {
            unCheckedCheckBoxElements()
            showSelectView()
        }
        binding.phraseFieldView.wrongAnswerButton.setOnClickListener {
            showGameView()
        }
    }

    private fun initPlayersSelectedList() {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in viewModel.playerList.indices) {
                val checkBox = CheckBox(context)
                checkBox.text = viewModel.playerList[i].namePlayer
                checkBox.id = viewModel.playerList[i].id
                checkBox.buttonDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.checkbox_selector)
                checkBox.textSize = 18f
                checkBox.setPadding(4,4,4,4)
                binding.selectPlayerFieldView.playersLayout.addView(checkBox)
            }
        }
    }

    private fun initSelectedPlayersView() {
        binding.selectPlayerFieldView.selectedWinnersButton.setOnClickListener {
            if (viewModel.themesOne.isEmpty())
                binding.playingFieldView.firstLevelButton.visibility = View.GONE
            if (viewModel.themesTwo.isEmpty())
                binding.playingFieldView.secondLevelButton.visibility = View.GONE
            if (viewModel.themesThree.isEmpty())
                binding.playingFieldView.thirdLevelButton.visibility = View.GONE

            if (viewModel.themesOne.isEmpty() && viewModel.themesTwo.isEmpty() && viewModel.themesThree.isEmpty()){
                checkWinPlayer()
                showWinnerView()
                binding.winnerFieldView.resultRecyclerView.adapter = playersScoreAdapter
                playersScoreAdapter.updateData(viewModel.playerList)
            } else {
                initSelectPlayersCheckBox()
                showGameView()
                playersScoreAdapter.updateData(viewModel.playerList)
            }
        }
    }

    private fun initSelectPlayersCheckBox() {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0 until binding.selectPlayerFieldView.playersLayout.childCount) {
                val child: CheckBox = binding.selectPlayerFieldView.playersLayout.getChildAt(i) as CheckBox
                if (child.isChecked) {
                    val newKey = child.id - 1
                    viewModel.playerList[newKey] = PlayerData(
                        id = viewModel.playerList[newKey].id,
                        namePlayer = viewModel.playerList[newKey].namePlayer,
                        score = viewModel.playerList[newKey].score + playerScore
                    )

                }
            }
        }
    }

    private fun unCheckedCheckBoxElements() {
        for (i in 0 until binding.selectPlayerFieldView.playersLayout.childCount) {
            val child: CheckBox = binding.selectPlayerFieldView.playersLayout.getChildAt(i) as CheckBox
            child.isChecked = false
            child.isSelected = false
        }
    }


    private fun checkWinPlayer() {
        val sortList = viewModel.playerList.sortedWith(compareBy { it.score })
        binding.winnerFieldView.winnerName.text = sortList[sortList.size - 1].namePlayer
    }
}
