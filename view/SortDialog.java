package view;

import javax.swing.*;
import java.awt.*;

public class SortDialog extends JDialog {
    private JComboBox<String> sortOptions;
    private JButton applyButton;
    private boolean confirmed = false;

    public SortDialog(JFrame parent) {
        super(parent, "Sort Tasks", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        String[] options = {"Due Date", "Status", "Description"};
        sortOptions = new JComboBox<>(options);
        applyButton = new JButton("Apply");

        applyButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });

        JPanel centerPanel = new JPanel();
        centerPanel.add(new JLabel("Sort by:"));
        centerPanel.add(sortOptions);

        add(centerPanel, BorderLayout.CENTER);
        add(applyButton, BorderLayout.SOUTH);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getSelectedOption() {
        return (String) sortOptions.getSelectedItem();
    }
}
