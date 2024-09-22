package bezierCurve;

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
}