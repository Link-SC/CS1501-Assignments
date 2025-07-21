package src;

public class PercolationStats {
	private static final double CONFIDENCE_95 = 1.96;
    private final double[] thresholds;
    private final int T;
    private double mean;
    private double stddev;
    
    public PercolationStats(int N, int T) {
    	if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be positive");
        }
    	
    	this.T = T;
    	thresholds = new double[T];
    	Stopwatch stopwatch = new Stopwatch();
    	
    	for (int t = 0; t < T; t++) {
    		Percolation experiment = new Percolation(N);
    		int openCounts = 0;
    		while (experiment.percolates() == false) {
    			int i = StdRandom.uniform(N), j = StdRandom.uniform(N);
    			while(experiment.isOpen(i, j)) {
    				i = StdRandom.uniform(N);
    				j = StdRandom.uniform(N);
    			}
    			experiment.open(i, j);
    			openCounts++;
    		}
    		thresholds[t] = (double) openCounts / (N * N);
    	}
    	
    	mean = StdStats.mean(thresholds);
    	stddev = StdStats.stddev(thresholds);
    	
    	double elapsedTime = stopwatch.elapsedTime();
    	System.out.println("Total running time: " + elapsedTime + " seconds");
    }
    
    public double mean() {
    	return mean;
    }
    
    public double stddev() {
    	return stddev;
    }
    
    public double confidenceLow() {
    	return mean - CONFIDENCE_95 * stddev / Math.sqrt(T);
    }
    
    public double confidenceHigh() {
        return mean + CONFIDENCE_95 * stddev / Math.sqrt(T);
    }
    
    public static void run(int N, int T) {
    	PercolationStats stats = new PercolationStats(N, T);
    	System.out.println("Example values after creating PercolationStats(" + N +", " + T +")");
        System.out.println("Mean() = " + stats.mean());
        System.out.println("StdDev() = " + stats.stddev());
        System.out.println("95% Confidence Interval: [" + stats.confidenceLow() + ", " + stats.confidenceHigh() + "]");
    }
    
    public static void main(String[] args) {
    	run(1600, 100);
        run(3200, 100);
        run(1600, 200);
    }
}

