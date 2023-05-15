package dao;

import Entity.Todo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TodoDAO {
    private EntityManager _em;

    public TodoDAO(EntityManagerFactory emf) {
        this._em = emf.createEntityManager();
    }

    public void addAction (Todo todo){
        _em.getTransaction().begin();
        _em.persist(todo);
        _em.getTransaction().commit();
        _em.close();
    }

    public List<Todo> getAll (){
        List<Todo> todos = _em.createQuery("select t from Todo t",Todo.class).getResultList();
        _em.close();
        return todos;
    }

    public void changeStatusAction (int id ){
        _em.getTransaction().begin();
        Todo todo = _em.find(Todo.class,id);
        todo.setFinish(true);

        _em.flush();
        _em.getTransaction().commit();
        _em.close();
    }

    public void deleteAction (int id){
        _em.getTransaction().begin();
        Todo todo = _em.find(Todo.class,id);
        _em.remove(todo);

        _em.getTransaction().commit();
        _em.close();
    }
}
