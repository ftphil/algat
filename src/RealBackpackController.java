package algat.controller;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.util.Pair;

public class RealBackpackController {

  @FXML
  private HBox startingSetBox;
  @FXML
  private HBox backpackBox;
  @FXML
  private TextField volumeInput;
  @FXML
  private TextField profitInput;
  @FXML
  private VBox backpackLegend;
  @FXML
  private VBox startingSetLegend;
  @FXML
  private Button nextStepButton;

  private List<Pair<Pane, Double>> startingSet = new ArrayList<Pair<Pane, Double>>();
  private int backpackVolume;
  private int backpackProfit = 0;

  private boolean allNumbers = true;

  private void invalidInput(String message) {
    allNumbers = false;

    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Valore non valido");
    alert.setHeaderText(null);
    alert.setContentText(message);

    alert.showAndWait();
  }

  private void setPV(Pane node) {

    VBox vbox = (VBox) node.getChildren().get(1);

    TextField profitTF = (TextField) vbox.getChildren().get(1);
    TextField volumeTF = (TextField) vbox.getChildren().get(2);

    int profit = Integer.parseInt(profitTF.getText());
    int volume = Integer.parseInt(volumeTF.getText());

    profitTF.setDisable(true);
    volumeTF.setDisable(true);

    double PV = (double)profit/volume;
    startingSet.add(new Pair<>(node, PV));

    ((Label) vbox.getChildren().get(3)).setText(String.format("%.3f", PV));
  }

  private void allNumbersCheck(Pane node) {
    VBox vbox = (VBox) node.getChildren().get(1);
    TextField profitTF = (TextField) vbox.getChildren().get(1);
    TextField volumeTF = (TextField) vbox.getChildren().get(2);
    int profit = Integer.parseInt(profitTF.getText());
    int volume = Integer.parseInt(volumeTF.getText());
  }

  public void handleSort(Event event) {
    allNumbers = true;

    //check that all input text are only numbers
    for (Node node : startingSetBox.getChildren()) {
      if (node.getId().equals("startingSetLegend")) {continue;}
      if (!allNumbers) {continue;}

      Pane panelXi = (Pane) node;
      try {
        allNumbersCheck(panelXi);
      } catch (Exception ex) {
        invalidInput(ex.getMessage());
      }
    }

    if (allNumbers == true) {
      for (Node node : startingSetBox.getChildren()) {
        if (node.getId().equals("startingSetLegend")) {continue;}
        Pane panelXi = (Pane) node;
        setPV(panelXi);
      }

      startingSet.sort(Comparator.comparing(pair -> -pair.getValue()));
      VBox legend = (VBox) startingSetBox.getChildren().get(0);
      startingSetBox.getChildren().clear();
      startingSetBox.getChildren().add(legend);
      for (Pair<Pane, Double> pair : startingSet) {
        startingSetBox.getChildren().add(pair.getKey());
      }

      //disable "ordina" button
      ((Node) event.getSource()).setDisable(true);
      //enable "prosegui" button
      nextStepButton.setDisable(false);
    }
  }

  public void handleNextStep(Event event) {
    try {
      backpackVolume = Integer.parseInt(volumeInput.getText());

      volumeInput.setDisable(true);
      backpackLegend.setVisible(true);

      Pane panel = (Pane) startingSetBox.getChildren().get(1);
      backpackBox.getChildren().add(panel);

      VBox vbox = (VBox) panel.getChildren().get(1);
      TextField profitTF = (TextField) vbox.getChildren().get(1);
      TextField volumeTF = (TextField) vbox.getChildren().get(2);
      int profit = Integer.parseInt(profitTF.getText());
      int volume = Integer.parseInt(volumeTF.getText());

      double x = Math.min((double) backpackVolume/volume, 1.0);
      if (volume > backpackVolume) {
        profitTF.setText(Integer.toString((int) (profit*x)));
        volumeTF.setText(Integer.toString((int) (volume*x)));
        updateBackpack(0, backpackProfit + (int) (profit*x));
      } else {
        updateBackpack(backpackVolume - volume, backpackProfit + profit);
      }

      ((Label) vbox.getChildren().get(3)).setText(String.format("%.3f", x));

      if (startingSetBox.getChildren().size() < 2) {
        startingSetLegend.setVisible(false);
        ((Node) event.getSource()).setDisable(true);
      }
    } catch (Exception ex) {
      invalidInput(ex.getMessage());
    }
  }

  private void updateBackpack(int newVolume, int newProfit) {
    backpackVolume = newVolume;
    backpackProfit = newProfit;
    volumeInput.setText(Integer.toString(newVolume));
    profitInput.setText(Integer.toString(newProfit));
  }

}