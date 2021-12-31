package com.eugene.games.newyearsgames.ui.fragments.crocodile_new_year.create_players

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.eugene.games.newyearsgames.R
import com.eugene.games.newyearsgames.data.PlayerData
import com.eugene.games.newyearsgames.databinding.FragmentCreatePlayersBinding
import com.eugene.games.newyearsgames.ui.fragments.crocodile_new_year.SharedViewModel

private const val TAG = "CreatePlayersFragment"

class CreatePlayersFragment : Fragment(R.layout.fragment_create_players) {

    private var _binding: FragmentCreatePlayersBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCreatePlayersBinding.inflate(inflater, container, false)
        updaterTextWatcher()
        initAppPlayerButton()
        initStartGameButton()
        sharedViewModel.players.clear()
        return binding.root
    }

    private fun initAppPlayerButton() {
        var playerId = 0
        binding.addPlayers.isEnabled = false
        binding.addPlayers.setOnClickListener {
            val playerName: String = binding.gameNameEditText.text.toString()
            val textView = TextView(context)
            textView.text = playerName
            playerId += 1
            sharedViewModel.loadPlayer(PlayerData(id = playerId, namePlayer = playerName, score = 0))
            Log.e(TAG, "initAppPlayerButton: "+ sharedViewModel.getPlayer() )
            textView.textSize = 20F
            textView.setTextColor(resources.getColor(R.color.black, null))
            binding.playersListLayout.addView(textView)
            binding.gameNameEditText.text?.clear()
        }
    }

    private fun initStartGameButton() {
        binding.startGame.isEnabled = false
        binding.startGame.setOnClickListener {
            findNavController().navigate(R.id.gameFragment)
        }
    }

    private fun updaterTextWatcher() {
        binding.gameNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.addPlayers.isEnabled = binding.gameNameEditText.text?.isNotEmpty() == true
                binding.startGame.isEnabled = sharedViewModel.getPlayer().size > 1

                if (sharedViewModel.getPlayer().size > 6) {
                    binding.addPlayers.visibility = View.GONE
                    binding.textInputLayout.visibility = View.GONE
                    hideKeyboard(binding.gameNameEditText)
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideKeyboard(view: View) {
        val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}
