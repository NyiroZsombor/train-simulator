package bezierCurve;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class QuadraticBezierCurve {
    public int[] start;
    public int[] end;
    public int[] control;
    LinearBezierCurve[] sections;
    
    public QuadraticBezierCurve(int[] start, int[] control, int[] end) {
        this.start = start;
        this.control = control;
        this.control = control;
        this.end = end;
        sections = new LinearBezierCurve[2];
        sections[0] = new LinearBezierCurve(start, control);
        sections[1] = new LinearBezierCurve(control, end);
    }

    public int[] calculatePosition(double t) {
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
                int[] pos = curve.calculatePosition(t);
                xPoints[i] = pos[0];
                yPoints[i] = pos[1];
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
            g2.drawArc(curve.start[0] - s2, curve.start[1] - s2, s, s, 0, 360);
            g2.drawArc(curve.control[0] - s2, curve.control[1] - s2, s, s, 0, 360);
            g2.drawArc(curve.end[0] - s2, curve.end[1] - s2, s, s, 0, 360);
        }

        if (rLines) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.CYAN);
            g2.drawLine(curve.start[0], curve.start[1], curve.control[0], curve.control[1]);
            g2.drawLine(curve.control[0], curve.control[1], curve.end[0], curve.end[1]);
        }
    }
}