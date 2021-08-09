package xadrez;

import tabuleirochess.Peca;
import tabuleirochess.Tabuleiro;

public class PecaXadrez extends Peca {

	private Color color;

	public PecaXadrez(Tabuleiro tab, Color color) {
		super(tab);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}
