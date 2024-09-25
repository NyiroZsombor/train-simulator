package main;

import bezierCurve.Path;
import bezierCurve.QuadraticBezierCurve;
import trains.Train;
import trains.TrainStop;

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
    ArrayList<Path> paths;
    Train train;
    TrainStop trainStop;

    long startTime;
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
        paths = new ArrayList<>();
        action = "create";
        font = new Font("Serif", 0, 24);
        t = 0;
        startTime = System.nanoTime();

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
                train.update(0.1d);
            }
            repaint();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: optimize, delete
    public int[] calculateTime(long elapsedNano) {
        long milliSeconds = (int)(elapsedNano / 1_000_000);
        long seconds = milliSeconds / 1_000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        int years = (int)(weeks / 52);

        int[] time = {
            (int)(milliSeconds % 1_000),
            (int)(seconds % 60),
            (int)(minutes % 60),
            (int)(hours % 24),
            (int)(days % 7),
            (int)(weeks % 52),
            years
        };
        return time;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.clearRect(0, 0, windowWidth, windowHeigth);

        for (int i = 0; i < curves.size(); i++) {
            QuadraticBezierCurve.render(g2, curves.get(i), true, true, true, true);
        }

        for (int i = 0; i < paths.size(); i++) {
            Path.render(g2, paths.get(i), true, true, false, false);
        }

        renderActivePoints(g2);
        if (action.equals("running")) {
            trainStop.render(g2);
            train.render(g2);
        }

        g2.setFont(font);
        g2.setColor(Color.BLACK);
        g2.drawString(action, 400 + 8, 12 + 8);
        renderTimeAndDate(g2);

        g2.dispose();
    }

    private void renderActivePoints(Graphics2D g2) {
        int s = 10;
        g2.setColor(Color.ORANGE);
        g2.setStroke(new BasicStroke(3));
        
        for (int i = 0; i < lastPoints.size(); i++) {
            g2.drawArc((int)lastPoints.get(i)[0] - s / 2, (int)lastPoints.get(i)[1] - s / 2, s, s, 0, 360);
        }
    }

    private void renderTimeAndDate(Graphics2D g2) {
        int[] time = calculateTime(System.nanoTime() - startTime);

        g2.setFont(font);
        g2.setColor(Color.BLACK);
        g2.drawString(time[3] + ":" + time[2] + ":" + time[1] + "." + time[0], 8, 12 + 8);
        g2.drawString(time[6] + ", week: " + time[5] + " day: " + time[4], 8, 12 + 32);
    }
}
