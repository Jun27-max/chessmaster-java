package xadrez.pecas;

import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez{

	public Bispo(Tabuleiro tab, Color color) {
		super(tab, color);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] movimenta() {
		boolean[][] mat = new boolean[getTab().getLinhas()][getTab().getColunas()];

		Posicao p = new Posicao(0, 0);

		// Noroeste
		p.setValues(position.getLinha() - 1, position.getColuna() - 1);
		while (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() - 1, p.getColuna() -1);
		}
		if (getTab().existeUmaPeca(p) && isOponentPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Nordeste
		p.setValues(position.getLinha() - 1, position.getColuna() + 1);
		while (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTab().existeUmaPeca(p) && isOponentPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudeste
		p.setValues(position.getLinha() + 1, position.getColuna() + 1);
		while (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTab().existeUmaPeca(p) && isOponentPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudoeste
		p.setValues(position.getLinha() + 1, position.getColuna() - 1);
		while (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTab().existeUmaPeca(p) && isOponentPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}

}
