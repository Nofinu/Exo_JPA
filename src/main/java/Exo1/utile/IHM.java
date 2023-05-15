package Exo1.utile;

import Exo1.Entity.Todo;
import Exo1.dao.TodoDAO;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class IHM {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_exo");
    private Scanner scanner;
    private TodoDAO todoDAO;

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
        System.out.println("------ add to do ----");
        System.out.println("nom de la to do :");
        String title = scanner.nextLine();
        Todo todo = new Todo(title);

        todoDAO = new TodoDAO(emf);
        todoDAO.addAction(todo);
    }

    private void showAllTodo (){
        List<Todo> todoList = null;
        System.out.println("------ afficher toute les todo -------");
        todoDAO = new TodoDAO(emf);
        todoList = todoDAO.getAll();
        todoList.forEach(e -> System.out.println(e));
    }

    private void changeStatus (){
        System.out.println("------- changement du statut d'une todo");
        System.out.println("id de la todo :");
        int id = scanner.nextInt();
        scanner.nextLine();
        todoDAO = new TodoDAO(emf);
        todoDAO.changeStatusAction(id);
    }

    private void deleteTodo (){
        System.out.println("--------- dsupression de todo ---------");
        System.out.println("id de la todo :");
        int id = scanner.nextInt();
        scanner.nextLine();
        todoDAO = new TodoDAO(emf);
        todoDAO.deleteAction(id);
    }

}
