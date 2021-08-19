package xadrez.pecas;

import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;
import xadrez.Color;
import xadrez.Partida;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	private Partida partida;

	public Rei(Tabuleiro tab, Color color, Partida partida) {
		super(tab, color);
		this.partida = partida;

	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean canMove(Posicao position) {
		PecaXadrez p = (PecaXadrez) getTab().peca(position);
		return p == null || p.getColor() != getColor();
	}

	private boolean testeReiCastling(Posicao pos) {
		PecaXadrez p = (PecaXadrez) getTab().peca(pos);
		return p != null && p instanceof Rei && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] movimenta() {
		boolean[][] mat = new boolean[getTab().getLinhas()][getTab().getColunas()];

		Posicao p = new Posicao(0, 0);

		// Acima
		p.setValues(position.getLinha() - 1, position.getColuna());
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Abaixo
		p.setValues(position.getLinha() + 1, position.getColuna());
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Esquerda
		p.setValues(position.getLinha(), position.getColuna() - 1);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Direita
		p.setValues(position.getLinha(), position.getColuna() + 1);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// noroeste
		p.setValues(position.getLinha() - 1, position.getColuna() - 1);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nordeste
		p.setValues(position.getLinha() - 1, position.getColuna() + 1);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudoeste
		p.setValues(position.getLinha() + 1, position.getColuna() + 1);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudeste
		p.setValues(position.getLinha() + 1, position.getColuna() + 1);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Movimento Castling

		if (getMoveCount() == 0 && !partida.isCheck()) {
			// Roque pequeno
			Posicao posT1 = new Posicao(position.getLinha(), position.getColuna() + 3);
			if (testeReiCastling(posT1)) {
				Posicao p1 = new Posicao(position.getLinha(), position.getColuna() + 1);
				Posicao p2 = new Posicao(position.getLinha(), position.getColuna() + 2);
				if (getTab().peca(p1) == null && getTab().peca(p2) == null) {
					mat[position.getLinha()][position.getColuna() + 2] = true;
				}
			}

			// Roque Grande
			Posicao posT2 = new Posicao(position.getLinha(), position.getColuna() - 4);
			if (testeReiCastling(posT2)) {
				Posicao p1 = new Posicao(position.getLinha(), position.getColuna() - 1);
				Posicao p2 = new Posicao(position.getLinha(), position.getColuna() - 2);
				Posicao p3 = new Posicao(position.getLinha(), position.getColuna() - 3);
				if (getTab().peca(p1) == null && getTab().peca(p2) == null && getTab().peca(p3) == null) {
					mat[position.getLinha()][position.getColuna() - 2] = true;
				}
			}
		}

		return mat;
	}

}
