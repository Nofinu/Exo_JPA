package Exo1.utile;

import Exo1.Entity.Task;
import Exo1.Entity.Todo;
import Exo1.dao.TodoDAO;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
                case 0:
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


        System.out.println("description de la tache :");
        String description = scanner.nextLine();

        System.out.println("date format(dd-MM-yyyy) :");
        String dateString = scanner.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            date = new Date("01/01/2001");
        }

        System.out.println("priorité de la tache :");
        int priority = scanner.nextInt();
        scanner.nextLine();

        Todo todo = new Todo(title);
        Task task = new Task(description,date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),priority,todo);
        todo.setTask(task);

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
