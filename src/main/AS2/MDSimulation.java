package mdsimulation;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;

public class MDSimulation {
    public static void main(String[] args) {
        try {
            int n = StdIn.readInt();
            System.out.println("particle number " + n);
            Particle[] particles = new Particle[n];
            for (int i = 0; i < n; i++) {
                double rx = StdIn.readDouble();
                double ry = StdIn.readDouble();
                double vx = StdIn.readDouble();
                double vy = StdIn.readDouble();
                
                double radius = StdIn.readDouble();
                double mass = StdIn.readInt();
                int r = StdIn.readInt();
                int g = StdIn.readInt();
                int b = StdIn.readInt();
                particles[i] = new Particle(rx, ry, vx, vy, radius, mass, r, g, b);
            }

            StdDraw.setCanvasSize(600, 600);
            StdDraw.setXscale(0, 1);
            StdDraw.setYscale(0, 1);

            CollisionSystem system = new CollisionSystem(particles);
            system.simulate();
        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}