package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Color;
import xadrez.Partida;
import xadrez.PecaXadrez;
import xadrez.XadrezPosicao;

public class UI {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static void limpaTela() {
		System.out.print("\033[H033{2J");
		System.out.flush();
	}

	public static XadrezPosicao leiaPosicao(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new XadrezPosicao(coluna, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro de leitura de posi��o. Valido valores de a1 at� h8");
		}
	}

	public static void printPartida(Partida partida, List<PecaXadrez> captura) {
		printTabuleiro(partida.getPecas());
		System.out.println();
		printCapturaPecas(captura);
		System.out.println();
		System.out.println("Turn : " + partida.getTurn());
		if(!partida.isCheckMate()) {
			System.out.println("CHECK");
		}
		else {
			System.out.println("CHECKMATE");
			System.out.println("Vencedor: "+ partida.getCurrent());
			
		}
		
		System.out.println("Esperando jogador: " + partida.getCurrent());
		if (partida.isCheck()) {
			System.out.println("CHECK!");
		}
	}

	public static void printTabuleiro(PecaXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printTabuleiro(PecaXadrez[][] pecas, boolean[][] possiveis) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], possiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void printPeca(PecaXadrez peca, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (peca.getColor() == Color.BRANCO) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

	private static void printCapturaPecas(List<PecaXadrez> captura) {
		List<PecaXadrez> branco = captura.stream().filter(x -> x.getColor() == Color.BRANCO)
				.collect(Collectors.toList());
		List<PecaXadrez> preto = captura.stream().filter(x -> x.getColor() == Color.PRETO).collect(Collectors.toList());
		System.out.println("Pe�as Capturadas: ");
		System.out.print("Pe�as brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(branco.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Pe�as pretas: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(preto.toArray()));
		System.out.print(ANSI_RESET);
	}

}
