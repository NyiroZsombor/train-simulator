package bezierCurve;

public class LinearBezierCurve {
    int[] start;
    int[] end;
    
    public LinearBezierCurve(int[] start, int[] end) {
        this.start = start;
        this.end = end;
    }

    public int[] calculatePosition(double t) {
        int[] pos = new int[2];
        pos[0] = (int)((double)(end[0] - start[0])*t) + start[0];
        pos[1] = (int)((double)(end[1] - start[1])*t) + start[1];
        return pos;
    }
}
