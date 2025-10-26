import controller.TodoController;
import dao.TodoDAO;
import view.TodoView;

public class TodoApp {
    public static void main(String[] args) {
        try {
            TodoView view = new TodoView();
            TodoDAO dao = new TodoDAO();
            TodoController controller = new TodoController(view, dao);
            view.setController(controller);
        } catch (Exception e) {
            System.out.println("Failed to start app: " + e.getMessage());
        }
    }
}