package algat;

import java.io.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.util.Callback;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;

public class AlgatApp extends Application {

  private static String projectName = "AlgaT - greedyIsTheWay";

  @FXML
  private Pagination pagRealBackpack;

  @FXML
  private Pagination pagKruskal;

  public static void main(String[] args) {
      launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
    stage.setTitle(projectName);
    stage.setScene(new Scene(root, 900, 600));
    stage.show();
  }

  private Node getQuestionView(String page, Integer id) {
    VBox box = new VBox();
    try {
      String questionPath = "questions/" + page + '/' + id.toString() + ".fxml";
      box = (VBox) FXMLLoader.load(getClass().getResource(questionPath));
    } catch (IOException ex) {
      System.out.println(ex);
    }
    return box;
  }

  public void initialize() {

    pagRealBackpack.setPageFactory(new Callback<Integer, Node>() {
      public Node call(Integer pageIndex) {
        return getQuestionView("realbackpack", pageIndex + 1);
      }
    });

    pagKruskal.setPageFactory(new Callback<Integer, Node>() {
      public Node call(Integer pageIndex) {
        return getQuestionView("kruskal", pageIndex + 1);
      }
    });
  }

  public void initRealBackpack() {
    Parent root;
    try {
        root = FXMLLoader.load(getClass().getResource("RealBackpackView.fxml"));
        Stage stage = new Stage();
        stage.setTitle(projectName);
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }
    catch (IOException e) {
        e.printStackTrace();
    }
  }

  public void initKruskal() {
    Parent root;
    try {
        root = FXMLLoader.load(getClass().getResource("KruskalView.fxml"));
        Stage stage = new Stage();
        stage.setTitle(projectName);
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }
    catch (IOException e) {
        e.printStackTrace();
    }
  }

}