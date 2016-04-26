package br.ufsc.ine.cco.ia.gomoku.bussines;

public class Movimento {
	public int LINHA;
	public int COLUNA;

	public Movimento() {
	}

	public Movimento(int linha, int coluna) {
		this.LINHA = linha;
		this.COLUNA = coluna;
	}

	public boolean equals(Movimento outro) {
		return (this.LINHA == outro.LINHA) && (this.COLUNA == outro.COLUNA);
	}
}
