package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.UUID;

public class Controller implements Initializable {
    @FXML
    private ListView<Student> Students;
    @FXML
    Button Create;
    @FXML
    Button Delete;
    @FXML
    Button Load;
    @FXML
    JFXButton GPA3;
    @FXML
    JFXButton MajorBio;
    @FXML
    JFXButton Age25;

    final String hostname = "studentdb.cnfxx7covndy.us-east-1.rds.amazonaws.com";
    final String dbName = "studentdb";
    final String port = "3306";
    final String username = "admin";
    final String password = "Powerkill11";
    final String AWS_URL = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + username + "&password=" + password;

    private void createDatabase(String url) {
        try {

            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            try {
                stmt.execute("CREATE TABLE Student (" +
                        "Name1 CHAR(25), " +
                        "Age VARCHAR(25), " +
                        "GPA VARCHAR(40), " +
                        "Major CHAR(25)," +
                        "Id VARCHAR(38) )");

                System.out.println("TABLE CREATED");
            } catch (Exception ex) {
                System.out.println("TABLE ALREADY EXISTS, NOT CREATED");
            }
            UUID id;
            String idString;
            for (int i = 0; i < 10; i++) {
                id = UUID.randomUUID();
                idString = id.toString();
                String name = "Student " + i;
                String age = "2" + i;
                String Major;
                double n = i;
                if (i % 2 == 1) {
                    Major = "Biology";
                } else {
                    Major = "Math";
                }
                String GPA = "" + (2 + n / 10);
                String sql = "INSERT INTO Student VALUES" + "('" + name + "', '" + age + "', '" + GPA + "', '" + Major + "','" + idString + "')";
                stmt.executeUpdate(sql);
            }
            System.out.println("TABLE FILLED");
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            String msg = ex.getMessage();
            System.out.println(msg);
        }
    }

   private void GPAFilter(String url) {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sqlStatement = "SELECT Name1, Age, Major, GPA, Id FROM Student";
            ResultSet result = stmt.executeQuery(sqlStatement);
            ObservableList<Student> GPAFilterList = FXCollections.observableArrayList();
            while (result.next()) {
                Student student = new Student();
                if(result.getDouble("GPA")<2.5) {
                    student.Id = UUID.fromString(result.getString("Id"));
                    student.Name1 = result.getString("Name1");
                    student.Major = result.getString("Major");
                    student.GPA = result.getDouble("GPA");
                    student.Age = result.getInt("Age");
                    GPAFilterList.add(student);
                }
                else{
                    break;
                }
            }
            Students.setItems(GPAFilterList);

            System.out.println("DATA LOADED");
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            String msg = ex.getMessage();
            System.out.println("DATA NOT LOADED");
            System.out.println(msg);
        }
    }
    private void BioFilter(String url) {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sqlStatement = "SELECT Name1, Age, Major, GPA, Id FROM Student";
            ResultSet result = stmt.executeQuery(sqlStatement);
            ObservableList<Student> BioFilterList = FXCollections.observableArrayList();
            String bio = "Biology";
            while (result.next()) {
                Student student = new Student();
                if(result.getString("Major").equals(bio)) {
                    student.Id = UUID.fromString(result.getString("Id"));
                    student.Name1 = result.getString("Name1");
                    student.Major = result.getString("Major");
                    student.GPA = result.getDouble("GPA");
                    student.Age = result.getInt("Age");
                    BioFilterList.add(student);
                }
                else{
                }
            }
            Students.setItems(BioFilterList);

            System.out.println("DATA LOADED");
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            String msg = ex.getMessage();
            System.out.println("DATA NOT LOADED");
            System.out.println(msg);
        }
    }
    private void AgeFilter(String url) {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sqlStatement = "SELECT Name1, Age, Major, GPA, Id FROM Student";
            ResultSet result = stmt.executeQuery(sqlStatement);
            ObservableList<Student> AgeFilterList = FXCollections.observableArrayList();
            while (result.next()) {
                Student student = new Student();
                if(result.getInt("Age")>25) {
                    student.Id = UUID.fromString(result.getString("Id"));
                    student.Name1 = result.getString("Name1");
                    student.Major = result.getString("Major");
                    student.GPA = result.getDouble("GPA");
                    student.Age = result.getInt("Age");
                    AgeFilterList.add(student);
                }
                else{

                }
            }
            Students.setItems(AgeFilterList);

            System.out.println("DATA LOADED");
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            String msg = ex.getMessage();
            System.out.println("DATA NOT LOADED");
            System.out.println(msg);
        }
    }
    private void deleteDat(String url) {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE Student");
            stmt.close();
            conn.close();
            System.out.println("TABLE DROPPED");
        } catch (Exception ex) {
            String msg = ex.getMessage();
            System.out.println("TABLE NOT DROPPED");
            System.out.println(msg);
        }
    }

    private void loadData(String url) {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sqlStatement = "SELECT Name1, Age, GPA, Major, Id FROM Student";
            ResultSet result = stmt.executeQuery(sqlStatement);
            ObservableList<Student> dbStudentList = FXCollections.observableArrayList();
            while (result.next()) {
                Student student = new Student();
                student.Id = UUID.fromString(result.getString("Id"));
                student.Name1 = result.getString("Name1");
                student.Major = result.getString("Major");
                student.GPA = result.getDouble("GPA");
                student.Age = result.getInt("Age");
                dbStudentList.add(student);
            }
            Students.setItems(dbStudentList);

            System.out.println("DATA FILTERED");
            stmt.close();
            conn.close();
        }catch (Exception ex) {
            String msg = ex.getMessage();
            System.out.println("TABLE NOT FILTERED");
            System.out.println(msg);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createDatabase(AWS_URL);
            }
        });
        Load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadData(AWS_URL);
            }
        });
        Delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteDat(AWS_URL);
            }
        });
        GPA3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               GPAFilter(AWS_URL);
            }
        });
        MajorBio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BioFilter(AWS_URL);
            }
        });
        Age25.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AgeFilter(AWS_URL);
            }
        });

    }
}

