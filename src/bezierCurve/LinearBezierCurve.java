package bezierCurve;

public class LinearBezierCurve {
    public double[] start;
    public double[] end;
    
    public LinearBezierCurve(double[] start, double[] end) {
        this.start = start;
        this.end = end;
    }

    public double[] calculatePosition(double t) {
        double[] pos = new double[2];
        pos[0] = ((end[0] - start[0])*t) + start[0];
        pos[1] = ((end[1] - start[1])*t) + start[1];
        return pos;
    }

    public double[] calculateNormal(double t, int p) {
        double[] pos0 = calculatePosition(t - 1d / p / 2);
        double[] pos1 = calculatePosition(t + 1d / p / 2);
        double[] diff = {pos0[0] - pos1[0], pos0[1] - pos1[1]};
        double dist = Math.hypot(diff[0], diff[1]);
        double[] tangent = {diff[0] / dist, diff[1] / dist};
        double[] normal = {-tangent[1], tangent[0]};
        return normal;
    }
}
