package algat.controller;

import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import javafx.scene.control.Pagination;

public class QuestionsController {

  private boolean isCorrect(RadioButton sel, VBox cont) {
    return (sel.getText().equals(correctAnswer(cont)));
  }

  private String correctAnswer(VBox cont) {
    int contSize = cont.getChildren().size();
    Label correctAnswerLabel = (Label) cont.getChildren().get(contSize - 1);
    return correctAnswerLabel.getText();
  }

  private int correctIndex(VBox cont) {
    int i = 0;
    String correctAnswer = correctAnswer(cont);
    String answer = ((RadioButton) cont.getChildren().get(i)).getText();
    while (!correctAnswer.equals(answer)) {
      i += 1;
      try { answer = ((RadioButton) cont.getChildren().get(i)).getText(); }
      catch(Exception e) {}
    }
    return i;
  }

  public void answerSelected(Event event) {
    RadioButton selected = (RadioButton) event.getSource();
    VBox parent = (VBox) selected.getParent();

    if (isCorrect(selected, parent)) {
      selected.setTextFill(Color.GREEN);
    } else {
      selected.setTextFill(Color.RED);

      int ci = correctIndex(parent);
      RadioButton correct = (RadioButton) parent.getChildren().get(ci);
      correct.setSelected(true);
      correct.setTextFill(Color.GREEN);
    }
    parent.setDisable(true);
    toggleNextButton((VBox) parent.getParent());
  }

  public void toggleNextButton(VBox cont) {
    Pagination pag = (Pagination) cont.getParent().getParent();
    Button nextBtn = (Button) cont.getChildren().get(cont.getChildren().size() - 1);
    if (pag.getCurrentPageIndex() < pag.getPageCount() - 1) {
      nextBtn.setDisable(false);
    } else {
      nextBtn.setText("Test completato");
    }
  }

  public void toggleNextQuestion(Event event) {
    Node src = (Node) event.getSource();
    Pagination pag = (Pagination) src.getParent().getParent().getParent();
    int nextPage = pag.getCurrentPageIndex() + 1;
    pag.setCurrentPageIndex(nextPage);
  }

}