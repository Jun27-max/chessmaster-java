package xadrez;

import tabuleirogame.Posicao;

public class XadrezPosicao {

	private char coluna;
	private int linha;

	public XadrezPosicao(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezException("Erro de intanciamento da posi��o. Valores v�lidos de a1 at� h8.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	protected Posicao paraPosicao() {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	protected static XadrezPosicao daPosicao (Posicao posicao) {
		return new XadrezPosicao((char)('a' + posicao.getColuna()), 8 - posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return ""+coluna+linha;
	}

}
