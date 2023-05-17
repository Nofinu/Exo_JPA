package Exo1.dao;

import Exo1.Entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class UserDAO {
    private EntityManager _em;

    public UserDAO(EntityManagerFactory emf) {
        this._em = emf.createEntityManager();
    }

    public void addUserAction (User user){
        _em.getTransaction().begin();
        _em.persist(user);
        _em.getTransaction().commit();
        _em.close();
    }

    public User findUserById (int id){
        User user = _em.find(User.class,id);
        _em.close();
        return user;
    }

    public void deleteUser (User user){
        _em.getTransaction().begin();
        _em.remove(user);
        _em.getTransaction().commit();
        _em.close();
    }
}
