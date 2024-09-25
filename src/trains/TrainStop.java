package trains;

import java.awt.Color;
import java.awt.Graphics2D;

import bezierCurve.Path;
import bezierCurve.QuadraticBezierCurve;

public class TrainStop {
    Path path;
    int sectionIdx;
    QuadraticBezierCurve section;
    double t;
    double[] normal;
    double[] pos;
    
    public TrainStop(Path path, int sectionIdx, double t) {
        this.path = path;
        this.sectionIdx = sectionIdx;
        section = path.sections.get(sectionIdx);
        this.t = t;
        normal = section.calculateNormal(t, 100);
        pos = section.calculatePosition(t);
    }

    public void render(Graphics2D g2) {
        final double[] iHat = normal.clone();
        final double[] jHat = {-normal[1], normal[0]};
        int s = 8;
        
        int[] xPoints = {
            (int)(+s * jHat[0] + pos[0]),
            (int)(-s * jHat[0] + pos[0]),
            (int)(iHat[0] * 3 * s - s * jHat[0] + pos[0]),
            (int)(iHat[0] * 3 * s + s * jHat[0] + pos[0]),
        };
        int[] yPoints = {
            (int)(+s * jHat[1] + pos[1]),
            (int)(-s * jHat[1] + pos[1]),
            (int)(iHat[1] * 3 * s - s * jHat[1] + pos[1]),
            (int)(iHat[1] * 3 * s + s * jHat[1] + pos[1]),
        };

        g2.setColor(Color.ORANGE);
        g2.drawPolygon(xPoints, yPoints, 4);
    }
}
