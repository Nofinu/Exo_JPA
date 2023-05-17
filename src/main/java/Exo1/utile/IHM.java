package Exo1.utile;

import Exo1.Entity.Category;
import Exo1.Entity.Task;
import Exo1.Entity.Todo;
import Exo1.Entity.User;
import Exo1.dao.CategoryDAO;
import Exo1.dao.TodoDAO;
import Exo1.dao.UserDAO;

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
    private UserDAO userDAO;
    private CategoryDAO categoryDAO;

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
                case 7:
                    addUser();
                    break;
                case 8:
                    showAllTodoByUser();
                    break;
                case 9:
                    deleteUser();
                case 10:
                    researchByName();
                    break;
                case 11:
                    addCategoryAction();
                    break;
                case 12 :
                    deleteCategoryAction();
                    break;
                case 13:
                    addTodoToCategory();
                    break;
                case 14:
                    showTodoFromCategory();
                    break;
                case 15:
                    deleteTodoFromCategory();
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
        System.out.println("-------------------------------");
        System.out.println("7-- ajouter un utilisateur");
        System.out.println("8-- afficher toute les tache d'un utilisateur");
        System.out.println("------------------------------");
        System.out.println("9-- supprimer un utilisateur");
        System.out.println("10-- recherche par nom ");
        System.out.println("----------------------");
        System.out.println("11-- ajouter une catégorie");
        System.out.println("12-- supprimer une catégorie");
        System.out.println("13-- ajouter une categorie a une todo");
        System.out.println("14-- afficher les todo d'une categorie ");
        System.out.println("15-- supprimer une todo d'une categorie");
        System.out.println("0-- quitter");
    }

    private void addTodo (){
        System.out.println("------ add to do ----");
        System.out.println("id de utilisateur associé a la todo :");
        int id = scanner.nextInt();
        scanner.nextLine();

        userDAO = new UserDAO(emf);
        User user = userDAO.findUserById(id);

        if(user != null){
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

            Todo todo = new Todo(title,user);
            Task task = new Task(description,date,priority,todo);
            todo.setTask(task);

            todoDAO = new TodoDAO(emf);
            todoDAO.addAction(todo);
        }else{
            System.out.println("aucun utilisateur trouver a cette id");
        }

    }

    private void showAllTodo (){
        List<Todo> todoList = null;
        System.out.println("------ afficher toute les todo -------");
        System.out.println("false: todo en cours / true: todo fini");
        boolean status = scanner.nextBoolean();
        scanner.nextLine();
        todoDAO = new TodoDAO(emf);
        todoList = todoDAO.getAllByStatus(status);
        todoList.forEach(System.out::println);
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

    private void addUser (){
        System.out.println("---------- ajout d'un utilisateur --------");
        System.out.println("nom :");
        String name = scanner.nextLine();

        User user = new User(name);
        userDAO = new UserDAO(emf);
        userDAO.addUserAction(user);
    }

    private void showAllTodoByUser (){
        System.out.println("------- affichage des todo par utilisateur --------");
        System.out.println("id de l'utilisateur :");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("------ afficher toute les todo -------");
        System.out.println("false: todo en cours / true: todo fini");
        boolean status = scanner.nextBoolean();
        scanner.nextLine();

        userDAO = new UserDAO(emf);
        User user = userDAO.findUserById(id);
        System.out.println(user.getName()+" :");
        user.getTodos().stream().filter(t ->t.isFinish() == status).forEach(System.out::println);
    }

    private void deleteUser (){
        System.out.println("--------- supression d'un utilisateur ----------");
        System.out.println("id de l'utilisateur :");
        int id = scanner.nextInt();
        scanner.nextLine();

        userDAO= new UserDAO(emf);
        userDAO.deleteUser(id);
    }

    private void researchByName (){
        System.out.println("---- recherche par nom -------");
        System.out.println("recherche de todo : todo / recherche d'utilisateur : user :");
        String input = scanner.nextLine();
        if(input.equals("todo")){
            System.out.println("entrer le titre de la todo rechercher :");
            String todoTitle = scanner.nextLine();
            todoDAO = new TodoDAO(emf);
            List<Todo> todos = todoDAO.findBytitle(todoTitle);
            todos.forEach(System.out::println);

        }else{
            System.out.println("entrer le nom de l'utilisateur:");
            String nameUser = scanner.nextLine();
            userDAO = new UserDAO(emf);
            List<User> users = userDAO.findByName(nameUser);
            users.forEach(u -> u.getTodos().forEach(System.out::println));

        }
    }

    private void addCategoryAction (){
        System.out.println("------- ajout d'une categorie ------");
        System.out.println("nom de la categorie :");
        String name = scanner.nextLine();

        Category category = new Category(name);
        categoryDAO = new CategoryDAO(emf);
        categoryDAO.addCategory(category);
    }

    private void deleteCategoryAction (){
        System.out.println("------- suppresion d'une categorie ------");
        System.out.println("id de la categorie :");
        int id = scanner.nextInt();
        scanner.nextLine();

        categoryDAO = new CategoryDAO(emf);
        categoryDAO.deleteCategory(id);
    }

    private void addTodoToCategory (){
        System.out.println("----- ajouter une todo a une category -----");
        System.out.println("id de la todo :");
        int idTodo = scanner.nextInt();
        System.out.println("id de la category :");
        int  idCategory = scanner.nextInt();

        categoryDAO = new CategoryDAO(emf);
        categoryDAO.addTodoToCategory(idTodo,idCategory);
    }

    private void showTodoFromCategory (){
        System.out.println("------- affichage des todo par leur categorie --------");
        System.out.println("id de la categorie :");
        int id = scanner.nextInt();
        scanner.nextLine();

        categoryDAO = new CategoryDAO(emf);
        Category category = categoryDAO.findById(id);
        if(category != null){
            category.getTodos().forEach(System.out::println);
        }else{
            System.out.println("aucune categorie trouver");
        }
    }

    private void deleteTodoFromCategory (){
        System.out.println("----- suppresion d'une todo d'une categorie");
        System.out.println("id de la categorie :");
        int idCategory = scanner.nextInt();
        System.out.println("id de la todo :");
        int idTodo = scanner.nextInt();
        scanner.nextLine();

        categoryDAO = new CategoryDAO(emf);
        categoryDAO.deleteTodoFromCategory(idTodo,idCategory);
    }

}
