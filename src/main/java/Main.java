import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            new Main().invokeConsoleMenu();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement getConnectionStatement()   {
        Statement statement = null;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return statement;
    }

    public void createNewStudentsTable() throws SQLException {
            getConnectionStatement().executeUpdate("CREATE TABLE person (id INTEGER, name VARCHAR(100), " +
                                                            "averageMark INTEGER, faculty VARCHAR(100), age INTEGER)");
            System.out.println("Table is created");
    }

    public void deleteStudentsTable() throws SQLException {
            getConnectionStatement().executeUpdate("DROP TABLE person");
            System.out.println("Table with students is deleted");
    }

    public void addNewStudentToDB() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter student's name");
        String name = input.nextLine();
        System.out.println("Enter student's average mark");
        int averageMark = input.nextInt();
        System.out.println("Enter student's faculty");
        String faculty = input.nextLine();
        System.out.println("Enter student's age");
        int age = input.nextInt();
        Random randomID = new Random();
        int ID = randomID.nextInt(500);

        Student student = new Student(ID ,name, averageMark, faculty, age);
        getConnectionStatement().executeUpdate(String.format("INSERT INTO person VALUES(%d, '%s', %d, %s, %d)",
                student.getID(), student.getName(), student.getAverageMark(), student.getFaculty(), student.getAge()));
        System.out.println("The student is added!");
    }

    public void displaySortedStudentTable() throws SQLException {
         getConnectionStatement().executeUpdate("SELECT * FROM person ORDER BY name ASC");
         System.out.println("The sorted table is displayed!");
    }

    public void findStudentsInTable() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int userInput = 0;
        ResultSet resultSet = null;

        System.out.println("To find student(s) pick a field to be searched \n" +
                "1 - ID \n" +
                "2 - student(s) name \n" +
                "3 - average mark \n" +
                "4 - faculty \n" +
                "5 - age value");
        while (userInput != 2) {
            switch (scanner.nextInt()) {
                case 1 :
                    System.out.print("Enter the id: ");
                    resultSet = getConnectionStatement().executeQuery(String.
                            format("SELECT * FROM person WHERE id = %d ORDER BY name ASC", scanner.nextInt()));
                    break;
                case 2 :
                    System.out.print("Enter the name: ");
                    resultSet = getConnectionStatement().executeQuery(String.
                            format("SELECT * FROM person WHERE name = '%s' ORDER BY name ASC", scanner.nextLine()));
                    break;
                case 3 :
                    resultSet = getConnectionStatement().executeQuery(String.
                            format("SELECT * FROM person WHERE averageMark = %d ORDER BY name ASC", scanner.nextInt()));
                    break;
                case 4 :
                    System.out.print("Enter the faculty name: ");
                    resultSet = getConnectionStatement().executeQuery(String.
                            format("SELECT * FROM person WHERE faculty = '%s' ORDER BY name ASC", scanner.nextLine()));
                    break;
                case 5 :
                    System.out.print("Enter the age: ");
                    resultSet = getConnectionStatement().executeQuery(String.
                            format("SELECT * FROM person WHERE age = %d ORDER BY name ASC", scanner.nextInt()));
                    break;
                default:
                    System.out.println("Try again, please. You haven't typed any of the given command.");
            }

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int averageMark = resultSet.getInt("averageMark");
            String faculty = resultSet.getString("faculty");
            int age = resultSet.getInt("age");

            System.out.printf("Current student data: %d, '%s', %d, %s, %d ", id, name, averageMark, faculty, age);

            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("name");
                averageMark = resultSet.getInt("averageMark");
                faculty = resultSet.getString("faculty");
                age = resultSet.getInt("age");

                System.out.printf("Current student data: %d, '%s', %d, %s, %d ", id, name, averageMark, faculty, age);
            }

            System.out.println("If you want to proceed, type \"1\" otherwise \"2\" ");
            userInput = scanner.nextInt();
        }
        System.out.println("The student(s) found!");
    }

    public void invokeConsoleMenu() throws SQLException {
        int input = 0;
        while (input != 9) {
            System.out.println("Choose a service from the menu: \n" +
                                "1 - create a table with students \n" +
                                "2 - delete a table \n" +
                                "3 - add a student \n" +
                                "4 - display sorted table \n" +
                                "5 - find student(s)  \n" +
                                "6 - do something \n" +
                                "7 - create a text file \n" +
                                "8 - download a film \n" +
                                "9 - shutdown the program");
            Scanner scanner = new Scanner(System.in);
            switch (input = scanner.nextInt()) {
                case 1:
                    createNewStudentsTable();
                break;
                case 2:
                    deleteStudentsTable();
                break;
                case 3:
                    addNewStudentToDB();
                break;
                case 4:
                    displaySortedStudentTable();
                break;
                case 5:
                    findStudentsInTable();
                break;
                case 6: System.out.println("Something has happened!");
                break;
                case 7: System.out.println("A text file is created");
                break;
                case 8: System.out.println("The downloading of a film is started");
                break;
                case 9: System.out.println("Ok, i'm done...");
                break;
                default:
                    System.out.println("What are you try'na to say?");
            }
        }
    }
}
