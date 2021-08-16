package tabuleirogame;

public abstract class Peca {

	protected Posicao position;
	private Tabuleiro tab;

	public Peca(Tabuleiro tab) {
		this.tab = tab;
	}

	protected Tabuleiro getTab() {
		return tab;
	}

	public abstract boolean[][] movimenta();

	public boolean movi(Posicao posicao) {
		return movimenta()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public boolean existeMovi() {
		boolean[][] mat = movimenta();
		for(int i=0; i<mat.length; i++) {
			for(int j=0; j<mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}

}
