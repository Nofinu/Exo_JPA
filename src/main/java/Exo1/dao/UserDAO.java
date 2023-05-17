package Exo1.dao;

import Exo1.Entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class UserDAO {
    private final EntityManager _em;

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

    public void deleteUser (int id){
        _em.getTransaction().begin();
        User user = _em.find(User.class,id);
        if(user != null){
            _em.remove(user);
            _em.getTransaction().commit();
        }
        _em.close();
    }

    public List<User> findByName (String name){
        Query query = _em.createQuery("select u from User u where u.name like :name",User.class);
        query.setParameter("name","%"+name+"%");
        List<User> users = query.getResultList();
        return users;
    }
}
