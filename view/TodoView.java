package view;

import controller.TodoController;
import model.TodoModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TodoView extends JFrame {
    private final JTextField nameField;
    private final JTextField taskField;
    private final JTextField dateField;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private ArrayList<TodoModel> tasks;
    private TodoController controller;

    public TodoView() {
        setTitle("📝 Todo App");
        setSize(950, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Row 0: Created By
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Created By:");
        nameLabel.setFont(labelFont);
        inputPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(30);
        nameField.setFont(labelFont);
        inputPanel.add(nameField, gbc);

        // Row 1: Task Description
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel descLabel = new JLabel("Task Description:");
        descLabel.setFont(labelFont);
        inputPanel.add(descLabel, gbc);

        gbc.gridx = 1;
        taskField = new JTextField(30);
        taskField.setFont(labelFont);
        inputPanel.add(taskField, gbc);

        // Row 2: Due Date
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel dueDateLabel = new JLabel("Due Date (YYYY-MM-DD):");
        dueDateLabel.setFont(labelFont);
        inputPanel.add(dueDateLabel, gbc);

        gbc.gridx = 1;
        dateField = new JTextField(30);
        dateField.setFont(labelFont);
        inputPanel.add(dateField, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton addButton = new JButton("➕ Add Task");
        JButton doneButton = new JButton("✅ Mark as Done");
        JButton deleteButton = new JButton("🗑️ Delete Task");

        buttonPanel.add(addButton);
        buttonPanel.add(doneButton);
        buttonPanel.add(deleteButton);

        // Table setup
        tableModel = new DefaultTableModel(new String[]{
            "Created By", "Description", "Created Date", "Created Time", "Due Date", "Status"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button Actions
        addButton.addActionListener(e -> {
            if (controller != null) {
                controller.addTask(nameField.getText(), taskField.getText(), dateField.getText());
            }
        });

        doneButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (controller != null && selectedRow != -1 && tasks != null) {
                int taskId = tasks.get(selectedRow).getId();
                controller.markTaskDone(taskId);
            } else {
                showError("Please select a task to mark as done.");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (controller != null && selectedRow != -1 && tasks != null) {
                int taskId = tasks.get(selectedRow).getId();
                controller.deleteTask(taskId);
            } else {
                showError("Please select a task to delete.");
            }
        });
    }

    public void setController(TodoController controller) {
        this.controller = controller;
        setVisible(true);
    }

    public void updateTable(ArrayList<TodoModel> tasks) {
        this.tasks = tasks;
        tableModel.setRowCount(0);
        for (TodoModel task : tasks) {
            tableModel.addRow(new Object[]{
                task.getCreatedBy(),
                task.getDescription(),
                task.getCreatedDate(),
                task.getCreatedTime(),
                task.getDueDate(),
                task.isDone() ? "Done" : "Pending"
            });
        }
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void clearInputs() {
        nameField.setText("");
        taskField.setText("");
        dateField.setText("");
    }
}