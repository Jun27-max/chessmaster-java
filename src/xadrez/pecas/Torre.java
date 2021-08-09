package xadrez.pecas;

import tabuleirochess.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez{

	public Torre(Tabuleiro tab, Color color) {
		super(tab, color);
	}

	@Override
	public String toString() {
		return "R";
	}
	
	

}
