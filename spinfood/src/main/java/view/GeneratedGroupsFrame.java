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

import static model.ParticipantManager.*;


public class GeneratedGroupsFrame extends JFrame {

    private JButton generateButton;
    private JButton resetButton;
    private DefaultListModel<String> optionsModel;
    private DefaultListModel<String> originalOptionsModel;
    private JList<String> optionsList;
    private JTable first;

    private PairGenerator pairGenerator;
    private ParticipantManager participantManager;


    final int[] num = {4, 5, 1, 3, 4};
    int[] optionValues = new int[5];
    public GeneratedGroupsFrame() {

        setTitle("Generated Groups");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ParticipantManager participantModel = new ParticipantManager();
        CSVFileReader fileReader = new CSVFileReader();
        PairGenerator pairGenerator = new PairGenerator();
        GroupGenerator groupGenerator = new GroupGenerator();
        PartyLocation partyLocation = new PartyLocation();
        Location location = new Location(partyLocation.getLongitude(), partyLocation.getLatitude());
        java.util.List<Pair> initialPair = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants(),num);
        List<Pair> csvPairs = CSVFileReader.getCSV_Pairs();
        List<Pair> concatenatedlist = pairGenerator.makeAllPairsTogether(initialPair, csvPairs);
        List<Participant> succssesorsList = ParticipantManager.getPairSuccessors();
        List<Group> starter = ParticipantManager.getGeneratedGroupsInStarter();


        JPanel groupsPanel = new JPanel();
        groupsPanel.setLayout(new BorderLayout());


        optionsModel = new DefaultListModel<>();
        optionsModel.addElement("Food Preference");
        optionValues[0] = 1;
        optionsModel.addElement("Age Difference");
        optionValues[1] = 2;
        optionsModel.addElement("Sex Diversity");
        optionValues[2] = 3;
        optionsModel.addElement("Path Length");
        optionValues[3] = 4;
        optionsModel.addElement("Short Waiting List");
        optionValues[4] = 5;
        
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
                    fileReader.readCSVFile("spinfood/teilnehmerliste.csv");
                    partyLocation.readCSVFilePartyLocation("spinfood/partylocation.csv");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                java.util.List<Pair> initialPair = pairGenerator.generateInitialPopulation(getParticipants(), num);
                List<Pair> csvPairs = getCSV_Pairs();
                List<Pair> concatenatedlist = pairGenerator.makeAllPairsTogether(initialPair, csvPairs);
               // List<Participant> successorsList = participantManager.getPairSuccessors();
                List<Group> firstGroup = groupGenerator.makeStarterGroups(concatenatedlist, optionValues);

             //   List<Group> mainGroup= participantManager.getGeneratedGroupsInMainDish();

                List<Group> mainGroup = groupGenerator.makeMainDishGroups(concatenatedlist, optionValues,location);
                List<Group> dessertGroup = groupGenerator.makeDessertGroups(concatenatedlist, optionValues,location);
                List<Pair> succList = getStarterSuccessors();



                // List<Group> starter = ParticipantManager.getGeneratedGroupsInStarter();
                JFrame popup = new JFrame();
                JPanel tablesPanel = new JPanel();
                tablesPanel.setLayout(new GridLayout(2, 2));

                JButton compareButton = new JButton("Compare Groups");
                compareButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        compareGroups();
                    }
                });


                DefaultTableModel starterTableModel = new DefaultTableModel();
                JTable starterTable = new JTable(starterTableModel);
                JScrollPane starterScrollPane = new JScrollPane(starterTable);
                starterScrollPane.setBorder(BorderFactory.createTitledBorder("Starter Table"));

                Vector<Vector<Object>> starterData = new Vector<>();
                for (Group group : firstGroup) {
                    Vector<Object> starterRow = new Vector<>();
                    starterRow.add(group.getPair1().toString());
                    starterRow.add(group.getPair2().toString());
                    starterRow.add(group.getPair3().toString());
                    //  starterRow.add(groupGenerator.makeIndicatorForGroupList(firstGroup));

                    starterData.add(starterRow);
                }

                Vector<String> starterColumnNames = new Vector<>();
                starterColumnNames.add("Pair 1");
                starterColumnNames.add("Pair 2");
                starterColumnNames.add("Pair 3");

                starterTableModel.setDataVector(starterData, starterColumnNames);
                tablesPanel.add(starterScrollPane);



                DefaultTableModel mainTableModel = new DefaultTableModel();
                JTable mainTable = new JTable(mainTableModel);
                JScrollPane mainScrollPane = new JScrollPane(mainTable);
                mainScrollPane.setBorder(BorderFactory.createTitledBorder("Main Table"));


                Vector<Vector<Object>> mainData = new Vector<>();
                for (Group group : mainGroup) {
                    Vector<Object> mainRow = new Vector<>();
                    mainRow.add(group.getPair1().toString());
                    mainRow.add(group.getPair2().toString());
                    mainRow.add(group.getPair3().toString());
                    //  starterRow.add(groupGenerator.makeIndicatorForGroupList(firstGroup));

                    mainData.add(mainRow);
                }

                Vector<String> mainColumnNames = new Vector<>();
                mainColumnNames.add("Pair 1");
                mainColumnNames.add("Pair 2");
                mainColumnNames.add("Pair 3");

                mainTableModel.setDataVector(mainData, mainColumnNames);

                tablesPanel.add(mainScrollPane);


                DefaultTableModel dessertTableModel = new DefaultTableModel();
                JTable dessertTable = new JTable(dessertTableModel);
                JScrollPane dessertScrollPane = new JScrollPane(dessertTable);
               dessertScrollPane.setBorder(BorderFactory.createTitledBorder("Dessert Table"));


                Vector<Vector<Object>> dessertData = new Vector<>();
                for (Group group : dessertGroup) {
                    Vector<Object> dessertRow = new Vector<>();
                    dessertRow.add(group.getPair1().toString());
                    dessertRow.add(group.getPair2().toString());
                    dessertRow.add(group.getPair3().toString());
                    //  starterRow.add(groupGenerator.makeIndicatorForGroupList(firstGroup));

                    dessertData.add(dessertRow);
                }

                Vector<String> dessertColumnNames = new Vector<>();
                dessertColumnNames.add("Pair 1");
                dessertColumnNames.add("Pair 2");
                dessertColumnNames.add("Pair 3");

                dessertTableModel.setDataVector(dessertData, dessertColumnNames);

                tablesPanel.add(dessertScrollPane);


                DefaultTableModel succTableModel = new DefaultTableModel();
                JTable succTable = new JTable(succTableModel);
                JScrollPane succScrollPane = new JScrollPane(succTable);
                tablesPanel.add(succScrollPane);

                // Update the table model with the pairs and matching scores
                Vector<Vector<Object>> succData = new Vector<>();
                for (Pair pair : succList) {
                    Vector<Object> pairRow = new Vector<>();
                    pairRow.add(pair.getPerson1().toString());
                    pairRow.add(pair.getPerson2().toString());
                    succData.add(pairRow);
                }
                Vector<String> pairsColumnNames = new Vector<>();

                pairsColumnNames.add("Participant 1");
                pairsColumnNames.add("Participant 2");
                succTableModel.setDataVector(succData, pairsColumnNames);



                popup.setContentPane(tablesPanel);
                popup.setTitle("Generated Groups");
                popup.setSize(1000, 800);
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

    private void compareGroups() {
        JFrame comparisonFrame = new JFrame();
        comparisonFrame.setTitle("Group Comparison");
        comparisonFrame.setSize(800, 600);
        comparisonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comparisonFrame.setLocationRelativeTo(null);



        // Create a JTable and its model to display the comparison data
        DefaultTableModel comparisonTableModel = new DefaultTableModel();
        JTable comparisonTable = new JTable(comparisonTableModel);
        JScrollPane comparisonScrollPane = new JScrollPane(comparisonTable);
        comparisonScrollPane.setBorder(BorderFactory.createTitledBorder("Group Comparison Table"));
        JTable selectedTable;

        // Add the comparison table to the comparison frame
        comparisonFrame.getContentPane().add(comparisonScrollPane);

        // Set the comparison frame visible
        comparisonFrame.setVisible(true);
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
                int sourceIndex = optionsList.getSelectedIndex();
                optionsModel.remove(sourceIndex);
                optionsModel.add(targetIndex, data);
                optionsList.setSelectedIndex(targetIndex);

                int[] updatedOptionValues = new int[optionsModel.getSize()];

                for (int i = 0; i < optionsModel.getSize(); i++) {
                    int originalIndex = originalOptionsModel.indexOf(optionsModel.elementAt(i));
                    updatedOptionValues[i] = optionValues[originalIndex];
                }
                optionValues = updatedOptionValues;

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