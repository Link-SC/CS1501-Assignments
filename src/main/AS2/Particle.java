package mdsimulation;

public class Particle {
    private double rx, ry;
    private double vx, vy;
    private final double radius;
    private final double mass;
    private final int r, g, b;
    private int collisionCount;

    public Particle(double rx, double ry, double vx, double vy, double radius, double mass, int r, int g, int b) {
        this.radius = radius;
        this.mass = mass;
        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.r = r;
        this.g = g;
        this.b = b;
        this.collisionCount = 0;
    }

    public double collidesX() {
        if (vx == 0) return -1;
        double dt;
        if (vx > 0) {
            dt = (1.0 - radius - rx) / vx;
        } else {
            dt = (radius - rx) / vx;
        }
        return (dt > 0 && !Double.isNaN(dt)) ? dt : -1;
    }

    public double collidesY() {
        if (vy == 0) return -1;
        double dt;
        if (vy > 0) {
            dt = (1.0 - radius - ry) / vy;
        } else {
            dt = (radius - ry) / vy;
        }
        return (dt > 0 && !Double.isNaN(dt)) ? dt : -1;
    }

    public double collides(Particle b) {
        double dx = b.rx - this.rx;
        double dy = b.ry - this.ry;
        double dvx = b.vx - this.vx;
        double dvy = b.vy - this.vy;
        double dvdr = dx * dvx + dy * dvy;

        if (dvdr >= 0) return -1;

        double dvdv = dvx * dvx + dvy * dvy;
        if (dvdv == 0) return -1;

        double drdr = dx * dx + dy * dy;
        double sigma = this.radius + b.radius;
        double d = dvdr * dvdr - dvdv * (drdr - sigma * sigma);
      
        if (d < -1e-9) return -1;

        double dt = -(dvdr + Math.sqrt(d)) / dvdv;
        return (dt >= -1e-9) ? dt : -1;
    }

    public void bounceX() {
        vx = -vx;
        collisionCount++;
    }

    public void bounceY() {
        vy = -vy;
        collisionCount++;
    }

    public void bounce(Particle b) {
        double dx = b.rx - this.rx;
        double dy = b.ry - this.ry;
        double dvx = b.vx - this.vx;
        double dvy = b.vy - this.vy;
        double dvdr = dx * dvx + dy * dvy;
        
        double sigma = this.radius + b.radius;

        double J = (2 * this.mass * b.mass * dvdr) / (sigma * (this.mass + b.mass));
        double Jx = (J * dx) / sigma;
        double Jy = (J * dy) / sigma;

        this.vx += Jx / this.mass;
        this.vy += Jy / this.mass;
        b.vx -= Jx / b.mass;
        b.vy -= Jy / b.mass;

        this.collisionCount++;
        b.collisionCount++;
    }

    public void move(double dt) {
        rx += vx * dt;
        ry += vy * dt;
    }

    public int getCollisionCount() {
        return collisionCount;
    }
    
    public double getRx() {
        return rx;
    }

    public double getRy() {
        return ry;
    }

    public double getRadius() {
        return radius;
    }
    
    public int getR() { return r; }
    public int getG() { return g; }
    public int getB() { return b; }
}