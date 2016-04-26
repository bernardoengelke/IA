package br.ufsc.ine.cco.ia.gomoku.bussines;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

	private String[][] matriz;
	private int nLinhas;
	private int nColunas;

	boolean jogadaAceita;
	private List<Movimento> jogadas;

	private Double pontuacaoMax;
	private Double pontuacaoMin;
	private String marcaAtual;

	public Tabuleiro(int nLinhas, int nColunas) {
		this.nLinhas = nLinhas;
		this.nColunas = nColunas;
		this.matriz = new String[nLinhas][nColunas];
		this.pontuacaoMax = (double) 0;
		this.pontuacaoMin = (double) 0;
		this.jogadaAceita = true;
		this.jogadas = new ArrayList<Movimento>();
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

	public void atualizar(Movimento mov, String marca, boolean jogadas) {
		if (this.matriz[mov.LINHA][mov.COLUNA].equals(".")) {
			this.jogadaAceita = true;
			this.matriz[mov.LINHA][mov.COLUNA] = marca;
			this.marcaAtual = marca;
			if (jogadas) {
				this.jogadas.add(mov);
			}
			this.atualizarPontuacao(mov);
		} else {
			this.jogadaAceita = false;
		}
	}

	private void atualizarPontuacao(Movimento ultimoMov) {
		int replaceIndex = ultimoMov.LINHA < ultimoMov.COLUNA ? ultimoMov.LINHA : ultimoMov.COLUNA;
		String marca = this.matriz[ultimoMov.LINHA][ultimoMov.COLUNA];

		String vertical = this.pegaLinhaVertical(ultimoMov);
		Double pAntVertical = this.pegaPontuacao(marca, vertical.substring(0, replaceIndex) + "." + vertical.substring(replaceIndex + 1));
		Double pVertical = this.pegaPontuacao(marca, vertical);

		String horizontal = this.pegaLinhaHorizontal(ultimoMov);
		Double pAntHorizontal = this.pegaPontuacao(marca, horizontal.substring(0, replaceIndex) + "." + horizontal.substring(replaceIndex + 1));
		Double pHorizontal = this.pegaPontuacao(marca, horizontal);

		if (marca.equals("X")) {
			this.pontuacaoMin += (((pVertical) - pAntVertical) + pHorizontal) - pAntHorizontal;
		} else {
			this.pontuacaoMax += (((pVertical) - pAntVertical) + pHorizontal) - pAntHorizontal;
		}
	}

	private Double pegaPontuacao(String marca, String linha) {
		Double cincoLivre = (this.countSequencia(marca, 5, 0, linha) * (double) 200000000000L);
		Double cincoBloqueado1 = (this.countSequencia(marca, 5, 1, linha) * (double) 2000000000L);
		Double cincoBloqueado2 = (this.countSequencia(marca, 5, 2, linha) * (double) 20000000L);

		Double quatroLivre = (this.countSequencia(marca, 4, 0, linha) * (double) 80000000);
		Double quatroBloqueado1 = (this.countSequencia(marca, 4, 1, linha) * (double) 80000);
		Double quatroBloqueado2 = (this.countSequencia(marca, 4, 2, linha) * (double) 0);

		Double tresLivre = (this.countSequencia(marca, 3, 0, linha) * (double) 400000);
		Double tresBloqueado1 = (this.countSequencia(marca, 3, 1, linha) * (double) 400);
		Double tresBloqueado2 = (this.countSequencia(marca, 3, 2, linha) * (double) 0);

		Double doisLivre = (this.countSequencia(marca, 2, 0, linha) * (double) 2000);
		Double doisBloqueado1 = (this.countSequencia(marca, 2, 1, linha) * (double) 2);
		Double doisBloqueado2 = (this.countSequencia(marca, 2, 2, linha) * (double) 0);

		return cincoLivre + cincoBloqueado1 + cincoBloqueado2 + quatroLivre + quatroBloqueado1 + quatroBloqueado2 + tresLivre + tresBloqueado1 + tresBloqueado2
				+ doisLivre + doisBloqueado1 + doisBloqueado2;
	}

	int countSequencia(String marca, int tamanho, int bloqueio, String linha) {
		int count = 0;

		String matchTemp = this.strMatch(marca, tamanho);

		if (bloqueio == 0) {
			String match = "." + matchTemp + ".";
			if (linha.contains(match)) {
				int x = linha.indexOf(match);
				while (x >= 0) {
					count++;
					x = linha.indexOf(match, match.length() + x);
				}
			}
			return count;
		} else {
			if (bloqueio == 1) {
				String match1 = this.proximaMarca(marca) + matchTemp + ".";
				String match2 = "." + match1 + this.proximaMarca(marca);

				if (linha.startsWith(matchTemp + ".")) {
					count++;
				}
				if (linha.endsWith("." + matchTemp)) {
					count++;
				}
				if (linha.contains(match1)) {
					int x = linha.indexOf(match1);
					while (x >= 0) {
						count++;
						x = linha.indexOf(match1, match1.length() + x);
					}
				}
				if (linha.contains(match2)) {
					int x = linha.indexOf(match2);
					while (x >= 0) {
						count++;
						x = linha.indexOf(match2, match2.length() + x);
					}
				}
				return count;
			} else {
				String match = this.proximaMarca(marca) + matchTemp + this.proximaMarca(marca);
				if (linha.contains(match)) {
					int x = linha.indexOf(match);
					while (x >= 0) {
						count++;
						x = linha.indexOf(match, match.length() + x);
					}
				}
			}
		}

		return count;
	}

	private String proximaMarca(String marca) {
		return marca.equals("X") ? "O" : "X";
	}

	public String proximaMarca() {
		return this.marcaAtual.equals("X") ? "O" : "X";
	}

	String strMatch(String marca, int tamanho) {
		String match = "";
		for (int i = 0; i < tamanho; i++) {
			match += marca;
		}
		return match;
	}

	public boolean jogadaAceita() {
		return this.jogadaAceita;
	}

	public Double pegaPontuacaoMax() {
		return this.pontuacaoMax;
	}

	public Double pegaPontuacaoMin() {
		return this.pontuacaoMin;
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

	@Override
	protected Object clone() {
		Tabuleiro clone = new Tabuleiro(15, 15);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				clone.matriz[i][j] = this.matriz[i][j];
			}
		}
		clone.jogadas = this.jogadas;
		clone.pontuacaoMax = this.pontuacaoMax;
		clone.pontuacaoMin = this.pontuacaoMin;
		clone.marcaAtual = this.marcaAtual;
		return clone;
	}

	public List<Movimento> pegaJogadas() {
		return this.jogadas;
	}

	public List<Movimento> pegaVizinhosLivres(Movimento movimento) {
		List<Movimento> vizinhosLivres = new ArrayList<Movimento>();
		int linha = movimento.LINHA;
		int coluna = movimento.COLUNA;

		String tempMarca;

		if ((coluna + 1) < 15) {
			coluna++;// verifica direita
			tempMarca = this.matriz[linha][coluna];
			if (tempMarca.contains(".")) {
				vizinhosLivres.add(new Movimento(linha, coluna));
			}
		}

		if ((linha - 1) > 0) {
			linha--;// verifica direita-baixo
			tempMarca = this.matriz[linha][coluna];
			if (tempMarca.contains(".")) {
				vizinhosLivres.add(new Movimento(linha, coluna));
			}
		}

		if ((coluna - 1) > 0) {
			coluna--;// verifica baixo
			tempMarca = this.matriz[linha][coluna];
			if (tempMarca.contains(".")) {
				vizinhosLivres.add(new Movimento(linha, coluna));
			}
		}

		if ((coluna - 1) > 0) {
			coluna--;// verifica esquerda-baixo
			tempMarca = this.matriz[linha][coluna];
			if (tempMarca.contains(".")) {
				vizinhosLivres.add(new Movimento(linha, coluna));
			}
		}

		if ((linha + 1) < 15) {
			linha++;// verifica esquerda
			tempMarca = this.matriz[linha][coluna];
			if (tempMarca.contains(".")) {
				vizinhosLivres.add(new Movimento(linha, coluna));
			}
		}

		if ((linha + 1) < 15) {
			linha++;// verifica esquerda-cima
			tempMarca = this.matriz[linha][coluna];
			if (tempMarca.contains(".")) {
				vizinhosLivres.add(new Movimento(linha, coluna));
			}
		}

		if ((coluna + 1) < 15) {
			coluna++;// verifica cima
			tempMarca = this.matriz[linha][coluna];
			if (tempMarca.contains(".")) {
				vizinhosLivres.add(new Movimento(linha, coluna));
			}
		}

		if ((coluna + 1) < 15) {
			coluna++;// verifica direita-cima
			tempMarca = this.matriz[linha][coluna];
			if (tempMarca.contains(".")) {
				vizinhosLivres.add(new Movimento(linha, coluna));
			}
		}

		return vizinhosLivres;
	}

}
