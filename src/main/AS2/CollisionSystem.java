package mdsimulation;
import java.util.PriorityQueue;
import edu.princeton.cs.algs4.StdDraw;

public class CollisionSystem {
    private PriorityQueue<Event> pq;
    private double t = 0.0;
    private Particle[] particles;

    public CollisionSystem(Particle[] particles) {
        this.particles = particles;
        this.pq = new PriorityQueue<>();
        initializeEvents();
    }

    private void initializeEvents() {
        for (Particle p : particles) {
            predictCollisions(p);
        }
        pq.add(new Event(0.01, null, null));
    }

    private void predictCollisions(Particle p) {
    	//if (p.vx == 0.0 && p.vy == 0.0) return;
        double dtX = p.collidesX();
        if (dtX >= 0) {
            pq.add(new Event(t + dtX, p, null));
        }
        double dtY = p.collidesY();
        if (dtY >= 0) {
            pq.add(new Event(t + dtY, null, p));
        }

        for (Particle other : particles) {
            if (p != other) {
                double dt = p.collides(other);
                if (dt >= 0) {
                    pq.add(new Event(t + dt, p, other));
                }
            }
        }
    }

    public void simulate() {
        while (!pq.isEmpty()) {
            Event event = pq.poll();
            if (!event.isValid()) continue;

            double dt = event.getTime() - t;
            for (Particle p : particles) {
                p.move(dt);
            }
            t = event.getTime();

            Particle a = event.getA();
            Particle b = event.getB();
            if (a != null && b != null) {
                a.bounce(b);
            } else if (a != null) {
                a.bounceX();
            } else if (b != null) {
                b.bounceY();
            } else {
                redraw();
                pq.add(new Event(t + 0.5, null, null));
            }

            if (a != null) {
                predictCollisions(a);
            }
            if (b != null) {
                predictCollisions(b);
            }
        }
        if (pq.isEmpty()) {
            System.out.println("The event queue is empty and the simulation ends.");
            return;
        }
    }

    private void redraw() {
    	StdDraw.clear();
        for (Particle p : particles) {
            StdDraw.setPenColor(p.getR(), p.getG(), p.getB());
            StdDraw.filledCircle(p.getRx(), p.getRy(), p.getRadius());
        }
        StdDraw.show();
    }
}