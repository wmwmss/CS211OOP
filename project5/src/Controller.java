import java.util.*;

public class Controller implements Signalable<Drone> {
  private int count = 0;
  private ArrayList<Coordinate> coords = new ArrayList<>();
  public int count() { return count; }
  public List<Coordinate> getCoords() { return Collections.unmodifiableList(coords); }
  
  @Override public void signal(Drone d) {
    Coordinate c = d.getPosition();
    if (!coords.contains(c)) {
        coords.add(d.getPosition());
        count++;
        System.err.println("found token at " + c);
    }
  }
}