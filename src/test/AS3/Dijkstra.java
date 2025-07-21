/*************************************************************************
 *  Dijkstra's algorithm.
 *
 *************************************************************************/
import java.awt.Color;

public class Dijkstra {
    private static double INFINITY = Double.MAX_VALUE;
    private static double EPSILON  = 0.000001;

    private EuclideanGraph G;
    private double[] dist;
    private int[] pred;
    private double[] heuristic;
    private int verticesExamined;

    public Dijkstra(EuclideanGraph G) {
        this.G = G;
        this.verticesExamined = 0;
    }

    public double distance(int s, int d) {
        long startTime = System.nanoTime();
        verticesExamined = 0;
        dijkstraWithAStar(s, d);
        long endTime = System.nanoTime();
        System.out.println("A* Algorithm Time: " + (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("Average vertices examined: " + (double) verticesExamined);
        return dist[d];
    }

    private void dijkstraWithAStar(int s, int d) {
        int V = G.V();

        dist = new double[V];
        pred = new int[V];
        heuristic = new double[V];

        for (int v = 0; v < V; v++) {
            dist[v] = INFINITY;
            pred[v] = -1;
            heuristic[v] = G.distance(v, d);
        }

        IndexPQ pq = new IndexPQ(V);
        for (int v = 0; v < V; v++) pq.insert(v, dist[v] + heuristic[v]);

        dist[s] = 0.0;
        pred[s] = s;
        pq.change(s, dist[s] + heuristic[s]);

        while (!pq.isEmpty()) {
            int v = pq.delMin();
            verticesExamined++;
            if (v == d) break;

            IntIterator i = G.neighbors(v);
            while (i.hasNext()) {
                int w = i.next();
                double newDist = dist[v] + G.distance(v, w);
                if (newDist < dist[w] - EPSILON) {
                    dist[w] = newDist;
                    pq.change(w, dist[w] + heuristic[w]);
                    pred[w] = v;
                }
            }
        }
    }

    public double dijkstraWithoutAStar(int s, int d) {
        long startTime = System.nanoTime();
        verticesExamined = 0;
        dijkstra(s, d);
        long endTime = System.nanoTime();
        System.out.println("Dijkstra Algorithm Time: " + (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("Average vertices examined: " + (double) verticesExamined);
        return dist[d];
    }

    private void dijkstra(int s, int d) {
        int V = G.V();

        dist = new double[V];
        pred = new int[V];
        for (int v = 0; v < V; v++) dist[v] = INFINITY;
        for (int v = 0; v < V; v++) pred[v] = -1;

        IndexPQ pq = new IndexPQ(V);
        for (int v = 0; v < V; v++) pq.insert(v, dist[v]);

        dist[s] = 0.0;
        pred[s] = s;
        pq.change(s, dist[s]);

        while (!pq.isEmpty()) {
            int v = pq.delMin();
            verticesExamined++;
            if (v == d) break;

            IntIterator i = G.neighbors(v);
            while (i.hasNext()) {
                int w = i.next();
                double newDist = dist[v] + G.distance(v, w);
                if (newDist < dist[w] - EPSILON) {
                    dist[w] = newDist;
                    pq.change(w, dist[w]);
                    pred[w] = v;
                }
            }
        }
    }

    public void showPath(int s, int d) {
        dijkstraWithAStar(s, d);
        if (pred[d] == -1) {
            System.out.println(d + " is unreachable from " + s);
            return;
        }
        for (int v = d; v != s; v = pred[v]) {
            System.out.print(v + "-");
        }
        System.out.println(s);
    }

    public void drawPath(int s, int d) {
        dijkstraWithAStar(s, d);
        if (pred[d] == -1) return;
        Turtle.setColor(Color.red);
        for (int v = d; v != s; v = pred[v]) {
            G.point(v).drawTo(G.point(pred[v]));
        }
        Turtle.render();
    }
}
