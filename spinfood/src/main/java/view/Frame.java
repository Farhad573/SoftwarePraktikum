package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Frame extends JFrame {

    public Frame() {
        // Set the title of the frame
        super("My Frame");

        // Create a JPanel to use as the content pane
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage image = ImageIO.read(new File("src/main/java/view/photo.jpg"));
                    g.drawImage(image, 0, 0, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // Set the layout manager
        panel.setLayout(new BorderLayout());

        // Add components to the panel
        JButton button = new JButton("Click me!");
        button.setPreferredSize(new Dimension(100, 50));
        panel.add(button, BorderLayout.SOUTH);


        // Set the panel as the content pane
        setContentPane(panel);

        // Set the size and location of the frame
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Show the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        new Frame();
    }
}