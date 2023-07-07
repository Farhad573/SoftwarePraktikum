package view;

import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;

import static model.ParticipantManager.getCSV_Pairs;
import static model.ParticipantManager.getParticipants;

public class GeneratedGroupsFrame extends JFrame {
    private JTextField textField;
    private JButton generateButton;
    private JButton resetButton;
    private DefaultListModel<String> optionsModel;
    private DefaultListModel<String> originalOptionsModel;
    private JList<String> optionsList;
    private JTable first;

    private PairGenerator pairGenerator;
    private ParticipantManager participantManager;


    final int[] num = {4, 5, 1, 3, 4};

    public GeneratedGroupsFrame() {
        setTitle("Generated Groups");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ParticipantManager participantModel = new ParticipantManager();
        CSVFileReader fileReader = new CSVFileReader();
        PairGenerator pairGenerator = new PairGenerator();
        GroupGenerator groupGenerator = new GroupGenerator();

        java.util.List<Pair> initialPair = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants(),num);
        List<Pair> csvPairs = CSVFileReader.getCSV_Pairs();
        List<Pair> concatenatedlist = pairGenerator.makeAllPairsTogether(initialPair, csvPairs);
        List<Participant> succssesorsList = ParticipantManager.getPairSuccessors();
        List<Group> starter = ParticipantManager.getGeneratedGroupsInStarter();


        JPanel groupsPanel = new JPanel();
        groupsPanel.setLayout(new BorderLayout());

        textField = new JTextField();
        groupsPanel.add(textField, BorderLayout.NORTH);

        optionsModel = new DefaultListModel<>();
        optionsModel.addElement("Food Preference");
        optionsModel.addElement("Age Difference");
        optionsModel.addElement("Sex Diversity");
        optionsModel.addElement("Path Length");
        optionsModel.addElement("Short Waiting List");

        originalOptionsModel = new DefaultListModel<>();
        for (int i = 0; i < optionsModel.size(); i++) {
            originalOptionsModel.addElement(optionsModel.elementAt(i));
        }

        optionsList = new JList<>(optionsModel);
        optionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        optionsList.setDragEnabled(true);
        optionsList.setDropMode(DropMode.INSERT);
        optionsList.setTransferHandler(new ListTransferHandler());
        JScrollPane optionsScrollPane = new JScrollPane(optionsList);
        groupsPanel.add(optionsScrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        generateButton = new JButton("Start Generating");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // ParticipantManager participantManager = new ParticipantManager();
                try {
                    fileReader.readCSVFile("src/main/resources/teilnehmerlisteNew.csv");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                java.util.List<Pair> initialPair = pairGenerator.generateInitialPopulation(getParticipants(), num);
                List<Pair> csvPairs = getCSV_Pairs();
                List<Pair> concatenatedlist = pairGenerator.makeAllPairsTogether(initialPair, csvPairs);
                List<Participant> successorsList = participantManager.getPairSuccessors();
                List<Group> firstGroup = groupGenerator.makeStarterGroups(concatenatedlist, num);
                List<Group> starter = ParticipantManager.getGeneratedGroupsInStarter();
                JFrame popup = new JFrame();
                JPanel tablesPanel = new JPanel();
                tablesPanel.setLayout(new GridLayout(2, 1));

                DefaultTableModel starterTableModel = new DefaultTableModel();
                JTable starterTable = new JTable(starterTableModel);
                JScrollPane starterScrollPane = new JScrollPane(starterTable);
                tablesPanel.add(starterScrollPane);

                Vector<Vector<Object>> starterData = new Vector<>();
                for (Group group : starter) {
                    Vector<Object> starterRow = new Vector<>();
                    starterRow.add(group.getPair1());
                    starterRow.add(group.getPair2());
                    starterRow.add(group.getPair3());
                    //  starterRow.add(groupGenerator.makeIndicatorForGroupList(firstGroup));

                    starterData.add(starterRow);
                }

                Vector<String> starterColumnNames = new Vector<>();
                starterColumnNames.add("Pair 1");
                starterColumnNames.add("Pair 2");
                starterColumnNames.add("Pair 3");

                starterTableModel.setDataVector(starterData, starterColumnNames);

                popup.setContentPane(tablesPanel);
                popup.setTitle("Generated Groups");
                popup.setSize(800, 600);
                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
            }
        });

        buttonsPanel.add(generateButton);

        resetButton = new JButton("Reset Preferences");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetOptions();
            }
        });
        buttonsPanel.add(resetButton);

        groupsPanel.add(buttonsPanel, BorderLayout.SOUTH);

        setContentPane(groupsPanel);
    }

    private class ListTransferHandler extends TransferHandler {
        private int sourceIndex;

        @Override
        protected Transferable createTransferable(JComponent c) {
            sourceIndex = optionsList.getSelectedIndex();
            return new StringSelection(optionsList.getSelectedValue());
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }

        @Override
        public boolean canImport(TransferSupport support) {
            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            return dl.getIndex() != sourceIndex;
        }

        @Override
        public boolean importData(TransferSupport support) {
            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            int targetIndex = dl.getIndex();
            if (targetIndex < 0 || targetIndex > optionsModel.getSize()) {
                return false;
            }
            try {
                String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                optionsModel.remove(sourceIndex);
                optionsModel.add(targetIndex, data);
                optionsList.setSelectedIndex(targetIndex);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private void resetOptions() {
        optionsModel.clear();
        for (int i = 0; i < originalOptionsModel.size(); i++) {
            optionsModel.addElement(originalOptionsModel.elementAt(i));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GeneratedGroupsFrame frame = new GeneratedGroupsFrame();
                frame.setVisible(true);
            }
        });
    }
}