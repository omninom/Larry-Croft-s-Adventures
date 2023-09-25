package nz.ac.wgtn.swen225.lc.domain;
import java.awt.Point;
import java.util.*;

public class Chap {
  private Point position;
  private ArrayList<Tile> keys;
  private int treasure;

  public Chap(int initialRow, int initialCol){
    this.position = new Point(initialRow, initialCol);
    this.keys = new ArrayList<>();
  }

  public Point getPosition(){
    return position;
  }

  public void setPosition(int row, int col){
    position.setLocation(row, col);
  }

  public ArrayList<Tile> getKeys(){
    return keys;
  }

  public void addKey(Tile key){
    keys.add(key);
  }

  public void removeKey(Tile key){
    keys.remove(key);
  }
}
