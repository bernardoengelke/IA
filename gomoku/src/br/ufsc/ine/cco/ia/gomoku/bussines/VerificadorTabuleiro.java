package br.ufsc.ine.cco.ia.gomoku.bussines;

import java.util.List;

import br.ufsc.ine.cco.ia.gomoku.Gomoku.Movimento;

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
		// TODO Auto-generated method stub
		return null;
	}

	public static Object pegaPontuacao(Tabuleiro tabuleiro) {
		// TODO Auto-generated method stub
		return null;
	}

}
