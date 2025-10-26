package model;

public class TodoModel {
    private int id;
    private String createdBy;
    private String description;
    private String createdDate;
    private String createdTime;
    private String dueDate;
    private boolean isDone;

    public TodoModel(int id, String createdBy, String description, String createdDate, String createdTime, String dueDate) {
        this.id = id;
        this.createdBy = createdBy;
        this.description = description;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.dueDate = dueDate;
        this.isDone = false;
    }

    public int getId() { return id; }
    public String getCreatedBy() { return createdBy; }
    public String getDescription() { return description; }
    public String getCreatedDate() { return createdDate; }
    public String getCreatedTime() { return createdTime; }
    public String getDueDate() { return dueDate; }
    public boolean isDone() { return isDone; }

    public void markDone() { this.isDone = true; }
}