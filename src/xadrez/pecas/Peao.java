package xadrez.pecas;

import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;
import xadrez.Color;
import xadrez.Partida;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private Partida partida;

	public Peao(Tabuleiro tab, Color color, Partida partida) {
		super(tab, color);
		this.partida = partida;

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

			// en passant Branco

			if (position.getLinha() == 3) {
				Posicao esquerda = new Posicao(position.getLinha(), position.getColuna() - 1);
				if (getTab().posicaoExiste(esquerda) && isOponentPeça(esquerda)
						&& getTab().peca(esquerda) == partida.getEnPassant()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(position.getLinha(), position.getColuna() + 1);
				if (getTab().posicaoExiste(direita) && isOponentPeça(direita)
						&& getTab().peca(direita) == partida.getEnPassant()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}
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

			// en passant Preto

			if (position.getLinha() == 4) {
				Posicao esquerda = new Posicao(position.getLinha(), position.getColuna() - 1);
				if (getTab().posicaoExiste(esquerda) && isOponentPeça(esquerda)
						&& getTab().peca(esquerda) == partida.getEnPassant()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(position.getLinha(), position.getColuna() + 1);
				if (getTab().posicaoExiste(direita) && isOponentPeça(direita)
						&& getTab().peca(direita) == partida.getEnPassant()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}

		}

		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}

}
