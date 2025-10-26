package dao;

import model.TodoModel;
import java.sql.*;
import java.util.ArrayList;

public class TodoDAO {
    private Connection conn;

    public TodoDAO() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo?serverTimezone=Asia/Kolkata", "root", "");
    }

    public void addTask(TodoModel task) throws SQLException {
        String sql = "INSERT INTO tasks (created_by, description, created_date, created_time, due_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, task.getCreatedBy());
        stmt.setString(2, task.getDescription());
        stmt.setString(3, task.getCreatedDate());
        stmt.setString(4, task.getCreatedTime());
        stmt.setString(5, task.getDueDate());
        stmt.setBoolean(6, task.isDone());
        stmt.executeUpdate();
    }

    public ArrayList<TodoModel> getAllTasks() throws SQLException {
        ArrayList<TodoModel> list = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            TodoModel task = new TodoModel(
                rs.getInt("id"),
                rs.getString("created_by"),
                rs.getString("description"),
                rs.getString("created_date"),
                rs.getString("created_time"),
                rs.getString("due_date")
            );
            if (rs.getBoolean("status")) task.markDone();
            list.add(task);
        }
        return list;
    }

    public void markTaskDone(int id) throws SQLException {
        String sql = "UPDATE tasks SET status = TRUE WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public void deleteTask(int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}