package Exo1.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "todos",fetch = FetchType.EAGER)
    private List<Category> categoryList = new ArrayList<>();

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

    public int getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(int todo_id) {
        this.todo_id = todo_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void addCategory(Category categoryList) {
        this.categoryList.add(categoryList) ;
    }
    public void removeCategory(Category categoryList) {
        this.categoryList.remove(categoryList) ;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
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
                ", task=" + task +
                ", Category="+ categoryList;
    }
}
