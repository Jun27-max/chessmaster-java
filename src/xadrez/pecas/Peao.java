package xadrez.pecas;

import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(Tabuleiro tab, Color color) {
		super(tab, color);

	}

	@Override
	public boolean[][] movimenta() {
		boolean[][] mat = new boolean[getTab().getLinhas()][getTab().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getColor() == Color.BRANCO) {
			p.setValues(position.getLinha() + 1, position.getColuna());
			if (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(position.getLinha() + 2, position.getColuna());
			Posicao p2 = new Posicao(position.getLinha() + 1, position.getColuna());
			if (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p) && getTab().posicaoExiste(p2)
					&& !getTab().existeUmaPeca(p2) && getMoveCount() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(position.getLinha() + 1, position.getColuna() - 1);
			if (getTab().posicaoExiste(p) && isOponentPeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(position.getLinha() + 1, position.getColuna() + 1);
			if (getTab().posicaoExiste(p) && isOponentPeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		} else {
			p.setValues(position.getLinha() + 1, position.getColuna());
			if (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(position.getLinha() + 2, position.getColuna());
			Posicao p2 = new Posicao(position.getLinha() + 1, position.getColuna());
			if (getTab().posicaoExiste(p) && !getTab().existeUmaPeca(p) && getTab().posicaoExiste(p2)
					&& !getTab().existeUmaPeca(p2) && getMoveCount() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(position.getLinha() + 1, position.getColuna() - 1);
			if (getTab().posicaoExiste(p) && isOponentPeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(position.getLinha() + 1, position.getColuna() + 1);
			if (getTab().posicaoExiste(p) && isOponentPeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

		}

		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
	
	

}
