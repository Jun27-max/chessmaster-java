package xadrez.pecas;

import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {

	public Rainha(Tabuleiro tab, Color color) {
		super(tab, color);
	}

	@Override
	public String toString() {
		return "r";
	}

	@Override
	public boolean[][] movimenta() {
		boolean[][] mat = new boolean[getTab().getLinhas()][getTab().getColunas()];

		Posicao p = new Posicao(0, 0);

		// Acima
		p.setValues(position.getLinha() - 1, position.getColuna());
		while (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTab().existeUmaPeca(p) && isOponentPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// esquerda
		p.setValues(position.getLinha(), position.getColuna() - 1);
		while (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTab().existeUmaPeca(p) && isOponentPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// direita
		p.setValues(position.getLinha(), position.getColuna() + 1);
		while (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getColuna() + 1);
		}
		if (getTab().existeUmaPeca(p) && isOponentPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Abaixo
		p.setValues(position.getLinha() + 1, position.getColuna());
		while (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTab().existeUmaPeca(p) && isOponentPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Noroeste
		p.setValues(position.getLinha() - 1, position.getColuna() - 1);
		while (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() - 1, p.getColuna() - 1);
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
