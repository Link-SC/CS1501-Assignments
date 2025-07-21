package mdsimulation;
public class Event implements Comparable<Event> {
    private final double time;
    private final Particle a, b;
    private final int countA, countB;

    public Event(double t, Particle a, Particle b) {
        this.time = t;
        this.a = a;
        this.b = b;
        this.countA = (a != null) ? a.getCollisionCount() : -1;
        this.countB = (b != null) ? b.getCollisionCount() : -1;
    }

    public double getTime() { return time; }

    public boolean isValid() {
        if (a != null && a.getCollisionCount() != countA) return false;
        if (b != null && b.getCollisionCount() != countB) return false;
        return true;
    }

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }
    
    public Particle getA() {
        return a;
    }

    public Particle getB() {
        return b;
    }
    
}