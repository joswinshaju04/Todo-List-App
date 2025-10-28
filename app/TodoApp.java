package app;

import controller.TodoController;
import dao.TodoDAO;
import view.TodoView;
import view.LoginView;

public class TodoApp {
    public TodoApp(String username) {
        TodoDAO dao = new TodoDAO();
        TodoView view = new TodoView();
        TodoController controller = new TodoController(view, dao, username);
        view.setController(controller);
    }

    public static void main(String[] args) {
        new LoginView(); // Start with login screen
    }
}