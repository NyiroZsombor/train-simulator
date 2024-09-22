package main;

import bezierCurve.QuadraticBezierCurve;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable {
    final int windowWidth = 500;
    final int windowHeigth = 500;
    
    public boolean dragging = false;

    ArrayList<int[]> lastPoints;
    ArrayList<QuadraticBezierCurve> curves;

    int[] selected;
    boolean running = false;
    String action;
    Thread thread;
    Input input;
    Font font;

    public Panel() {
        setPreferredSize(new Dimension(windowWidth, windowHeigth));
        setBackground(new Color(0xFFFFFF));
        setFocusable(true);
        requestFocusInWindow();
        
        input = new Input(this);
        lastPoints = new ArrayList<>();
        curves = new ArrayList<>();
        action = "create";
        font = new Font("Serif", 0, 24);

        addMouseListener(input);
        addMouseMotionListener(input);
        addKeyListener(input);
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        running = true;
        while (running) {
            repaint();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.clearRect(0, 0, windowWidth, windowHeigth);

        for (int i = 0; i < curves.size(); i++) {
            //renderCurve(g2, curves.get(i));
            QuadraticBezierCurve.render(g2, curves.get(i), true, true, true);
        }

        g2.setFont(font);
        g2.setColor(Color.BLACK);
        g2.drawString(action, 0 + 8, 12 + 8);

        g2.dispose();
    }

    public void renderCurve(Graphics2D g2, QuadraticBezierCurve curve) {
        int n = 10;
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];

        for (int i = 0; i < n; i++) {
            int[] point = curve.calculatePosition((double)i / (n - 1));
            xPoints[i] = point[0];
            yPoints[i] = point[1];
        }

        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(3));
        g2.drawPolyline(xPoints, yPoints, n);
        g2.setColor(Color.RED);
        g2.drawArc(curve.start[0] - 5, curve.start[1] - 5, 10, 10, 0, 360);
        g2.drawArc(curve.control[0] - 5, curve.control[1] - 5, 10, 10, 0, 360);
        g2.drawArc(curve.end[0] - 5, curve.end[1] - 5, 10, 10, 0, 360);
    }
}
