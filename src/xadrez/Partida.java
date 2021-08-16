package xadrez;

import java.util.ArrayList;
import java.util.List;

import tabuleirogame.Peca;
import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida {

	private int turn;
	private Color currentPlayer;
	private Tabuleiro tabuleiro;

	private List<Peca> pecasNoTabuleiro;
	private List<Peca> pecasCapturadas;

	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
		turn = 1;
		currentPlayer = Color.BRANCO;
		pecasNoTabuleiro = new ArrayList<>();
		pecasCapturadas = new ArrayList<>();
		iniciaPartida();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrent() {
		return currentPlayer;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	public boolean[][] possiveisMovimentacoes(XadrezPosicao sourcePosition) {
		Posicao position = sourcePosition.paraPosicao();
		validaPosicao(position);
		return tabuleiro.peca(position).movimenta();
	}

	public PecaXadrez performanceXadrezMove(XadrezPosicao busca, XadrezPosicao target) {
		Posicao source = busca.paraPosicao();
		Posicao targ = target.paraPosicao();
		validaPosicao(source);
		validaTargetPosicao(source, targ);
		Peca captura = makeMove(source, targ);
		proximoTurno();
		return (PecaXadrez) captura;
	}

	private Peca makeMove(Posicao busca, Posicao target) {
		Peca p = tabuleiro.removePeca(busca);
		Peca captured = tabuleiro.removePeca(target);
		tabuleiro.lugarPeca(p, target);

		if (captured != null) {
			pecasNoTabuleiro.remove(captured);
			pecasCapturadas.add(captured);
		}

		return captured;
	}

	private void validaPosicao(Posicao pos) {
		if (!tabuleiro.existeUmaPeca(pos)) {
			throw new XadrezException("Não existe peça na posição de origem.");
		}
		if (currentPlayer != ((PecaXadrez) tabuleiro.peca(pos)).getColor()) {
			throw new XadrezException("Não é a sua vez de jogar. ");
		}
		if (tabuleiro.peca(pos).existeMovi()) {
			throw new XadrezException("Não existe possíveis movimentos para essa peça.");
		}
	}

	private void validaTargetPosicao(Posicao source, Posicao targ) {
		if (!tabuleiro.peca(source).movi(targ)) {
			throw new XadrezException("A peça escolhida não pode ser mover para posição de destino. ");
		}
	}

	private void proximoTurno() {
		turn++;
		currentPlayer = (currentPlayer == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
	}

	private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void iniciaPartida() {
		lugarNovaPeca('c', 1, new Torre(tabuleiro, Color.BRANCO));
		lugarNovaPeca('c', 2, new Torre(tabuleiro, Color.BRANCO));
		lugarNovaPeca('d', 2, new Torre(tabuleiro, Color.BRANCO));
		lugarNovaPeca('e', 2, new Torre(tabuleiro, Color.BRANCO));
		lugarNovaPeca('e', 1, new Torre(tabuleiro, Color.BRANCO));
		lugarNovaPeca('d', 1, new Rei(tabuleiro, Color.BRANCO));

		lugarNovaPeca('c', 7, new Torre(tabuleiro, Color.PRETO));
		lugarNovaPeca('c', 8, new Torre(tabuleiro, Color.PRETO));
		lugarNovaPeca('d', 7, new Torre(tabuleiro, Color.PRETO));
		lugarNovaPeca('e', 7, new Torre(tabuleiro, Color.PRETO));
		lugarNovaPeca('e', 8, new Torre(tabuleiro, Color.PRETO));
		lugarNovaPeca('d', 8, new Rei(tabuleiro, Color.PRETO));

	}

}
