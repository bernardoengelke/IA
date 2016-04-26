package br.ufsc.ine.cco.ia.gomoku.bussines;

import java.util.ArrayList;
import java.util.List;

public class VerificadorTabuleiro {

	private final static String CONDICAO_VENCER = "(.|0|X)*(XXXXX|00000)(.|0|X)*";

	public static boolean ehNodoFolha(Tabuleiro tabuleiro, Movimento utlimoMov) {
		String dPrimaria = tabuleiro.pegaDiagonalPrimaria(utlimoMov);
		String dSegundaria = tabuleiro.pegaDiagonalSecundaria(utlimoMov);

		String vertical = tabuleiro.pegaLinhaVertical(utlimoMov);
		String horizontal = tabuleiro.pegaLinhaHorizontal(utlimoMov);

		//@formatter:off
		return dPrimaria.matches(CONDICAO_VENCER)   ||
				dSegundaria.matches(CONDICAO_VENCER)  ||
				vertical.matches(CONDICAO_VENCER)     ||
				horizontal.matches(CONDICAO_VENCER);
		//@formatter:on
	}

	public static List<Movimento> pegaListaMovimentos(Tabuleiro tabuleiro) {
		List<Movimento> movimentosCandidatos = new ArrayList<Movimento>();

		List<Movimento> jogadas = tabuleiro.pegaJogadas();
		for (Movimento movimento : jogadas) {
			for (Movimento vizinho : tabuleiro.pegaVizinhosLivres(movimento)) {
				boolean add = true;
				for (Movimento candidato : movimentosCandidatos) {
					if (candidato.equals(vizinho)) {
						add = false;
						break;
					}
				}
				if (add) {
					movimentosCandidatos.add(vizinho);
				}
			}
		}

		return movimentosCandidatos;
	}

	public static Object pegaPontuacao(Tabuleiro tabuleiro, boolean ehComputador) {
		return ehComputador ? tabuleiro.pegaPontuacaoMax() - tabuleiro.pegaPontuacaoMin() : tabuleiro.pegaPontuacaoMin() - tabuleiro.pegaPontuacaoMax();
	}

}
