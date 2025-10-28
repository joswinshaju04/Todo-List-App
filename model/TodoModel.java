package model;

public class TodoModel {
    private int id;
    private String createdBy;
    private String description;
    private String createdDate;
    private String createdTime;
    private String dueDate;
    private String status;

    public TodoModel(int id, String createdBy, String description, String createdDate, String createdTime, String dueDate, String status) {
        this.id = id;
        this.createdBy = createdBy;
        this.description = description;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getId() { return id; }
    public String getCreatedBy() { return createdBy; }
    public String getDescription() { return description; }
    public String getCreatedDate() { return createdDate; }
    public String getCreatedTime() { return createdTime; }
    public String getDueDate() { return dueDate; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setDescription(String description) { this.description = description; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
    public void setCreatedTime(String createdTime) { this.createdTime = createdTime; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public void setStatus(String status) { this.status = status; }
}