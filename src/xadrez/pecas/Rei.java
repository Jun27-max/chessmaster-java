package xadrez.pecas;

import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	public Rei(Tabuleiro tab, Color color) {
		super(tab, color);

	}
	
	

	@Override
	public String toString() {
		return "R";
	}

	private boolean canMove(Posicao position) {
		PecaXadrez p = (PecaXadrez) getTab().peca(position);
		return p == null || p.getColor() != getColor();
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

		return mat;
	}

}
