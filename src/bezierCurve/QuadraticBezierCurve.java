package bezierCurve;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class QuadraticBezierCurve {
    public double[] start;
    public double[] end;
    public double[] control;
    public double length;
    LinearBezierCurve[] sections;
    
    public QuadraticBezierCurve(double[] start, double[] control, double[] end) {
        this.start = start;
        this.control = control;
        this.control = control;
        this.end = end;
        sections = new LinearBezierCurve[2];
        sections[0] = new LinearBezierCurve(start, control);
        sections[1] = new LinearBezierCurve(control, end);
        length = calculateLength(1000);
        System.out.println(length);
    }

    public double calculateLength(int p) {
        double[] pos = calculatePosition(1d / p);
        double[] diff = {pos[0] - start[0], pos[1] - start[1]};
        return Math.hypot(diff[0], diff[1]) * p;
    }

    public double[] calculatePosition(double t) {
        return new LinearBezierCurve(
            sections[0].calculatePosition(t),
            sections[1].calculatePosition(t)
        ).calculatePosition(t);
    }

    public static void render(Graphics2D g2, QuadraticBezierCurve curve, boolean rCurve, boolean rPoints, boolean rLines) {
        if (rCurve) {
            int n = 10;
            int[] xPoints = new int[n + 1];
            int[] yPoints = new int[n + 1];
    
            for (int i = 0; i <= n; i++) {
                double t = (double)i / n;
                double[] pos = curve.calculatePosition(t);
                xPoints[i] = (int)pos[0];
                yPoints[i] = (int)pos[1];
            }

            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.BLACK);
            g2.drawPolyline(xPoints, yPoints, n + 1);
        }

        if (rPoints) {
            int s = 10;
            int s2 = s / 2;
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.BLUE);
            g2.drawArc((int)curve.start[0] - s2, (int)curve.start[1] - s2, s, s, 0, 360);
            g2.drawArc((int)curve.control[0] - s2, (int)curve.control[1] - s2, s, s, 0, 360);
            g2.drawArc((int)curve.end[0] - s2, (int)curve.end[1] - s2, s, s, 0, 360);
        }

        if (rLines) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.CYAN);
            g2.drawLine((int)curve.start[0], (int)curve.start[1], (int)curve.control[0], (int)curve.control[1]);
            g2.drawLine((int)curve.control[0], (int)curve.control[1], (int)curve.end[0], (int)curve.end[1]);
        }
    }
}