package utile;

import Entity.Todo;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class IHM {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_exo");
    private Scanner scanner;
    private  EntityManager em;

    public IHM(){
        scanner = new Scanner(System.in);
    }

    public void start (){
        int entry;
        do{
            menu();
            entry = scanner.nextInt();
            scanner.nextLine();

            switch (entry){
                case 1:
                    addTodo();
                    break;
                case 2:
                    showAllTodo();
                    break;
                case 3:
                    changeStatus();
                    break;
                case 4:
                    deleteTodo();
                    break;
                default :
                    System.out.println("entrer une valeur valide ");
                    break;

            }

        }while(entry != 0);

        emf.close();
    }

    private void menu (){
        System.out.println("------menu--------");
        System.out.println("1-- ajouter une tache ");
        System.out.println("2-- afficher toute les taches");
        System.out.println("3-- marquer une tache comme fini");
        System.out.println("4-- supprimer une tache");
        System.out.println("0-- quitter");
    }

    private void addTodo (){
        em = emf.createEntityManager();
        em.getTransaction().begin();

        System.out.println("------ add to do ----");
        System.out.println("nom de la to do :");
        String title = scanner.nextLine();
        Todo todo = new Todo(title);

        em.persist(todo);
        em.getTransaction().commit();
        em.close();
    }

    private void showAllTodo (){
        em = emf.createEntityManager();
        List<Todo> todoList = null;

        System.out.println("------ afficher toute les todo -------");
        todoList = em.createQuery("select t from Todo t",Todo.class).getResultList();
        todoList.forEach(e -> System.out.println(e));

        em.close();
    }

    private void changeStatus (){
        em = emf.createEntityManager();
        em.getTransaction().begin();

        System.out.println("------- changement du statut d'une todo");
        System.out.println("id de la todo :");
        int id = scanner.nextInt();
        scanner.nextLine();

        Todo todo = em.find(Todo.class,id);
        todo.setFinish(true);

        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    private void deleteTodo (){
        em = emf.createEntityManager();
        em.getTransaction().begin();

        System.out.println("--------- dsupression de todo ---------");
        System.out.println("id de la todo :");
        int id = scanner.nextInt();
        scanner.nextLine();
        Todo todo = em.find(Todo.class,id);
        em.remove(todo);

        em.getTransaction().commit();
        em.close();
    }

}
