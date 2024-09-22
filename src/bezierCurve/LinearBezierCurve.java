package bezierCurve;

public class LinearBezierCurve {
    double[] start;
    double[] end;
    
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
}
