package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.Partida;
import xadrez.PecaXadrez;
import xadrez.XadrezException;
import xadrez.XadrezPosicao;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Partida partida = new Partida();
		List<PecaXadrez> captura = new ArrayList<>();

		while (!partida.isCheckMate()) {
			try {
				UI.limpaTela();
				UI.printPartida(partida, captura);
				System.out.println();
				System.out.print("Busca: ");
				XadrezPosicao busca = UI.leiaPosicao(sc);
				
				boolean[][] possiveis = partida.possiveisMovimentacoes(busca);
				UI.limpaTela();
				UI.printTabuleiro(partida.getPecas(), possiveis);

				System.out.println();
				System.out.print("Target: ");
				XadrezPosicao target = UI.leiaPosicao(sc);

				PecaXadrez capturaPeca = partida.performanceXadrezMove(busca, target);
				
				if(capturaPeca != null) {
					captura.add(capturaPeca);
				}
				
				if (partida.getPromoted() != null) {
					System.out.println("Entra com a peça a ser promovida (B/C/T/r)");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B")&&!type.equals("C")&&!type.equals("T")&&!type.equals("r")) {
						System.out.println("Valor inválido. Entra com a peça a ser promovida (B/C/T/r)");
						type = sc.nextLine().toUpperCase();
					}
					partida.replacePromotedPeca(type);
				}
			} 
			catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} 
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();

			}
		}
		UI.limpaTela();
		UI.printPartida(partida, captura);
	}

}
