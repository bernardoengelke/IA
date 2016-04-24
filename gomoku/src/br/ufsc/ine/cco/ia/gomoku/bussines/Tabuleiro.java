package br.ufsc.ine.cco.ia.gomoku.bussines;

import br.ufsc.ine.cco.ia.gomoku.Gomoku.Movimento;

public class Tabuleiro {

	private String[][] matriz;
	private int nLinhas;
	private int nColunas;

	boolean jogadaAceita;

	public Tabuleiro(int nLinhas, int nColunas) {
		this.nLinhas = nLinhas;
		this.nColunas = nColunas;
		this.matriz = new String[nLinhas][nColunas];
		this.jogadaAceita = true;
		this.limpaMatriz();
	}

	private void limpaMatriz() {
		for (int i = 0; i < this.nLinhas; i++) {
			for (int j = 0; j < this.nColunas; j++) {
				this.matriz[i][j] = ".";
			}
		}
	}

	public boolean temVencedor(Movimento mov) {
		return VerificadorTabuleiro.ehNodoFolha(this, mov);
	}

	public void imprimir() {
		System.out.print("    1  2  3  4  5  6  7  8  9 10 11 12 13 14 15");
		System.out.print("\n");
		for (int i = 0; i < 15; i++) {
			if (i < 9) {
				System.out.print(i + 1 + "   ");
			} else {
				System.out.print(i + 1 + "  ");
			}
			for (int k = 0; k < 15; k++) {
				System.out.print(this.matriz[i][k] + "  ");
			}
			System.out.print("\n");
		}
	}

	public void atualizar(Movimento mov, String marca) {
		if (this.matriz[mov.LINHA][mov.COLUNA].equals(".")) {
			this.jogadaAceita = true;
			this.matriz[mov.LINHA][mov.COLUNA] = marca;
		} else {
			this.jogadaAceita = false;
		}
	}

	public boolean jogadaAceita() {
		return this.jogadaAceita;
	}

	public String pegaDiagonalPrimaria(Movimento mov) {
		int linha = mov.LINHA;
		int col = mov.COLUNA;
		String dPrimaria = this.matriz[linha][col];

		while ((linha > 0) && (col > 0)) {
			linha--;
			col--;
			dPrimaria = this.matriz[linha][col] + dPrimaria;
		}

		linha = mov.LINHA;
		col = mov.COLUNA;
		while ((linha < 14) && (col < 14)) {
			linha++;
			col++;
			dPrimaria = dPrimaria + this.matriz[linha][col];
		}

		return dPrimaria;
	}

	public String pegaDiagonalSecundaria(Movimento mov) {
		int linha = mov.LINHA;
		int col = mov.COLUNA;
		String dSegundaria = this.matriz[linha][col];

		while ((linha > 0) && (col < 14)) {
			linha--;
			col++;
			dSegundaria = this.matriz[linha][col] + dSegundaria;
		}

		linha = mov.LINHA;
		col = mov.COLUNA;
		while ((linha < 14) && (col > 0)) {
			linha++;
			col--;
			dSegundaria = dSegundaria + this.matriz[linha][col];
		}

		return dSegundaria;
	}

	public String pegaLinhaVertical(Movimento mov) {
		int linha = mov.LINHA;
		int col = mov.COLUNA;
		String vertical = this.matriz[linha][col];

		while (linha > 0) {
			linha--;
			vertical = this.matriz[linha][col] + vertical;
		}

		linha = mov.LINHA;
		while (linha < 14) {
			linha++;
			vertical = vertical + this.matriz[linha][col];
		}

		return vertical;
	}

	public String pegaLinhaHorizontal(Movimento mov) {
		int linha = mov.LINHA;
		int col = mov.COLUNA;
		String horizontal = this.matriz[linha][col];

		while (col > 0) {
			col--;
			horizontal = this.matriz[linha][col] + horizontal;
		}

		col = mov.COLUNA;
		while (col < 14) {
			col++;
			horizontal = horizontal + this.matriz[linha][col];
		}

		return horizontal;
	}

	public static Tabuleiro clone(Tabuleiro tabuleiro) {
		Tabuleiro clone = new Tabuleiro(15, 15);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				clone.matriz[i][j] = tabuleiro.matriz[i][j];
			}
		}
		return clone;
	}

}
