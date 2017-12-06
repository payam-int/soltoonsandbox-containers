package ir.pint.soltoon.soltoongame.shared.data.map;

import ir.pint.soltoon.utils.shared.facades.json.Secure;

import java.io.Serializable;
import java.util.*;

@Secure
public class GameBoard implements Serializable{
   private Cell[][] cells;
   protected int height, width;
   protected HashMap<Long,GameObject> id2object;
   protected Long myID;
   protected HashMap<Long,Integer> playerMoney;
   protected HashMap<Long,Integer> playerMoneyPerTurn;
   protected HashMap<Long,Integer> playerPenalty;
   protected HashMap<Long,List<Long> > playerOwnedIds;
   protected HashMap<Long,Long> idOwner;
   protected TreeSet<Long> playerIDs;
   private Integer board = 0;

   protected GameBoard(int h, int w) {
      height = h;
      width = w;
      cells = new Cell[width][height];
      for (int i=0;i<width;i++)
         for (int j=0;j<height;j++)
            cells[i][j] = new Cell(i,j);
      id2object = new HashMap<>();
      playerMoney = new HashMap<>();
      playerPenalty = new HashMap<>();
      idOwner = new HashMap<>();
      playerMoneyPerTurn = new HashMap<>();
      playerOwnedIds = new HashMap<>();
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
      return idOwner.get(id);
   }

   public Long getMyID() {
      return myID;
   }

   public int getMoneyByID(Long id) {
      Integer res = playerMoney.get(id);
      return (res==null)?0:res;
   }
   public int getPenaltyByID(Long id) {
      Integer res = playerPenalty.get(id);
      return (res==null)?0:res;
   }

   public int getMoneyPerTurn(Long id) {
      Integer res = playerMoneyPerTurn.get(id);
      return ((res==null)?0:res);
   }

   public Cell getCellByDirection(Cell cell, Direction dir) {
      int x = cell.getX()+dir.dx(), y=cell.getY()+dir.dy();
      if (x<0 || x>width || y<0 || y>width) return null;
      else return getCellByIndex(x,y);
   }

   public List<Long> idsByPlayerID(Long id) {
      return playerOwnedIds.get(id);
   }

   public Set<Long> getPlayerIDs() {
      return playerIDs;
   }

   public void inc() {
      this.board++;
   }
}
