package view;

import model.FoodPreference;
import model.HasKitchen;
import model.Participant;
import model.Sex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditParticipantDialog extends JDialog {
    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField sexField;
    private JTextField foodPreferenceField;
    private JTextField hasKitchenField;

    private Participant participant;
    private boolean participantUpdated;
    private JComboBox comboBox1;

    public EditParticipantDialog(JFrame parentFrame, Participant participant) {
        super(parentFrame, "Edit Participant", true);
        this.participant = participant;
        participantUpdated = false;

        setSize(400, 300);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10)); // Increase row count to accommodate hasKitchen field

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(participant.getId());
        idField.setEditable(false);
        formPanel.add(idLabel);
        formPanel.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(participant.getName());
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField(String.valueOf(participant.getAge()));
        formPanel.add(ageLabel);
        formPanel.add(ageField);

        JLabel sexLabel = new JLabel("Sex:");
        sexField = new JTextField(String.valueOf(participant.getSex()));
        formPanel.add(sexLabel);
        formPanel.add(sexField);

        JLabel foodPreferenceLabel = new JLabel("Food Preference:");
        foodPreferenceField = new JTextField(String.valueOf(participant.getFoodPreference()));
        formPanel.add(foodPreferenceLabel);
        formPanel.add(foodPreferenceField);

        JLabel hasKitchenLabel = new JLabel("Has Kitchen:"); // Add a label for the hasKitchen field
        hasKitchenField = new JTextField(String.valueOf(participant.getHasKitchen()));
        formPanel.add(hasKitchenLabel);
        formPanel.add(hasKitchenField);

        add(formPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveParticipant();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveParticipant() {
        String name = nameField.getText().trim();
        int age = Integer.parseInt(ageField.getText().trim());
        String sex = sexField.getText().trim();
        String foodPreference = foodPreferenceField.getText().trim();
        String hasKitchen = hasKitchenField.getText().trim();

        participant.setName(name);
        participant.setAge(age);
        participant.setSex(Sex.valueOf(sex));
        participant.setFoodPreference(FoodPreference.valueOf(foodPreference));
        participant.setHasKitchen(HasKitchen.valueOf(hasKitchen));

        participantUpdated = true;
        dispose();
    }

    public boolean isParticipantUpdated() {
        return participantUpdated;
    }
}
