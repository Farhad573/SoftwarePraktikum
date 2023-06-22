package view;

import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;

import static model.CSVFileReader.getCSV_Pairs;
import static model.CSVFileReader.getParticipants;

public class MainFrame extends JFrame {
    private ParticipantManager participantModel;
    private CSVFileReader fileReader;

    private JTable participantsTable;
    private DefaultTableModel tableModel;

    public MainFrame() {
        participantModel = new ParticipantManager();
        fileReader = new CSVFileReader();
        PairGenerator pairGenerator = new PairGenerator();

        setTitle("Participant Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a tabbed pane to hold the components
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create a panel for the participants tab
        JPanel participantsPanel = new JPanel();
        participantsPanel.setLayout(new BorderLayout());

        // Create a panel for the search field and button
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        // Create a text field for entering the participant ID
        JTextField searchField = new JTextField();
        searchField.setColumns(10);
        searchPanel.add(searchField);

        // Create a button for search
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchId = searchField.getText().trim();
                if (!searchId.isEmpty()) {
                    Participant participant = participantModel.getParticipantById(searchId);
                    if (participant != null) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Participant found:\n" + participant.toString(), "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this, "Participant not found", "Search Result", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        searchPanel.add(searchButton);

        participantsPanel.add(searchPanel, BorderLayout.PAGE_START);

        // Create a table model and table to display the participants
        tableModel = new DefaultTableModel();
        participantsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(participantsTable);
        participantsPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a button panel for action buttons
        JPanel buttonPanel = new JPanel();

        // Create a button to load the CSV file
        JButton loadButton = new JButton("Load CSV");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(MainFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
                        fileReader.readCSVFile(fileName);
                        displayParticipants();
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(MainFrame.this, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.add(loadButton);


        // Create a button for editing a participant
        JButton editButton = new JButton("Edit Participant");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Edit participant functionality here
                int selectedRow = participantsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String participantId = (String) participantsTable.getValueAt(selectedRow, 0);
                    Participant participant = participantModel.getParticipantById(participantId);
                    if (participant != null) {
                        // Open a dialog to edit the participant
                        EditParticipantDialog dialog = new EditParticipantDialog(MainFrame.this, participant);
                        dialog.setVisible(true);

                        // Update the participant information in the table
                        if (dialog.isParticipantUpdated()) {
                            displayParticipants();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Please select a participant to edit", "Edit Participant", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        buttonPanel.add(editButton);

        // Create a button for deleting a participant
        JButton deleteButton = new JButton("Delete Participant");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = participantsTable.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure you want to delete this participant?", "Delete Participant", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        String participantId = (String) participantsTable.getValueAt(selectedRow, 0);
                        deleteParticipant(participantId);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Please select a participant to delete", "Delete Participant", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        buttonPanel.add(deleteButton);



        // Create a button to start pairing
        JButton startPairingButton = new JButton("Start Pairing");
        startPairingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Generate the pairs
                java.util.List<Pair> initialPair = pairGenerator.generateInitialPopulation(getParticipants());
                List<Pair> csvPairs = getCSV_Pairs();
                List<Pair> concatenatedlist = pairGenerator.makeAllPairsTogether(initialPair, csvPairs);

                // Create a table model and table for the pairs
                DefaultTableModel pairsTableModel = new DefaultTableModel();
                JTable pairsTable = new JTable(pairsTableModel);
                JScrollPane pairsScrollPane = new JScrollPane(pairsTable);
                tabbedPane.addTab("Pairs", pairsScrollPane);

                // Update the table model with the pairs and matching scores
                Vector<Vector<Object>> pairsData = new Vector<>();
                for (Pair pair : concatenatedlist) {
                    Vector<Object> pairRow = new Vector<>();
                    pairRow.add(pair.getPerson1());
                    pairRow.add(pair.getPerson2());
                    pairRow.add(pairGenerator.makeIndicatorForPairsList(concatenatedlist));
                    pairsData.add(pairRow);
                }
                Vector<String> pairsColumnNames = new Vector<>();
                pairsColumnNames.add("Participant 1");
                pairsColumnNames.add("Participant 2");
                pairsColumnNames.add("Matching Score");
                pairsTableModel.setDataVector(pairsData, pairsColumnNames);

                // Switch to the "Pairs" tab
                tabbedPane.setSelectedComponent(pairsScrollPane);
            }
        });
        buttonPanel.add(startPairingButton);

        // Add the button panel to the participants panel
        participantsPanel.add(buttonPanel, BorderLayout.PAGE_END);

        // Add the participants panel to the tabbed pane with the title "Participant"
        tabbedPane.addTab("Participant", participantsPanel);

        // Add the tabbed pane to the frame
        add(tabbedPane);

        // Set the participants tab as the default selected tab
        tabbedPane.setSelectedComponent(participantsPanel);
    }

    private void deleteParticipant(String participantId) {

    }

    private void displayParticipants() {
        // Clear the existing data
        tableModel.setRowCount(0);

        // Get the participants from the participant model
        Vector<Vector<Object>> data = new Vector<>();
        for (Participant participant : participantModel.getParticipants()) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(participant.getId());
            rowData.add(participant.getName());
            rowData.add(participant.getAge());
            rowData.add(participant.getSex());
            rowData.add(participant.getFoodPreference());
            rowData.add(participant.getHasKitchen());
            data.add(rowData);
        }

        // Update the table model with the new data
        Vector<String> columnNames = new Vector<>();
        columnNames.add("id");
        columnNames.add("Name");
        columnNames.add("Age");
        columnNames.add("Gender");
        columnNames.add("Food Preference");
        columnNames.add("Kitchen");
        tableModel.setDataVector(data, columnNames);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame gui = new MainFrame();
                gui.setVisible(true);
            }
        });
    }
}
