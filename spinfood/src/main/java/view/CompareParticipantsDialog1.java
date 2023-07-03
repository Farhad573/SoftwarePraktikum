package view;

import model.Participant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class CompareParticipantsDialog1 extends JDialog {
    private JTable resultTable;

    public CompareParticipantsDialog1(JFrame parentFrame, Participant participant1, Participant participant2) {
        super(parentFrame, "Compare Participants", true);

        setSize(600, 400);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        // Compare the participants and generate the result
        JTable comparisonResult = compareParticipants(participant1, participant2);

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

    private JTable compareParticipants(Participant participant1, Participant participant2) {
        Map<String, Object> attributes1 = participant1.toMap();
        Map<String, Object> attributes2 = participant2.toMap();

        // Create the table model with three columns
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Category");
        tableModel.addColumn("Participant 1");
        tableModel.addColumn("Participant 2");

        // Compare the attributes and add rows to the table model
        for (String attribute : attributes1.keySet()) {
            Object value1 = attributes1.get(attribute);
            Object value2 = attributes2.get(attribute);

            tableModel.addRow(new Object[]{attribute, value1, value2});
        }

        // Create the table with the table model
        JTable comparisonTable = new JTable(tableModel);

        return comparisonTable;
    }
}