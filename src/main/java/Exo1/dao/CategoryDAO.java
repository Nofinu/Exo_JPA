package Exo1.dao;

import Exo1.Entity.Category;
import Exo1.Entity.Todo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CategoryDAO {
    private final EntityManager _em;

    public CategoryDAO(EntityManagerFactory emf) {
        this._em = emf.createEntityManager();
    }

    public void addCategory (Category category){
        _em.getTransaction().begin();
        _em.persist(category);
        _em.getTransaction().commit();
        _em.close();
    }

    public void deleteCategory (int id){
        Category category = _em.find(Category.class,id);
        if(category != null){
            _em.getTransaction().begin();
            _em.remove(category);
            _em.getTransaction().commit();
            _em.close();
        }
    }

    public void addTodoToCategory (int idTodo,int idCategory){
        Todo todo = _em.find(Todo.class,idTodo);
        Category category = _em.find(Category.class,idCategory);
        if(todo != null && category != null){
            todo.addCategory(category);
            category.addTodos(todo);
            _em.getTransaction().begin();
            _em.persist(category);
            _em.getTransaction().commit();
        }else{
            System.out.println("categorie ou todo invalide");
        }

        _em.close();
    }

    public Category findById (int id){
        Category category = _em.find(Category.class,id);
        _em.close();
        return category;
    }

    public void deleteTodoFromCategory (int idTodo,int idCategory){
        Todo todo = _em.find(Todo.class,idTodo);
        Category category = _em.find(Category.class,idCategory);
        if(todo != null && category != null){
            category.removeTodo(todo);
            todo.removeCategory(category);
            _em.getTransaction().begin();
            _em.persist(category);
            _em.getTransaction().commit();
        } else{
            System.out.println("todo ou categorie invalide");
        }
        _em.close();
    }
}
