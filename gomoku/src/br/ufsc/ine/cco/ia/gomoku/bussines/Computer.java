package br.ufsc.ine.cco.ia.gomoku.bussines;

import java.util.List;

public class Computer {

	private static boolean calculoMinhaPontuacao = false;

	public static Movimento decide(Tabuleiro tabuleiro) {
		Object[] o = pegaMelhorMovimento(tabuleiro, 4, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		System.out.println(o[0]);
		return (Movimento) o[1];
	}

	private static Object[] pegaMelhorMovimento(Tabuleiro tabuleiro, int iteracao, double minhaPontuacao, double pontuacaoInimiga) {
		if (iteracao == 0) {
			Object[] x = { VerificadorTabuleiro.pegaPontuacao(tabuleiro, ehCalculoMinhaPontuacao()), null };
			return x;
		}
		List<Movimento> movimentos = VerificadorTabuleiro.pegaListaMovimentos(tabuleiro);

		Object[] temp;
		Double melhorPontuacao = minhaPontuacao;
		Movimento melhorMov = null;

		while (movimentos.size() > 0) {
			Tabuleiro novoTabuleiro = (Tabuleiro) tabuleiro.clone();
			Movimento proximoMov = movimentos.get(0);
			novoTabuleiro.atualizar(proximoMov, tabuleiro.proximaMarca(), false);
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

	private static boolean ehCalculoMinhaPontuacao() {
		calculoMinhaPontuacao = calculoMinhaPontuacao ? false : true;
		return calculoMinhaPontuacao;
	}

}
