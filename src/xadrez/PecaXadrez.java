package xadrez;

import tabuleirogame.Peca;
import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Color color;

	public PecaXadrez(Tabuleiro tab, Color color) {
		super(tab);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	protected boolean isOponentPeça(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTab().peca(posicao);
		return p != null && p.getColor() != color;
	}

}
