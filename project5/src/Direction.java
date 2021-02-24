public enum Direction {
  UP(0,1), DOWN(0,-1), LEFT(-1,0), RIGHT(1,0), NONE(0,0);
  
  private final Coordinate delta;
  private Direction(int dx, int dy) { this.delta = new Coordinate(dx, dy); }
  public Coordinate delta() { return delta; }
}