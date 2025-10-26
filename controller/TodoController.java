package controller;

import dao.TodoDAO;
import model.TodoModel;
import view.TodoView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TodoController {
    private final TodoDAO dao;
    private final TodoView view;
    private ArrayList<TodoModel> tasks;

    public TodoController(TodoView view, TodoDAO dao) {
        this.view = view;
        this.dao = dao;
        loadTasks();
    }

    public void loadTasks() {
        try {
            tasks = dao.getAllTasks();
            view.updateTable(tasks);
        } catch (Exception e) {
            view.showError("Error loading tasks: " + e.getMessage());
        }
    }

    public void addTask(String createdBy, String description, String dueDate) {
        if (createdBy == null || createdBy.trim().isEmpty()) {
            view.showError("Please enter your name.");
            return;
        }
        if (description == null || description.trim().isEmpty()) {
            view.showError("Task description cannot be empty.");
            return;
        }
        try {
            String createdDate = LocalDate.now().toString();
            String createdTime = LocalTime.now().withNano(0).toString();
            TodoModel task = new TodoModel(0, createdBy.trim(), description.trim(), createdDate, createdTime, dueDate.trim());
            dao.addTask(task);
            loadTasks();
            view.clearInputs();
        } catch (Exception e) {
            view.showError("Error adding task: " + e.getMessage());
        }
    }

    public void markTaskDone(int taskId) {
        try {
            dao.markTaskDone(taskId);
            loadTasks();
        } catch (Exception e) {
            view.showError("Error marking task done: " + e.getMessage());
        }
    }

    public void deleteTask(int taskId) {
        try {
            dao.deleteTask(taskId);
            loadTasks();
        } catch (Exception e) {
            view.showError("Error deleting task: " + e.getMessage());
        }
    }
}