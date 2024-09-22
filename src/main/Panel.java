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

    ArrayList<double[]> lastPoints;
    ArrayList<QuadraticBezierCurve> curves;

    double[] selected;
    double t;
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
        t = 0;

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
            if (action.equals("running")) {
                t += 10 / curves.get((int)t).length;
                t %= 3;
                //System.out.println(t);
            }
            repaint();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.clearRect(0, 0, windowWidth, windowHeigth);

        for (int i = 0; i < curves.size(); i++) {
            QuadraticBezierCurve.render(g2, curves.get(i), true, true, true);
        }

        if (action.equals("running")) {
            int s = 16;
            int s2 = s / 2;
            double[] pos = curves.get((int)t).calculatePosition(t % 1);
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            g2.drawArc((int)pos[0] - s2, (int)pos[1] - s2, s, s, 0, 360);
        }

        g2.setFont(font);
        g2.setColor(Color.BLACK);
        g2.drawString(action, 0 + 8, 12 + 8);

        g2.dispose();
    }
}
