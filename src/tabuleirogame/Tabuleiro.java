package tabuleirogame;

public class Tabuleiro {

	private int linhas;
	private int colunas;

	private Peca[][] pecas;

	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro no tabuleiro: Deve ter 1 linha e 1 coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];

	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peca peca(int linha, int coluna) {
		if (!positionExists(linha, coluna)) {
			throw new TabuleiroException("Posição não existente no tabuleiro");
		}
		return pecas[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("Posição não existente");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	public void lugarPeca(Peca peca, Posicao pos) {
		if(existeUmaPeca(pos)) {
			throw new TabuleiroException("Já existe uma peça na posição" + pos);
		}
		pecas[pos.getLinha()][pos.getColuna()] = peca;
		peca.position = pos;
	}
	
	public Peca removePeca(Posicao pos) {
		if(!posicaoExiste(pos)) {
			throw new TabuleiroException("Posição não está no tabuleiro");
		}
		if(peca(pos) == null) {
			return null;
		}
		Peca aux = peca(pos);
		aux.position = null;
		pecas[pos.getLinha()][pos.getColuna()] = null;
		return aux;
	}

	private boolean positionExists(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean posicaoExiste(Posicao pos) {
		return positionExists(pos.getLinha(), pos.getColuna());
	}

	public boolean existeUmaPeca(Posicao pos) {
		if (!posicaoExiste(pos)) {
			throw new TabuleiroException("Posição não existente no tabuleiro");
		}
		return peca(pos) != null;
	}
}
