package trains;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import bezierCurve.Path;
import bezierCurve.QuadraticBezierCurve;

public class Train {
    Path path;
    int sectionIdx;
    QuadraticBezierCurve section;
    double t;
    public double speed;
    double velocity;
    double absVelocity;
    public double acceleration;
    double friction;

    public Train(Path path, int sectionIdx, double t) {
        this.path = path;
        this.sectionIdx = sectionIdx;
        this.t = t;
        section = path.sections.get(sectionIdx);
        speed = 50;
        velocity = 0;
        absVelocity = velocity / section.length;
        acceleration = 0;
        friction = 0.1;
    }

    public void update(double dt) {
        velocity += acceleration * dt;
        velocity *= 1 / (1 + friction);
        absVelocity = velocity / section.length;
        t += absVelocity * dt;
        if (t > 1) {
            sectionIdx = (sectionIdx + 1) % path.sections.size();
            section = path.sections.get(sectionIdx);
            t -= 1;
            System.out.println(velocity / section.length);
        }
    }

    public void render(Graphics2D g2) {
        double[] pos = section.calculatePosition(t);
        int s = 20;

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));
        g2.drawArc((int)pos[0] - s / 2, (int)pos[1] - s / 2, s, s, 0, 360);

        renderSpeed(g2);
    }

    public void renderSpeed(Graphics2D g2) {
        double[] normal = section.calculateNormal(t, 100);
        double[] tangent = {-normal[1] * absVelocity * 100, normal[0] * absVelocity * 100};
        double[] pos = section.calculatePosition(t);

        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.MAGENTA);
        g2.drawLine((int)pos[0], (int)pos[1], (int)(pos[0] + tangent[0]), (int)(pos[1] + tangent[1]));
    }
}
