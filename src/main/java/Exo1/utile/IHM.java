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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
                case 5:
                    showTodoBefore();
                    break;
                case 6:
                    showTodoBetween();
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
        System.out.println("5-- afficher les todo avant une date");
        System.out.println("6-- afficher les todos entre 2 dates");
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
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        System.out.println("priorité de la tache :");
        int priority = scanner.nextInt();
        scanner.nextLine();

        Todo todo = new Todo(title);
        Task task = new Task(description,date,priority,todo);
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

    private void showTodoBefore (){
        System.out.println("-------- affichage des todo avant une date --------");
        System.out.println("date format(dd-MM-yyyy) :");
        String dateString = scanner.nextLine();
        LocalDate beforeDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        todoDAO = new TodoDAO(emf);
        List<Todo> todos = todoDAO.getAll();
        List<Todo> todoListBefore =todos.stream().filter(t -> t.getTask().getDate().isBefore(beforeDate)).toList();
        todoListBefore.forEach(System.out::println);
    }

    private void showTodoBetween (){
        System.out.println("-------- affichage des todo entre 2 dates --------");
        System.out.println("date debut format(dd-MM-yyyy) :");
        String dateString = scanner.nextLine();
        LocalDate dateStart = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        System.out.println("date fin format(dd-MM-yyyy) :");
        dateString = scanner.nextLine();
        LocalDate dateEnd = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        todoDAO = new TodoDAO(emf);
        List<Todo> todos = todoDAO.getAllBetweenDate(dateStart,dateEnd);
        todos.forEach(System.out::println);
    }

}
