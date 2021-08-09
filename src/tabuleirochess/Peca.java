package tabuleirochess;

public class Peca {

	protected Posicao pos;
	private Tabuleiro tab;

	public Peca(Tabuleiro tab) {
		this.tab = tab;
	}

	protected Tabuleiro getTab() {
		return tab;
	}

}
