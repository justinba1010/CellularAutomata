import java.util.*;

public class Grid {
  private boolean[][] grid;
  private int gen;
  private static final char DEAD = ' ';
  private static final char ALIVE = '*';
  //Default to Conways Game of Life
  private int[] numNeighbors = {3};
  private int[][] neighborhood;
  private boolean bNeighborhood = false;
  private boolean bNumNeighbors = true;
  private boolean both = false;//if false can be one or the other(two rules)
  public Grid(int xsize, int ysize) {
    grid = new boolean[xsize][ysize];
  }
  public Grid(int size) {
    init(size);
  }//ConwayGrid Constructor
  public Grid() {
    init(10);
  }//ConwayGrid Default Constructor
  private void init(int size) {
    gen = 0;
    grid = new boolean[size][size];
    blanket();
  }//init

  public void modify(int x, int y, boolean a) {
    if(legal(x,y)) {
      grid[x][y] = a;
    }
  }//modify

  public boolean[][] getGrid() {
    return grid;
  }

  private void blanket() {//Felt it could be useful
    for(boolean[] row : grid) {
      for(boolean spot : row) {
        spot = false;
      }
    }
  }

  public void nextGen() {
    //Lists so we can go through and not overwrite our grid while we view it
    ArrayList<Integer[]> newalive = new ArrayList<Integer[]>();
    ArrayList<Integer[]> newdead = new ArrayList<Integer[]>();
    for(int x = 0; x < grid.length; x++) {
      for(int y = 0; y < grid[x].length; y++) {
        if(isAlive(x,y) != grid[x][y]) {
          Integer[] spot = {x,y};
          if(grid[x][y]) {
            newdead.add(spot);
          } else {
            newalive.add(spot);
          }
        }
      }
    }//for x

    //Change grid
    for(Integer[] alive : newalive) {
      modify(alive[0],alive[1], true);
    }

    for(Integer[] dead : newdead) {
      modify(dead[0],dead[1],false);
    }

  }//nextGen

  private boolean isAlive(int x, int y) {
    boolean state = false;
    //Check for num neighbors
    if(bNumNeighbors) {
      int neighborsAlive = 0;
      for(int xi = -1; xi <= 1; xi++) {
        for(int yi = -1; yi <= 1; yi++) {
          if(legal(x+xi,y+yi)){
            neighborsAlive += grid[x+xi][y+yi] ? 1 : 0;
          }
        }
      }//for xi
      for(int neighbor : numNeighbors) {//Check neighbors
        if(neighbor == neighborsAlive) state = true;
      }
    }//if bnum

    //Check for neighborhood
    //Need ideas for this
    if(bNeighborhood) {
      if(both && !state) return false; //Check the both state
      state = false;//Reset state so we can do next check
      for(int[] neighbor : neighborhood) {
        if(neighbor.length != 2) continue;
        if(legal(x+neighbor[0], y+neighbor[1])) {
          if(grid[x+neighbor[0]][y+neighbor[1]]) {
            state = true;
          } else return false;
        }
      }
    }
    return state;
  }

  private boolean legal(int x, int y) {
    return x >= 0 && y >= 0 && x < grid.length && y < grid[x].length;
  }

  public String toString() {
    return toString(true);
  }

  public String toString(boolean generation) {
    String s = "";
    for(boolean[] row : grid) {
      for(boolean spot : row) {
        s += (spot) ? ALIVE : DEAD;
      }
      s += "\n";
    }
    if(generation) s+= "Generation #"+gen;
    return s;
  }

  public Integer[] getDimensions() {
    Integer[] s = {grid.length, grid[0].length};
    return s;
  }
}//ConwayGrid
