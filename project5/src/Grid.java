public class Grid {
  private final int sizeX, sizeY;
  private boolean[][] contents;
  
  public Grid(int sizeX, int sizeY) {
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    contents = new boolean[sizeX][sizeY];
  }
    
  public int sizeX() { return sizeX; }
  public int sizeY() { return sizeY; }
  
  private void boundsCheck(int x, int y) {
    if (x < 0 || y < 0 || x >= sizeX || y >= sizeY) {
      throw new GridCoordsOutOfBoundsException();
    }
  }
  
  public void setToken(Coordinate c) { setToken(c.getX(), c.getY()); }
  public void setToken(int x, int y) { 
    boundsCheck(x,y);
    contents[x][y] = true;
  }
  public void resetToken(Coordinate c) { resetToken(c.getX(), c.getY()); } 
  public void resetToken(int x, int y) { 
    boundsCheck(x,y);
    contents[x][y] = false;
  }
    
  public boolean hasToken(Coordinate c) { return hasToken(c.getX(), c.getY()); }
  public boolean hasToken(int x, int y) {
    boundsCheck(x,y);
    return contents[x][y];
  }
}