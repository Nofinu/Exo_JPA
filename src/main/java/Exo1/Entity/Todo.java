package Exo1.Entity;

import javax.persistence.*;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int todo_id;
    private String title;
    private boolean finish;
    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id",unique = true,nullable = false)
    private Task task;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Todo() {
    }

    public Todo(String title, User user) {
        this.title = title;
        this.finish = false;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(int todo_id) {
        this.todo_id = todo_id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Todo : " +
                "todo_id=" + todo_id +
                ", title='" + title + '\'' +
                ", finish=" + finish +
                ", task=" + task ;
    }
}
