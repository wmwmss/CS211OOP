import java.util.*;

public class Drone {
  private Coordinate position;
  private Grid grid;
  private ArrayList<Signalable<Drone>> listeners;
  
  public Drone(Coordinate start, Grid grid) {
    this.position = start; 
    this.grid = grid;
    this.listeners = new ArrayList<>();
  }
  public Drone(int x, int y, Grid grid) { this(new Coordinate(x,y), grid); } 
   
  public void addTokenListener(Signalable<Drone> listener) { listeners.add(listener); }
  public void removeTokenListener(Signalable<Drone> listener) { listeners.remove(listener); }
  private void signalAll() {
    for (Signalable<Drone> listener : listeners) { listener.signal(this); }
  }
  
  public void search() {
    move(Direction.NONE);  // make sure we've scanned the starting point
    while (position.getX() > 0) move(Direction.LEFT);
    while (position.getY() > 0) move(Direction.DOWN);
    //search(Direction.RIGHT, 0, 0, grid.sizeX()-1, grid.sizeY()-1);    
    searchLine();
  }
  
  private void searchLine() {
     if (position.getX() == 0) {
	   while (position.getX() < grid.sizeX()-1) move(Direction.RIGHT);
	 } else {
	   while (position.getX() > 0) move(Direction.LEFT);
	 }
	 if (position.getY() == grid.sizeY()-1) return;
	 move(Direction.UP);
	 searchLine();
  }
  
  private void search(Direction d, int lowX, int lowY, int highX, int highY) {
    System.out.printf("(%d,%d)--(%d,%d)\n(%d,%d)--(%d,%d) %s\n", lowX, highY, highX, highY, lowX, lowY, highX, lowY, d);
    if (lowX > highX || lowY > highY) return;  // we're done
    switch (d) {
      case UP: 
        while (position.getY() < highY) move(d);
        search(Direction.LEFT, lowX, lowY, highX-1, highY);
        break;
      case LEFT: 
        while (position.getX() > lowX) move(d);
        search(Direction.DOWN, lowX, lowY, highX, highY-1);
        break;
      case DOWN: 
        while (position.getY() > lowY) move(d);
        search(Direction.RIGHT, lowX+1, lowY, highX, highY);
        break;
      case RIGHT: 
        while (position.getX() < highX) move(d);
        search(Direction.UP, lowX, lowY+1, highX, highY);
        break;
    }
  }
  
  public Coordinate getPosition() { return position; }
  public void move(Direction d) {
    Coordinate temp = position.plus(d);
    grid.hasToken(temp); // verify (bounds-check) if it's a legal move
    position = temp;
    System.out.println("moved to " + position);
    if (grid.hasToken(position)) { signalAll(); }
  }
  
  public static void main(String[] args) {
    Grid g = new Grid(5,10);
    g.setToken(new Coordinate(3,8));
    g.setToken(new Coordinate(0,4));
    g.setToken(new Coordinate(1,6));
    Controller c = new Controller();
    Drone d = new Drone(new Coordinate(3,3), g);
    d.addTokenListener(c);
    d.search();
    System.err.println(c.getCoords() + " x" + c.count());
  }
}
