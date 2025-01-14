/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {
    // Asignamos el ViewModel a nuestro fragmento
    private lateinit var viewModel: GameViewModel

    // Binding de nuestro fragmento
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )

        // Iniciamos el ViewModel y lo asignamos a nuestro fragmento
        Log.i("GameFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Set the viewmodel for databinding - this allows the bound layout access
        // to all the data in the ViewModel
        binding.gameViewModel = viewModel
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        // Observadores de datos para hacer la interfaz reactiva, de esta manera cada vez que cambie un valor se actualiza la interfaz
        /** Setting up LiveData observation relationship **/
        // Ya no se usa porque lo hemos enlanzado por la vista XML
        /*viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })*/
        // Ya no se usa porque lo hemos enlanzado por la vista XML
        /* viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
             binding.wordText.text = newWord
         })*/
        // Observer for the Game finished event
        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer<Boolean> { hasFinished ->
            if (hasFinished) gameFinished()
        })

        // eventos, los eliminamos porque hacemos el binding con la vista
        // binding.correctButton.setOnClickListener { onCorrect() }
        // binding.skipButton.setOnClickListener { onSkip() }
        // binding.endGameButton.setOnClickListener { onEndGame() }

        // metodos de inicialización - Ya no se necesitan po Reactividad
        //updateScoreText()
        // updateWordText()
        return binding.root

    }

    /** Methods for button click handlers **/

    /*   private fun onSkip() {
           viewModel.onSkip()
           // Ya nos e necesita por reactividad
           //updateWordText()
           //updateScoreText()
       }

       private fun onCorrect() {
           viewModel.onCorrect()
           // Ya nos e necesita por reactividad
           //updateWordText()
           //updateScoreText()
       }*/


    /** Methods for updating the UI **/
    /*Ya no se necesitan po Reactividad
    private fun updateWordText() {
        binding.wordText.text = viewModel.word.value
    }*/

    /*Ya no se necesitan po Reactividad
    private fun updateScoreText() {
        binding.scoreText.text = viewModel.score.value.toString()
    }*/

    // Fin de juego
    /* private fun onEndGame() {
         Log.i("GameFragment", "Called onEndGame")
         gameFinished()
     }
 */
    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        // Para tratar con el evento de fin de juego
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score.value ?: 0
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGameFinishComplete()
    }
}
