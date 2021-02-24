public final class Coordinate {
  private final int x, y;
  public int getX() { return x; }
  public int getY() { return y; }
  
  public Coordinate(int x, int y) { this.x = x;  this.y = y; }
  
  public Coordinate plus(int dx, int dy) { return new Coordinate(x+dx, y+dy); }
  public Coordinate plus(Coordinate delta) { return new Coordinate(x+delta.x, y+delta.y); }
  public Coordinate plus(Direction d) { return this.plus(d.delta()); }
  @Override public String toString() { return String.format("(%d, %d)", x, y); }
  
  @Override public int hashCode() { return x+y; }
  @Override public boolean equals(Object o) {
    if (!(o instanceof Coordinate)) return false;
    Coordinate other = (Coordinate)o;
    return this.x == other.x && this.y == other.y;
  }
}