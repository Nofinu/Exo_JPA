package Exo1.dao;

import Exo1.Entity.Todo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.time.LocalDate;
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

    public List<Todo> getAllByStatus (boolean id){
        List<Todo> todos = null;
        Query query = _em.createQuery("select t from Todo t where t.finish = :status",Todo.class);
        query.setParameter("status",id);
        todos = query.getResultList();
        _em.close();
        return todos;
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

        _em.persist(todo);
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

    public List<Todo> getAllBetweenDate (LocalDate startDate,LocalDate endDate){
        Query query = _em.createQuery("select t from Todo t where t.task.date < :endDate and t.task.date >:startDate ",Todo.class);
        query.setParameter("startDate",startDate);
        query.setParameter("endDate",endDate);
        List<Todo> todos =query.getResultList();
        _em.close();
        return todos;
    }
}
