package xadrez;

import tabuleirogame.Peca;
import tabuleirogame.Posicao;
import tabuleirogame.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Color color;
	private int moveCount;

	public PecaXadrez(Tabuleiro tab, Color color) {
		super(tab);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void increaseMoveCount() {
		moveCount++;
	}

	public void decreaseMoveCount() {
		moveCount--;
	}

	public XadrezPosicao getXadrezPosicao() {
		return XadrezPosicao.daPosicao(position);
	}

	protected boolean isOponentPeça(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTab().peca(posicao);
		return p != null && p.getColor() != color;
	}

}
