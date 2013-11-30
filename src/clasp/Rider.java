package clasp;
import java.awt.*;  //Graphics
import javax.swing.*; //Jcomponent

/**
 * Title:        Clasp Version 2
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class Rider extends ClaspBasePiece {

static private ImageIcon imgRider1;
static private ImageIcon imgRider2;
static private ImageIcon imgRider3;
static private ImageIcon imgRider4;
static private ImageIcon imgRider5;
static private ImageIcon imgRider6;
static protected Image rider1Image;
static protected Image rider2Image;
static protected Image rider3Image;
static protected Image rider4Image;
static protected Image rider5Image;
static protected Image rider6Image;


//Static Initialisation block - ran only once.
static {
  imgRider1 = new ImageIcon(Cash.class.getResource("Rider1.gif"));
  imgRider2 = new ImageIcon(Cash.class.getResource("Rider2.gif"));
  imgRider3 = new ImageIcon(Cash.class.getResource("Rider3.gif"));
  imgRider4 = new ImageIcon(Cash.class.getResource("Rider4.gif"));
  imgRider5 = new ImageIcon(Cash.class.getResource("Rider5.gif"));
  imgRider6 = new ImageIcon(Cash.class.getResource("Rider6.gif"));

  rider1Image = imgRider1.getImage();
  rider2Image = imgRider2.getImage();
  rider3Image = imgRider3.getImage();
  rider4Image = imgRider4.getImage();
  rider5Image = imgRider5.getImage();
  rider6Image = imgRider6.getImage();
  }

  private int riderNum;

  public Rider(int num) {
    super();
    //Setup Player
    this.player=P0;
    riderNum=num;
  }

  public int getRiderNum() {
    return riderNum;
  }
  public void setRiderNum(int riderNum) {
    this.riderNum = riderNum;
  }

 //Returns the Player One Image
  protected Image getP0Image(){
    switch (riderNum) {
       case 1:
          return rider1Image;
       case 2:
          return rider2Image;
       case 3:
          return rider3Image;
       case 4:
          return rider4Image;
       case 5:
          return rider5Image;
       default:
          return rider6Image;
      }
  }


}