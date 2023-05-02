package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class View extends JFrame {

    private JLabel label;
    private JTextField textField;

    private JButton button;

    public View() {
        // Set the title of the frame
        super("Spin FOOD");

        // Create the UI components
        label = new JLabel("choose your file:");
        textField = new JTextField(20);
        button = new JButton("Browse");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);


                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textField.setText(selectedFile.getAbsolutePath());
                }
            }
        });



        // Set the layout manager
        setLayout(new GridLayout(3, 1));

        // Add the components to the frame
        add(label);
        add(button);
        add(textField);


        // Set the size and location of the frame
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Show the frame
        setVisible(true);
    }
}
