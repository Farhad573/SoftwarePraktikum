package view;

import model.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class ComparePairsDialouge extends JFrame {
    private JTable resultTable;

    public ComparePairsDialouge (JFrame parentFrame, Pair pair1, Pair pair2) {


        setSize(600, 400);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        // Compare the participants and generate the result
        JTable comparisonResult = compareParticipants(pair1, pair2);

        resultTable = comparisonResult;

        JScrollPane scrollPane = new JScrollPane(resultTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTable compareParticipants(Pair pair1, Pair pair2) {
        Map<String, Object> attributes1 = pair1.getPerson1().toMap();
        Map<String, Object> attributes2 = pair1.getPerson2().toMap();
        Map<String, Object> attributes3 = pair2.getPerson1().toMap();
        Map<String, Object> attributes4 = pair2.getPerson2().toMap();

        // Create the table model with three columns
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Category");
        tableModel.addColumn("Participant 1.1");
        tableModel.addColumn("Participant 1.2");
        tableModel.addColumn("Participant 2.1");
        tableModel.addColumn("Participant 2.2");

        // Compare the attributes and add rows to the table model
        for (String attribute : attributes1.keySet()) {
            Object value1 = attributes1.get(attribute);
            Object value2 = attributes2.get(attribute);
            Object value3 = attributes3.get(attribute);
            Object value4 = attributes4.get(attribute);

            tableModel.addRow(new Object[]{attribute, value1, value2, value3, value4});
        }

        // Create the table with the table model
        JTable comparisonTable = new JTable(tableModel);

        return comparisonTable;
    }

}
