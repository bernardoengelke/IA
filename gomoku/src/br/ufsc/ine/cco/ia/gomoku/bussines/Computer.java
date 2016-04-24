package br.ufsc.ine.cco.ia.gomoku.bussines;

import java.util.List;

import br.ufsc.ine.cco.ia.gomoku.Gomoku.Movimento;

public class Computer {

	public static void decide(Tabuleiro tabuleiro, Movimento mov) {
		mov = (Movimento) pegaMelhorMovimento(tabuleiro, 5, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)[1];
	}

	private static Object[] pegaMelhorMovimento(Tabuleiro tabuleiro, int iteracao, double minhaPontuacao, double pontuacaoInimiga) {
		List<Movimento> movimentos = VerificadorTabuleiro.pegaListaMovimentos(tabuleiro);

		if (iteracao == 0) {
			Object[] x = { VerificadorTabuleiro.pegaPontuacao(tabuleiro), movimentos.get(0) };
			return x;
		}

		Object[] temp;
		Double melhorPontuacao = minhaPontuacao;
		Movimento melhorMov = null;

		while (movimentos.size() > 0) {
			Tabuleiro novoTabuleiro = Tabuleiro.clone(tabuleiro);
			Movimento proximoMov = movimentos.get(0);
			tabuleiro.atualizar(proximoMov, "O");
			temp = pegaMelhorMovimento(novoTabuleiro, iteracao - 1, -pontuacaoInimiga, -melhorPontuacao);
			Double pontuacaoTemp = -(Double) temp[0];
			if (pontuacaoTemp > melhorPontuacao) {
				melhorPontuacao = pontuacaoTemp;
				melhorMov = proximoMov;
			}
			if (melhorPontuacao > pontuacaoInimiga) {
				Object[] x = { melhorPontuacao, melhorMov };
				return x;
			}
			movimentos.remove(0);
		}
		Object[] x = { melhorPontuacao, melhorMov };
		return x;
	}

}
