package src;

public class Percolation{
	private WeightedQuickUnionUF uf;
	private boolean[][] isOpened;
	private int N;
	private int top;
	private int bottom;
	private int openSiteCount;
	
	public Percolation(int N) {
		if (N <= 0) {
	        throw new java.lang.IllegalArgumentException("N must be positive");
	    }
		
		this.N = N;
		this.isOpened = new boolean[N][N];
		int size = N * N + 2;
		this.uf = new WeightedQuickUnionUF(size);
		this.top = N * N;
		this.bottom = N * N + 1;
		openSiteCount = 0;
		
		for (int j = 0; j < N; j++) {
			uf.union(top, index(0, j));
		}
	}
	
	private int index(int i, int j){
		return i * N + j;
	}
	
	private void validateIndices(int i, int j) {
	    if (i < 0 || i >= N || j < 0 || j >= N) {
	        throw new IndexOutOfBoundsException("Index (" + i + ", " + j + ") is out of bounds");
	    }
	}
	
	public void open(int i, int j) {
		validateIndices(i, j);
		if( isOpen(i, j) == false ) {
			isOpened[i][j] = true;
			openSiteCount++;
			int now = index(i, j);
			int[][] surround = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
			for (int[] sur : surround) {
				int ni = i + sur[0];
				int nj = j + sur[1];
				if (ni >= 0 && ni < N && nj >= 0 && nj < N && isOpen(ni, nj))
					uf.union(now, index(ni, nj));
			}
			if (i == N - 1) {
		        uf.union(index(i, j), bottom);  // ����ǵײ��У���������ײ�
		    }
		}
	}
	
	public boolean isOpen(int i, int j) {
		validateIndices(i, j);
		return isOpened[i][j];
	}
	
	public boolean isFull(int i, int j) {
		validateIndices(i, j);
		return isOpen(i, j) && uf.connected(index(i, j), top);
	}
	
	public int numberOfOpenSites() {
		return openSiteCount;
	}
	
	public boolean percolates() {
		if (N == 1 ) {
			return isOpen(0, 0);
		}
		return uf.connected(top, bottom);
		}
}