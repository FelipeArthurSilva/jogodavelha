package com.example.jogodavelha

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jogodavelha.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Vetor bidimensional que representa o tabuleiro de jogo
    val tabuleiro = arrayOf(
        arrayOf("A", "B", "C"),
        arrayOf("D", "E", "F"),
        arrayOf("G", "H", "I")
    )

    // Qual jogador está jogando atualmente
    var jogadorAtual = "X"

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Configuração dos cliques nos botões
        binding.buttonZero.setOnClickListener { buttonClick(it) }
        binding.buttonUm.setOnClickListener { buttonClick(it) }
        binding.buttonDois.setOnClickListener { buttonClick(it) }
        binding.buttonTres.setOnClickListener { buttonClick(it) }
        binding.buttonQuatro.setOnClickListener { buttonClick(it) }
        binding.buttonCinco.setOnClickListener { buttonClick(it) }
        binding.buttonSeis.setOnClickListener { buttonClick(it) }
        binding.buttonSete.setOnClickListener { buttonClick(it) }
        binding.buttonOito.setOnClickListener { buttonClick(it) }
    }

    // Função chamada quando um botão é clicado
    fun buttonClick(view: View) {
        val buttonSelecionado = view as Button
        buttonSelecionado.text = jogadorAtual

        // Atualiza o tabuleiro com o jogador atual
        when (buttonSelecionado.id) {
            binding.buttonZero.id -> tabuleiro[0][0] = jogadorAtual
            binding.buttonUm.id -> tabuleiro[0][1] = jogadorAtual
            binding.buttonDois.id -> tabuleiro[0][2] = jogadorAtual
            binding.buttonTres.id -> tabuleiro[1][0] = jogadorAtual
            binding.buttonQuatro.id -> tabuleiro[1][1] = jogadorAtual
            binding.buttonCinco.id -> tabuleiro[1][2] = jogadorAtual
            binding.buttonSeis.id -> tabuleiro[2][0] = jogadorAtual
            binding.buttonSete.id -> tabuleiro[2][1] = jogadorAtual
            binding.buttonOito.id -> tabuleiro[2][2] = jogadorAtual
        }

        // Atualiza a imagem de fundo do botão conforme o jogador atual
        if (jogadorAtual == "X") {
            buttonSelecionado.setBackgroundResource(R.drawable.simbolofla)
        } else {
            buttonSelecionado.setBackgroundResource(R.drawable.flu)
        }

        // Verifica se há um vencedor
        val vencedor = verificaVencedor(tabuleiro)
        if (!vencedor.isNullOrBlank()) {
            Toast.makeText(this, "Vencedor: $vencedor", Toast.LENGTH_LONG).show()
            reiniciarJogo()
            return
        }

        // Verifica empate
        if (verificaEmpate()) {
            Toast.makeText(this, "Empate", Toast.LENGTH_LONG).show()
            reiniciarJogo()
            return
        }

        // Troca o jogador
        jogadorAtual = if (jogadorAtual == "X") "O" else "X"
        if (jogadorAtual == "O") {
            maquinaJogar()
        }
    }

    // Reinicia o jogo
    private fun reiniciarJogo() {
        val intent = intent
        finish()
        startActivity(intent)
    }

    // Lógica para a máquina jogar automaticamente
    private fun maquinaJogar() {
        val posicoesVazias = mutableListOf<Pair<Int, Int>>()

        // Encontra todas as posições vazias no tabuleiro
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (tabuleiro[i][j] != "X" && tabuleiro[i][j] != "O") {
                    posicoesVazias.add(Pair(i, j))
                }
            }
        }

        // Seleciona uma posição vazia aleatoriamente
        if (posicoesVazias.isNotEmpty()) {
            val (linha, coluna) = posicoesVazias[Random.nextInt(posicoesVazias.size)]
            val button = getButton(linha, coluna)
            button.performClick()
        }
    }

    // Obtém o botão correspondente à posição no tabuleiro
    private fun getButton(row: Int, col: Int): Button {
        return when (row * 3 + col) {
            0 -> binding.buttonZero
            1 -> binding.buttonUm
            2 -> binding.buttonDois
            3 -> binding.buttonTres
            4 -> binding.buttonQuatro
            5 -> binding.buttonCinco
            6 -> binding.buttonSeis
            7 -> binding.buttonSete
            8 -> binding.buttonOito
            else -> throw IllegalArgumentException("Posição inválida")
        }
    }

    // Verifica se há um vencedor
    private fun verificaVencedor(tabuleiro: Array<Array<String>>): String? {
        // Verifica linhas e colunas
        for (i in 0 until 3) {
            if (tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2]) {
                return tabuleiro[i][0]
            }
            if (tabuleiro[0][i] == tabuleiro[1][i] && tabuleiro[1][i] == tabuleiro[2][i]) {
                return tabuleiro[0][i]
            }
        }
        // Verifica diagonais
        if (tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2]) {
            return tabuleiro[0][0]
        }
        if (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0]) {
            return tabuleiro[0][2]
        }
        return null
    }

    // Verifica se o jogo empatou
    private fun verificaEmpate(): Boolean {
        for (linha in tabuleiro) {
            for (valor in linha) {
                if (valor != "X" && valor != "O") {
                    return false
                }
            }
        }
        return true
    }
}
