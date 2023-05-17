package Exo1.Entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int task_id;
    private String description ;
    private LocalDate date;
    private int priority;
    @OneToOne(mappedBy = "task",fetch = FetchType.EAGER)
    private Todo todo;

    public Task() {
    }

    public Task(String description, LocalDate date, int priority, Todo todo) {
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.todo = todo;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    @Override
    public String toString() {
        return "task_id=" + task_id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", priority=" + priority;
    }
}
