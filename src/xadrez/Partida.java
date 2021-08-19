package xadrez;

import java.security.InvalidParameterException;
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
	private PecaXadrez enPassant;
	private PecaXadrez promoted;

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

	public PecaXadrez getEnPassant() {
		return enPassant;
	}

	public PecaXadrez getPromoted() {
		return promoted;
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

		PecaXadrez moved = (PecaXadrez) tabuleiro.peca(targ);

		// Promoção

		promoted = null;
		if (moved instanceof Peao) {
			if ((moved.getColor() == Color.BRANCO && targ.getLinha() == 0)
					|| (moved.getColor() == Color.PRETO && targ.getLinha() == 7)) {
				promoted = (PecaXadrez)tabuleiro.peca(targ);
				promoted = replacePromotedPeca("r");
			}
		}

		check = (testCheck(oponente(currentPlayer))) ? true : false;

		if (testCheckMate(oponente(currentPlayer))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// en passant
		if (moved instanceof Peao
				&& (targ.getLinha() == busca.getLinha() - 2 || targ.getLinha() == busca.getLinha() + 2)) {
			enPassant = moved;
		} else {
			enPassant = null;
		}

		return (PecaXadrez) captura;
	}

	public PecaXadrez replacePromotedPeca(String type) {
		if (promoted == null) {
			throw new IllegalStateException("Não há peça para ser promovida. ");
		}
		if (!type.equals("B")&&!type.equals("C")&&!type.equals("T")&&!type.equals("r")) {
			throw new InvalidParameterException("Tipo inválido para promoção.");
		}
		
		Posicao pos = promoted.getXadrezPosicao().paraPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = newPeca(type, promoted.getColor());
		tabuleiro.lugarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}

	private PecaXadrez newPeca(String type, Color color) {
		if (type.equals("B")) return new Bispo(tabuleiro, color);
		if (type.equals("C")) return new Cavalo(tabuleiro, color);
		if (type.equals("T")) return new Torre(tabuleiro, color);
		return new Rainha(tabuleiro, color);
		
	}

	private Peca makeMove(Posicao busca, Posicao target) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(busca);
		p.increaseMoveCount();
		Peca captured = tabuleiro.removePeca(target);
		tabuleiro.lugarPeca(p, target);

		if (captured != null) {
			pecasNoTabuleiro.remove(captured);
			pecasCapturadas.add(captured);
		}

		// Roque Pequeno
		if (p instanceof Rei && target.getColuna() == busca.getColuna() + 2) {
			Posicao buscaOrigin = new Posicao(busca.getLinha(), busca.getColuna() + 3);
			Posicao targetDestin = new Posicao(busca.getLinha(), busca.getColuna() + 1);
			PecaXadrez rook = (PecaXadrez) tabuleiro.removePeca(buscaOrigin);
			tabuleiro.lugarPeca(rook, targetDestin);
			rook.increaseMoveCount();
		}

		// Roque Grande
		if (p instanceof Rei && target.getColuna() == busca.getColuna() - 2) {
			Posicao buscaOrigin = new Posicao(busca.getLinha(), busca.getColuna() - 4);
			Posicao targetDestin = new Posicao(busca.getLinha(), busca.getColuna() - 1);
			PecaXadrez rook = (PecaXadrez) tabuleiro.removePeca(buscaOrigin);
			tabuleiro.lugarPeca(rook, targetDestin);
			rook.increaseMoveCount();
		}

		// en passant
		if (p instanceof Peao) {
			if (busca.getColuna() != target.getColuna() && captured == null) {
				Posicao peaoPos;
				if (p.getColor() == Color.BRANCO) {
					peaoPos = new Posicao(target.getLinha() + 1, target.getColuna());
				} else {
					peaoPos = new Posicao(target.getLinha() - 1, target.getColuna());
				}
				captured = tabuleiro.removePeca(peaoPos);
				pecasCapturadas.add(captured);
				pecasNoTabuleiro.remove(captured);
			}
		}

		return captured;
	}

	private void undoMove(Posicao busca, Posicao target, Peca captured) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(target);
		p.decreaseMoveCount();
		tabuleiro.lugarPeca(p, busca);

		if (captured != null) {
			tabuleiro.lugarPeca(captured, target);
			pecasCapturadas.remove(captured);
			pecasNoTabuleiro.add(captured);
		}

		// Roque Pequeno
		if (p instanceof Rei && target.getColuna() == busca.getColuna() + 2) {
			Posicao buscaOrigin = new Posicao(busca.getLinha(), busca.getColuna() + 3);
			Posicao targetDestin = new Posicao(busca.getLinha(), busca.getColuna() + 1);
			PecaXadrez rook = (PecaXadrez) tabuleiro.removePeca(targetDestin);
			tabuleiro.lugarPeca(rook, buscaOrigin);
			rook.decreaseMoveCount();
		}

		// Roque Grande
		if (p instanceof Rei && target.getColuna() == busca.getColuna() - 2) {
			Posicao buscaOrigin = new Posicao(busca.getLinha(), busca.getColuna() - 4);
			Posicao targetDestin = new Posicao(busca.getLinha(), busca.getColuna() - 1);
			PecaXadrez rook = (PecaXadrez) tabuleiro.removePeca(targetDestin);
			tabuleiro.lugarPeca(rook, buscaOrigin);
			rook.decreaseMoveCount();
		}

		// en passant
		if (p instanceof Peao) {
			if (busca.getColuna() != target.getColuna() && captured == enPassant) {
				PecaXadrez peao = (PecaXadrez) tabuleiro.removePeca(target);
				Posicao peaoPos;
				if (p.getColor() == Color.BRANCO) {
					peaoPos = new Posicao(3, target.getColuna());
				} else {
					peaoPos = new Posicao(4, target.getColuna());
				}
				tabuleiro.lugarPeca(peao, peaoPos);

			}
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
		lugarNovaPeca('e', 1, new Rei(tabuleiro, Color.BRANCO, this));
		lugarNovaPeca('f', 1, new Bispo(tabuleiro, Color.BRANCO));
		lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Color.BRANCO));
		lugarNovaPeca('h', 1, new Torre(tabuleiro, Color.BRANCO));
		lugarNovaPeca('a', 2, new Peao(tabuleiro, Color.BRANCO, this));
		lugarNovaPeca('b', 2, new Peao(tabuleiro, Color.BRANCO, this));
		lugarNovaPeca('c', 2, new Peao(tabuleiro, Color.BRANCO, this));
		lugarNovaPeca('d', 2, new Peao(tabuleiro, Color.BRANCO, this));
		lugarNovaPeca('e', 2, new Peao(tabuleiro, Color.BRANCO, this));
		lugarNovaPeca('f', 2, new Peao(tabuleiro, Color.BRANCO, this));
		lugarNovaPeca('g', 2, new Peao(tabuleiro, Color.BRANCO, this));
		lugarNovaPeca('h', 2, new Peao(tabuleiro, Color.BRANCO, this));

		lugarNovaPeca('a', 8, new Torre(tabuleiro, Color.PRETO));
		lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Color.PRETO));
		lugarNovaPeca('c', 8, new Bispo(tabuleiro, Color.PRETO));
		lugarNovaPeca('d', 8, new Rainha(tabuleiro, Color.PRETO));
		lugarNovaPeca('e', 8, new Rei(tabuleiro, Color.PRETO, this));
		lugarNovaPeca('f', 8, new Bispo(tabuleiro, Color.PRETO));
		lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Color.PRETO));
		lugarNovaPeca('h', 8, new Torre(tabuleiro, Color.PRETO));
		lugarNovaPeca('a', 7, new Peao(tabuleiro, Color.PRETO, this));
		lugarNovaPeca('b', 7, new Peao(tabuleiro, Color.PRETO, this));
		lugarNovaPeca('c', 7, new Peao(tabuleiro, Color.PRETO, this));
		lugarNovaPeca('d', 7, new Peao(tabuleiro, Color.PRETO, this));
		lugarNovaPeca('e', 7, new Peao(tabuleiro, Color.PRETO, this));
		lugarNovaPeca('f', 7, new Peao(tabuleiro, Color.PRETO, this));
		lugarNovaPeca('g', 7, new Peao(tabuleiro, Color.PRETO, this));
		lugarNovaPeca('h', 7, new Peao(tabuleiro, Color.PRETO, this));

	}

}
