package xadrez.pecas;

import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez {

	public Torre(Tabuleiro tab, Color color) {
		super(tab, color);
	}

	@Override
	public String toString() {
		return "T";
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

		return mat;
	}

}
