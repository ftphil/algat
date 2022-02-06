package algat.util;

import javafx.util.Pair;
import javafx.scene.Node;
import javafx.scene.shape.Line;

public class JFXGraphLink {
  public Line line;
  public Node u;
  public Node v;
  public int weigth;

  public JFXGraphLink(Line l, Node n1, Node n2, int w) {
    line = l;
    u = n1;
    v = n2;
    weigth = w;
  }

  public Pair<Double, Double> lineCenter() {
    Double centerX = (line.getStartX() + line.getEndX())/2;
    Double centerY = (line.getStartY() + line.getEndY())/2;
    return new Pair<>(centerX, centerY);
  }

  public int uToInt() {
    char character = u.getId().charAt(4);
    return ((int) character) - 65; //65 A in ascii
  }

  public int vToInt() {
    char character = v.getId().charAt(4);
    return ((int) character) - 65; //65 A in ascii
  }

  public String toString() {
    return u.getId().charAt(4) + " - " + v.getId().charAt(4) + " " + weigth;
  }

}