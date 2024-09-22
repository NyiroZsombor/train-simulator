package main;

import javax.swing.JFrame;

class Main extends JFrame{
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        Panel panel = new Panel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MÁV");
        setSize(panel.windowWidth, panel.windowHeigth);
        setLocationRelativeTo(null);
        add(panel);
        pack();
        setVisible(true);

        panel.start();
    }
}