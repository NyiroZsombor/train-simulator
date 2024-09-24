package bezierCurve;

import java.awt.Graphics2D;

import java.util.ArrayList;

public class Path {
    public ArrayList<QuadraticBezierCurve> sections;

    public Path(ArrayList<double[]> controls) {
        if (controls.size() < 3) {
            System.err.println("invalid Path object");
            return;
        }

        sections = createSections(controls);
    }

    private ArrayList<QuadraticBezierCurve> createSections(ArrayList<double[]> controls) {
        ArrayList<QuadraticBezierCurve> result = new ArrayList<>();

        for (int i = 0; i < controls.size(); i++) {
            double[] prevCtrl;
            if (i == 0) prevCtrl = controls.get(controls.size() - 1);
            else prevCtrl = controls.get(i - 1);
            double[] ctrl = controls.get(i);
            double[] nextCtrl = controls.get((i + 1) % controls.size());

            // TODO: optimize
            double[] prevDiff = {prevCtrl[0] - ctrl[0], prevCtrl[1] - ctrl[1]};
            double[] nextDiff = {nextCtrl[0] - ctrl[0], nextCtrl[1] - ctrl[1]};
            double[] prevMidPoint = {ctrl[0] + prevDiff[0] / 2, ctrl[1] + prevDiff[1] / 2};
            double[] nextMidPoint = {ctrl[0] + nextDiff[0] / 2, ctrl[1] + nextDiff[1] / 2};

            QuadraticBezierCurve curve = new QuadraticBezierCurve(prevMidPoint, ctrl, nextMidPoint);
            result.add(curve);
        }

        return result;
    }

    public static void render(Graphics2D g2, Path path, boolean rCurve, boolean rPoints, boolean rLines, boolean rNormals) {
        for (int i = 0; i < path.sections.size(); i++) {
            QuadraticBezierCurve.render(g2, path.sections.get(i), rCurve, rPoints, rLines, rNormals);
        }
    }
}
