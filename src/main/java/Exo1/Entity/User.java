package Exo1.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;
    private String name;
    @OneToMany (mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Todo> todos;

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id_user +
                ", name='" + name + '\'' +
                '}';
    }
}
