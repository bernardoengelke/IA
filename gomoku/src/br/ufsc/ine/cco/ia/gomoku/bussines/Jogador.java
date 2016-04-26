package br.ufsc.ine.cco.ia.gomoku.bussines;

import java.util.Scanner;

public class Jogador {

	private String nome;
	private String marca;

	public Jogador(String nome, String marca) {
		this.nome = nome;
		this.marca = marca;
	}

	public String pegaNome() {
		return this.nome;
	}

	public String pegaMarca() {
		return this.marca;
	}

	@SuppressWarnings("resource")
	public void perguntarJogada(Movimento mov) {
		String jogada = null;
		Boolean jogadaAceita = false;
		while (!jogadaAceita) {
			Scanner in = new Scanner(System.in);
			System.out.println(this.nome + " qual a sua jogada (Em caso de dúvida digite help)?");
			System.out.print(">>: ");
			jogada = in.nextLine();
			if (jogada.equals("$q")) {
				System.out.println("Saíndo do jogo...");
				System.exit(0);
			}
			if (jogada.equals("help")) {
				System.out.println("*Para entrar com uma jogada digite $nLinha,nColuna.");
				System.out.println("\tOnde 'nLinha' é o número da linha desejada e 'nColuna' é o número da coluna desejada.");
				System.out.println("*Para sair do jogo digite $q.\n");
			} else {
				jogadaAceita = jogada.matches("\\$([1-9]|1[0-5]),(1[0-5]|[1-9])");
				if (!jogadaAceita) {
					System.out.println("Jogada não aceita!");
				}
			}
		}
		mov.LINHA = Integer.parseInt(jogada.split(",")[0].substring(1)) - 1;
		mov.COLUNA = Integer.parseInt(jogada.split(",")[1]) - 1;
	}

	public boolean igual(Jogador j) {
		return (j != null) && this.nome.equals(j.pegaNome()) && this.marca.equals(j.pegaMarca());
	}

}
