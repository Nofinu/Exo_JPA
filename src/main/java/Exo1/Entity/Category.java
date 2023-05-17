package Exo1.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int category_id;
    private String name;
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable(name = "category_todo",joinColumns = @JoinColumn(name = "category_id"),
    inverseJoinColumns = @JoinColumn(name = "todo_id"))
    private List<Todo> todos;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public void addTodos(Todo todos) {
        this.todos.add(todos) ;
    }

    public void removeTodo (Todo todo){
        if(this.todos.contains(todo)){
            this.todos.remove(todo);
        }else{
            System.out.println("aucune todo corespondante");
        }
    }

    @Override
    public String toString() {
        return "category_id=" + category_id +
                ", name='" + name;
    }
}
