package ir.pint.soltoon.soltoongame.shared.data.map;

import java.io.Serializable;
import java.util.*;

public class GameBoard implements Serializable{
   private Cell[][] cells;
   protected int height, width;
   protected Map<Long,GameObject> id2object;
   protected Long myID;
   protected HashMap<Long,Integer> playerID2money;
   protected Map<Long,Integer> playerID2moneyPerTurn;
   protected Map<Long,Long> id2owner;
   protected Map<Long,Integer> playerID2penalty;
   protected Map<Long,List<Long> > playerID2ids;
   protected Set<Long> playerIDs;

   protected GameBoard(int h, int w) {
      height = h;
      width = w;
      cells = new Cell[width][height];
      for (int i=0;i<width;i++)
         for (int j=0;j<height;j++)
            cells[i][j] = new Cell(i,j);
      id2object = new HashMap<>();
      playerID2money = new HashMap<>();
      playerID2penalty = new HashMap<>();
      id2owner = new HashMap<>();
      playerID2moneyPerTurn = new HashMap<>();
      playerID2ids = new HashMap<>();
      playerIDs = new TreeSet<>();
   }
   
   public int getHeight() {
      return height;
   }
   
   public int getWidth() {
      return width;
   }
   
   public Cell getCellByIndex(int x, int y) {
      if (x >= 0 && x < width && y >= 0 && y < height)
         return cells[x][y];
      return null;
   }

   public GameObject getObjectByID(Long id) {
      return id2object.get(id);
   }

   public Long getownerByID(Long id) {
      return id2owner.get(id);
   }

   public Long getMyID() {
      return myID;
   }

   public int getMoneyByID(Long id) {
      Integer res = playerID2money.get(id);
      return (res==null)?0:res;
   }
   public int getPenaltyByID(Long id) {
      Integer res = playerID2penalty.get(id);
      return (res==null)?0:res;
   }

   public int getMoneyPerTurn(Long id) {
      Integer res = playerID2moneyPerTurn.get(id);
      return ((res==null)?0:res);
   }

   public Cell getCellByDirection(Cell cell, Direction dir) {
      int x = cell.getX()+dir.dx(), y=cell.getY()+dir.dy();
      if (x<0 || x>width || y<0 || y>width) return null;
      else return getCellByIndex(x,y);
   }

   public List<Long> idsByPlayerID(Long id) {
      return playerID2ids.get(id);
   }

   public Set<Long> getPlayerIDs() {
      return playerIDs;
   }
}
