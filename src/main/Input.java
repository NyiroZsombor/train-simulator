package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import bezierCurve.Path;
import bezierCurve.QuadraticBezierCurve;


public class Input implements KeyListener, MouseListener, MouseMotionListener {
    public boolean clicked;
    public int[] mousePos;

    Panel panel;

    public Input(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = new int[2];
        mousePos[0] = e.getX();
        mousePos[1] = e.getY();

        if (panel.dragging) {
            panel.selected[0] = mousePos[0];
            panel.selected[1] = mousePos[1];
            panel.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        boolean select = selectNode();

        if (panel.action.equals("create")) {
            double[] pos = new double[2];

            if (select) {
                pos[0] = (int)panel.selected[0];
                pos[1] = (int)panel.selected[1];
                panel.selected = null;
            }
            else{
                pos[0] = mousePos[0];
                pos[1] = mousePos[1];
            }
            panel.lastPoints.add(pos);
        }
        else if (panel.action.equals("move")) {
            if (select) panel.dragging = !panel.dragging;
            if (!panel.dragging) panel.selected = null;
        }
    }

    private boolean selectNode() {
        for (int i = 0; i < panel.curves.size(); i++) {
            QuadraticBezierCurve curve = panel.curves.get(i);

            if (Math.abs(curve.start[0] - mousePos[0]) < 10
            && Math.abs(curve.start[1] - mousePos[1]) < 10) {
                panel.selected = curve.start;
                return true;
            }
            else if (Math.abs(curve.control[0] - mousePos[0]) < 10
            && Math.abs(curve.control[1] - mousePos[1]) < 10) {
                panel.selected = curve.control;
                return true;
            }
            else if (Math.abs(curve.end[0] - mousePos[0]) < 10
            && Math.abs(curve.end[1] - mousePos[1]) < 10) {
                panel.selected = curve.end;
                return true;
            }
        }

        return false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_1) panel.action = "move";
        if (e.getKeyCode() == KeyEvent.VK_2) panel.action = "create";
        if (e.getKeyCode() == KeyEvent.VK_3) panel.action = "running";
        if (e.getKeyCode() == KeyEvent.VK_R) {
            panel.curves.clear();
            panel.paths.clear();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && panel.action.equals("create")) {
            panel.paths.add(new Path(panel.lastPoints));

            panel.lastPoints.clear();
            panel.repaint();
        }
    }
    
}
