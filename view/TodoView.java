package view;

import controller.TodoController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TodoView extends JFrame {
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JTextField taskField, dueDateField;
    private JButton addButton, editButton, deleteButton, markDoneButton;
    private JLabel messageLabel;
    private TodoController controller;

    public TodoView() {
        setTitle("Todo App");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Top input panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 30));
        taskField = new JTextField(20);
        dueDateField = new JTextField(10);
        addButton = new JButton("Add");

        topPanel.add(new JLabel("Task:"));
        topPanel.add(taskField);
        topPanel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        topPanel.add(dueDateField);
        topPanel.add(addButton);

        // Table model
        tableModel = new DefaultTableModel(new String[]{
             "Created By", "Description", "Created Date", "Created Time", "Due Date", "Status"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        taskTable = new JTable(tableModel);
        taskTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setPreferredSize(new Dimension(850, 300));

        // Bottom button panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        editButton = new JButton("Edit");
        markDoneButton = new JButton("Mark Done");
        deleteButton = new JButton("Delete");
        bottomPanel.add(editButton);
        bottomPanel.add(markDoneButton);
        bottomPanel.add(deleteButton);

        // Message label
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Assemble layout
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(bottomPanel);
        southPanel.add(messageLabel);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Button actions
        addButton.addActionListener(e -> controller.handleAdd());
        editButton.addActionListener(e -> controller.handleEdit());
        deleteButton.addActionListener(e -> controller.handleDelete());
        markDoneButton.addActionListener(e -> controller.handleMarkDone());
    }

    public void setController(TodoController controller) {
        this.controller = controller;
    }

    // Getters
    public JTable getTaskTable() { return taskTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTextField getTaskField() { return taskField; }
    public JTextField getDueDateField() { return dueDateField; }
    public JLabel getMessageLabel() { return messageLabel; }
}