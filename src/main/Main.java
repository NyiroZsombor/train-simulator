package main;

import javax.swing.JFrame;

class Main extends JFrame {
    Panel panel;
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        panel = new Panel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MÁV");
        setSize(panel.windowWidth, panel.windowHeigth);
        setLocationRelativeTo(null);
        add(panel);
        pack();
        setVisible(true);
        
        panel.start();
    }

    /*
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("#: ");
            String input = scanner.nextLine();
            System.out.print("> ");

            if (input.equals("exit")) break;
            else System.out.println("error");
        }
        scanner.close();
        System.exit(0);
    }
        */
}