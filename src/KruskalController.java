package algat.controller;

import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

import algat.util.JFXGraphLink;
import algat.util.MFSet;
import javafx.util.Pair;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class KruskalController {

  @FXML
  private Pane nodesView;
  @FXML
  private Pane linksView;
  @FXML
  private Pane weightsView;
  @FXML
  private ListView sideListView;

  private void addTextWeight(Pair<Double, Double> pos, int value) {
    Text text = new Text(Integer.toString(value));
    text.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
    text.setX(pos.getKey() - 5.0); //X
    text.setY(pos.getValue() - 5.0); //Y
    weightsView.getChildren().add(text);
  }

  private Pair<Pane, Pane> getLinkNodes(Line link) {
    String id = link.getId();
    Pane n1 = (Pane) nodesView.lookup("#node" + id.charAt(4));
    Pane n2 = (Pane) nodesView.lookup("#node" + id.charAt(5));
    return new Pair<Pane, Pane>(n1, n2);
  }

  private int linkNum = 10;
  private int nodeNum = 7;
  private List<JFXGraphLink> links = new ArrayList<JFXGraphLink>();
  private ObservableList<String> sideList = FXCollections.observableArrayList();

  public void initialize() {
    Random random = new Random();

    for (Node linkNode : linksView.getChildren()) {
      Line line = (Line) linkNode;
      JFXGraphLink link = new JFXGraphLink(
        line,
        getLinkNodes(line).getKey(),
        getLinkNodes(line).getValue(),
        random.nextInt(30)+1);

      link.line.setOpacity(0.2);

      links.add(link);
      addTextWeight(link.lineCenter(), link.weigth);
    }

    links.sort(Comparator.comparing(link -> link.weigth));

    for (JFXGraphLink link : links) {sideList.add(link.toString());}
    sideList.set(0, sideList.get(0) + " ◀️");
    sideListView.setItems(sideList);

  }

  private int stepCounter = 0;
  private int spanningTreeSize = 0;
  private Set<JFXGraphLink> minSpanningTree = new HashSet<JFXGraphLink>();
  private MFSet supportSet = new MFSet(nodeNum);

  public void kruskalHandler(Event event) {
    if ((spanningTreeSize < nodeNum - 1) & (stepCounter < linkNum)) {
      JFXGraphLink link = (JFXGraphLink) links.get(stepCounter);
      kruskalNextStep(link);
      updateGraphView();
    }
    if (spanningTreeSize == nodeNum - 1) {
      ((Node) event.getSource()).setDisable(true);
      // return the sum of all the arcs weights in the spanning tree
      String stDim = minSpanningTree.stream()
        .map(ob ->(ob.weigth))
        .reduce(0, Integer::sum)
        .toString();
      ((Button) event.getSource()).setText("Albero completato - dimensione " + stDim);
    }
  }

  private void kruskalNextStep(JFXGraphLink link) {
    int u = link.uToInt();
    int v = link.vToInt();
    if (supportSet.find(u) != supportSet.find(v)) {
      supportSet.merge(u,v);
      minSpanningTree.add(link);
      spanningTreeSize++;
    }
    stepCounter++;
  }

  private Color [] supportSetColors = {
    Color.BROWN,
    Color.BLUE,
    Color.RED,
    Color.GOLD,
    Color.AQUA,
    Color.LIME,
    Color.LIGHTCORAL,
  };

  private void updateGraphView() {
    for (JFXGraphLink link : minSpanningTree) {
      link.line.setOpacity(1.0);
      int uRep = supportSet.find(link.uToInt());
      int vRep = supportSet.find(link.vToInt());
      colorNode((Pane) link.u, supportSetColors[uRep]);
      colorNode((Pane) link.v, supportSetColors[vRep]);
    }

    JFXGraphLink currentLink = (JFXGraphLink) links.get(stepCounter - 1);
    if (minSpanningTree.contains(currentLink)) {
      forwardListItem('✓');
    } else {
      forwardListItem('ｘ');
      currentLink.line.getStrokeDashArray().addAll(2d, 21d);
    }

  }

  private void forwardListItem(char updateSymbol) {
    String previousItem = (String) sideList.get(stepCounter - 1);
    previousItem = previousItem.substring(0, previousItem.length() - 2); //remove arrow current item
    sideList.set(stepCounter - 1, previousItem + updateSymbol);
    sideList.set(stepCounter, sideList.get(stepCounter) + " ◀️");
  }

  private void colorNode(Pane node, Color c) {
    Circle border = (Circle) node.getChildren().get(0);
    border.setStroke(c);
  }


}