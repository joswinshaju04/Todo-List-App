package controller;

import dao.TodoDAO;
import model.TodoModel;
import view.TodoView;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TodoController {
    private TodoView view;
    private TodoDAO dao;
    private String username;
    private List<TodoModel> currentTasks = new ArrayList<>();

    public TodoController(TodoView view, TodoDAO dao, String username) {
        this.view = view;
        this.dao = dao;
        this.username = username;
        view.setController(this);
        loadTasks();
    }

    private void loadTasks() {
        currentTasks = dao.getTasks(username);
        reloadTable();
    }

    private void reloadTable() {
        view.getTableModel().setRowCount(0);
        for (TodoModel task : currentTasks) {
            view.getTableModel().addRow(new Object[]{
                task.getCreatedBy(),
                task.getDescription(),
                task.getCreatedDate(),
                task.getCreatedTime(),
                task.getDueDate(),
                task.getStatus()
            });
        }
    }

    public void handleAddFromDialog(String desc, String due) {
        if (desc.isEmpty() || due.isEmpty()) {
            view.getMessageLabel().setText("Task and due date required.");
            return;
        }

        try {
            LocalDate.parse(due);
        } catch (DateTimeParseException e) {
            view.getMessageLabel().setText("Invalid date format. Use YYYY-MM-DD.");
            return;
        }

        dao.addTask(username, desc, due);
        view.getMessageLabel().setText("Task added.");
        loadTasks();
    }

    public void handleEdit() {
        int row = view.getTaskTable().getSelectedRow();
        if (row == -1) {
            view.getMessageLabel().setText("Select a task to edit.");
            return;
        }

        int taskId = currentTasks.get(row).getId();
        String currentDesc = currentTasks.get(row).getDescription();
        String currentDue = currentTasks.get(row).getDueDate();

        String newDesc = JOptionPane.showInputDialog(view, "Edit Description:", currentDesc);
        if (newDesc == null || newDesc.trim().isEmpty()) {
            view.getMessageLabel().setText("Edit cancelled or empty description.");
            return;
        }

        String newDue = JOptionPane.showInputDialog(view, "Edit Due Date (YYYY-MM-DD):", currentDue);
        if (newDue == null || newDue.trim().isEmpty()) {
            view.getMessageLabel().setText("Edit cancelled or empty due date.");
            return;
        }

        try {
            LocalDate.parse(newDue);
        } catch (DateTimeParseException e) {
            view.getMessageLabel().setText("Invalid date format. Use YYYY-MM-DD.");
            return;
        }

        dao.updateTaskById(taskId, newDesc.trim(), newDue.trim());
        view.getMessageLabel().setText("Task updated.");
        loadTasks();
    }

    public void handleDelete() {
        int[] selectedRows = view.getTaskTable().getSelectedRows();
        if (selectedRows.length == 0) {
            view.getMessageLabel().setText("Select one or more tasks to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Delete selected tasks?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        for (int row : selectedRows) {
            int taskId = currentTasks.get(row).getId();
            dao.deleteTaskById(taskId);
        }

        view.getMessageLabel().setText("Selected tasks deleted.");
        loadTasks();
    }

    public void handleMarkDone() {
        int[] selectedRows = view.getTaskTable().getSelectedRows();
        if (selectedRows.length == 0) {
            view.getMessageLabel().setText("Select one or more tasks to mark done.");
            return;
        }

        for (int row : selectedRows) {
            int taskId = currentTasks.get(row).getId();
            dao.markTaskDoneById(taskId);
        }

        view.getMessageLabel().setText("Selected tasks marked as done.");
        loadTasks();
    }

    public void handleRevertToPending() {
        int row = view.getTaskTable().getSelectedRow();
        if (row == -1) {
            view.getMessageLabel().setText("Select a task to revert.");
            return;
        }

        TodoModel task = currentTasks.get(row);
        if (!task.getStatus().equalsIgnoreCase("Done")) {
            view.getMessageLabel().setText("Only 'Done' tasks can be reverted.");
            return;
        }

        dao.markTaskPendingById(task.getId());
        view.getMessageLabel().setText("Task reverted to pending.");
        loadTasks();
    }

    public void handleSort(String option) {
        switch (option) {
            case "Due Date":
                currentTasks.sort((a, b) -> a.getDueDate().compareTo(b.getDueDate()));
                break;
            case "Status":
                currentTasks.sort((a, b) -> a.getStatus().compareToIgnoreCase(b.getStatus()));
                break;
            case "Description":
                currentTasks.sort((a, b) -> a.getDescription().compareToIgnoreCase(b.getDescription()));
                break;
        }
        view.getMessageLabel().setText("Tasks sorted by " + option.toLowerCase() + ".");
        reloadTable();
    }
}
