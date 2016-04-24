package br.ufsc.ine.cco.ia.gomoku;

import br.ufsc.ine.cco.ia.gomoku.bussines.Computer;
import br.ufsc.ine.cco.ia.gomoku.bussines.Jogador;
import br.ufsc.ine.cco.ia.gomoku.bussines.Tabuleiro;

public class Gomoku {

	private static Gomoku gomoku;
	private Tabuleiro tabuleiro;
	private Jogador j1;
	private Jogador j2;
	private Jogador ultimoJogador;

	private Gomoku() {
		this.tabuleiro = new Tabuleiro(15, 15);
		this.j1 = new Jogador("Jogador 1", "X");
		this.j2 = new Jogador("Jogador 2", "O");
		this.ultimoJogador = this.j2;
	}

	public static Gomoku pegaInstancia() {
		gomoku = gomoku == null ? new Gomoku() : gomoku;
		return gomoku;
	}

	public void iniciar() {
		Jogador proximo = null;
		Movimento mov = null;
		do {
			this.tabuleiro.imprimir();
			if (this.tabuleiro.jogadaAceita()) {
				proximo = this.pegaProximoJogador();
			} else {
				System.out.println("Ultima jogada não aceita, local já ocupado.");
			}
			mov = new Movimento();
			if (proximo.igual(null)) {
				Computer.decide(this.tabuleiro, mov);
				this.ultimoJogador = this.j2;
				proximo = this.ultimoJogador;
			} else {
				proximo.perguntarJogada(mov);
			}
			this.tabuleiro.atualizar(mov, proximo.pegaMarca());
		} while (!this.tabuleiro.temVencedor(mov));
		this.parabenizarVencedor();
	}

	private Jogador pegaProximoJogador() {
		this.ultimoJogador = this.ultimoJogador.igual(this.j2) ? this.j1 : null;
		return this.ultimoJogador;
	}

	private void parabenizarVencedor() {
		System.out.println("Parabéns " + this.ultimoJogador.pegaNome() + ", você ganhou!");
		System.out.println("Finalizando o jogo ...");
	}

	public class Movimento {
		public int LINHA;
		public int COLUNA;

		Movimento() {
		}
	}
}
