package view;

import javax.swing.*;
import java.awt.*;

public class AddTaskDialog extends JDialog {
    private JTextField taskField, dueDateField;
    private JButton saveButton;
    private boolean confirmed = false;

    public AddTaskDialog(JFrame parent) {
        super(parent, "Add New Task", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 10, 10));

        taskField = new JTextField();
        dueDateField = new JTextField();

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });

        add(new JLabel("Task Description:"));
        add(taskField);
        add(new JLabel("Due Date (YYYY-MM-DD):"));
        add(dueDateField);
        add(new JLabel(""));
        add(saveButton);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getTask() {
        return taskField.getText().trim();
    }

    public String getDueDate() {
        return dueDateField.getText().trim();
    }
}
