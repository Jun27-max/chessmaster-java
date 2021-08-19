package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleirogame.Peca;
import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida {

	private int turn;
	private Color currentPlayer;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;

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

	public boolean isCheck() {
		return check;
	}

	public boolean isCheckMate() {
		return checkMate;
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

		if (testCheck(currentPlayer)) {
			undoMove(source, targ, captura);
			throw new XadrezException("Você não pode se colocar em Check");
		}

		check = (testCheck(oponente(currentPlayer))) ? true : false;

		if (testCheckMate(oponente(currentPlayer))) {
			checkMate = true;
		} else {
			proximoTurno();
		}
		return (PecaXadrez) captura;
	}

	private Peca makeMove(Posicao busca, Posicao target) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(busca);
		p.increaseMoveCount();
		Peca captured = tabuleiro.removePeca(target);
		tabuleiro.lugarPeca(p, target);

		if (captured != null) {
			pecasNoTabuleiro.remove(captured);
			pecasCapturadas.add(captured);
		}

		return captured;
	}

	private void undoMove(Posicao busca, Posicao target, Peca captured) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(target);
		p.decreaseMoveCount();
		tabuleiro.lugarPeca(p, busca);

		if (captured != null) {
			tabuleiro.lugarPeca(captured, target);
			pecasCapturadas.remove(captured);
			pecasNoTabuleiro.add(captured);
		}

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

	private Color oponente(Color color) {
		return (color == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
	}

	private PecaXadrez rei(Color color) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getColor() == color)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe" + color + "o Rei no tabuleiro");
	}

	private boolean testCheck(Color color) {
		Posicao posicaoRei = rei(color).getXadrezPosicao().paraPosicao();
		List<Peca> oponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getColor() == oponente(color))
				.collect(Collectors.toList());
		for (Peca p : oponente) {
			boolean[][] mat = p.movimenta();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getColor() == color)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimenta();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao source = ((PecaXadrez) p).getXadrezPosicao().paraPosicao();
						Posicao target = new Posicao(i, j);
						Peca capturado = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturado);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;

	}

	private void iniciaPartida() {
		lugarNovaPeca('a', 1, new Torre(tabuleiro, Color.BRANCO));
		lugarNovaPeca('b', 1, new Cavalo(tabuleiro, Color.BRANCO));
		lugarNovaPeca('c', 1, new Bispo(tabuleiro, Color.BRANCO));
		lugarNovaPeca('d', 1, new Rainha(tabuleiro, Color.BRANCO));
        lugarNovaPeca('e', 1, new Rei(tabuleiro, Color.BRANCO));
        lugarNovaPeca('f', 1, new Bispo(tabuleiro, Color.BRANCO));
        lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Color.BRANCO));
        lugarNovaPeca('h', 1, new Torre(tabuleiro, Color.BRANCO));
        lugarNovaPeca('a', 2, new Peao(tabuleiro, Color.BRANCO));
        lugarNovaPeca('b', 2, new Peao(tabuleiro, Color.BRANCO));
        lugarNovaPeca('c', 2, new Peao(tabuleiro, Color.BRANCO));
        lugarNovaPeca('d', 2, new Peao(tabuleiro, Color.BRANCO));
        lugarNovaPeca('e', 2, new Peao(tabuleiro, Color.BRANCO));
        lugarNovaPeca('f', 2, new Peao(tabuleiro, Color.BRANCO));
        lugarNovaPeca('g', 2, new Peao(tabuleiro, Color.BRANCO));
        lugarNovaPeca('h', 2, new Peao(tabuleiro, Color.BRANCO));
                                       
        lugarNovaPeca('a', 8, new Torre(tabuleiro, Color.PRETO));
        lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Color.PRETO));
        lugarNovaPeca('c', 8, new Bispo(tabuleiro, Color.PRETO));
        lugarNovaPeca('d', 8, new Rainha(tabuleiro, Color.PRETO));
        lugarNovaPeca('e', 8, new Rei(tabuleiro, Color.PRETO));
        lugarNovaPeca('f', 8, new Bispo(tabuleiro, Color.PRETO));
        lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Color.PRETO));
        lugarNovaPeca('h', 8, new Torre(tabuleiro, Color.PRETO));
        lugarNovaPeca('a', 7, new Peao(tabuleiro, Color.PRETO));
        lugarNovaPeca('b', 7, new Peao(tabuleiro, Color.PRETO));
        lugarNovaPeca('c', 7, new Peao(tabuleiro, Color.PRETO));
        lugarNovaPeca('d', 7, new Peao(tabuleiro, Color.PRETO));
        lugarNovaPeca('e', 7, new Peao(tabuleiro, Color.PRETO));
        lugarNovaPeca('f', 7, new Peao(tabuleiro, Color.PRETO));
        lugarNovaPeca('g', 7, new Peao(tabuleiro, Color.PRETO));
        lugarNovaPeca('h', 7, new Peao(tabuleiro, Color.PRETO));
                                                      
	}

}
