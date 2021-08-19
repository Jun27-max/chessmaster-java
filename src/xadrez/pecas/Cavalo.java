package xadrez.pecas;

import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez{
	
	public Cavalo(Tabuleiro tab, Color color) {
		super(tab, color);

	}
	
	

	@Override
	public String toString() {
		return "C";
	}

	private boolean canMove(Posicao position) {
		PecaXadrez p = (PecaXadrez) getTab().peca(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public boolean[][] movimenta() {
		boolean[][] mat = new boolean[getTab().getLinhas()][getTab().getColunas()];

		Posicao p = new Posicao(0, 0);

	
		p.setValues(position.getLinha() - 1, position.getColuna() - 2);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

	
		p.setValues(position.getLinha() - 2, position.getColuna() - 1 );
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(position.getLinha() - 2, position.getColuna() + 1);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(position.getLinha() - 1, position.getColuna() + 2);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

	
		p.setValues(position.getLinha() + 1, position.getColuna() + 2);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

	
		p.setValues(position.getLinha() + 2, position.getColuna() + 1);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

	
		p.setValues(position.getLinha() + 2, position.getColuna() - 1);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(position.getLinha() + 1, position.getColuna() - 2);
		if (getTab().posicaoExiste(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}

}
