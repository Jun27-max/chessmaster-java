package xadrez.pecas;

import tabuleirochess.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{

	public Rei(Tabuleiro tab, Color color) {
		super(tab, color);
		
	}
	
	@Override
	public String toString() {
		return "K";
	}

}
