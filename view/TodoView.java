package view;

import controller.TodoController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TodoView extends JFrame {
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton, markDoneButton, revertButton, sortButton;
    private JLabel messageLabel;
    private TodoController controller;

    public TodoView() {
        setTitle("Todo App");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        addButton = new JButton("Add Task");
        editButton = new JButton("Edit");
        markDoneButton = new JButton("Mark Done");
        deleteButton = new JButton("Delete");
        revertButton = new JButton("Revert to Pending");
        sortButton = new JButton("Sort Tasks");

        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(markDoneButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(revertButton);
        bottomPanel.add(sortButton);

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(bottomPanel);
        southPanel.add(messageLabel);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        addButton.addActionListener(e -> {
            AddTaskDialog dialog = new AddTaskDialog(this);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                controller.handleAddFromDialog(dialog.getTask(), dialog.getDueDate());
            }
        });

        editButton.addActionListener(e -> controller.handleEdit());
        deleteButton.addActionListener(e -> controller.handleDelete());
        markDoneButton.addActionListener(e -> controller.handleMarkDone());
        revertButton.addActionListener(e -> controller.handleRevertToPending());
        sortButton.addActionListener(e -> {
            SortDialog dialog = new SortDialog(this);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                controller.handleSort(dialog.getSelectedOption());
            }
        });
    }

    public void setController(TodoController controller) {
        this.controller = controller;
    }

    public JTable getTaskTable() {
        return taskTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }
}
