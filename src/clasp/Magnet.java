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

public class Magnet extends ClaspPiece {

static protected ImageIcon imgP0Magnet;
static protected ImageIcon imgP1Magnet;
static private ImageIcon imgP2Magnet;
static protected Image p0MagnetImage;
static protected Image p1MagnetImage;
static protected Image p2MagnetImage;

//Static Initialisation block - ran only once.
static {
   imgP0Magnet = new ImageIcon(Magnet.class.getResource("BlackMagnet.gif"));
   imgP1Magnet = new ImageIcon(Magnet.class.getResource("BlueMagnet.gif"));
   imgP2Magnet = new ImageIcon(Magnet.class.getResource("RedMagnet.gif"));
   p0MagnetImage = imgP0Magnet.getImage();
   p1MagnetImage = imgP1Magnet.getImage();
   p2MagnetImage = imgP2Magnet.getImage();
}

  public Magnet(int player) {
    super(player);
    }

 //Returns the Player One Image
protected Image getP1Image(){
  return p1MagnetImage;
  }

//Returns the Player Two Image
protected Image getP2Image(){
  return p2MagnetImage;
  }

//Returns the Player Two Image
protected Image getP0Image(){
  return p0MagnetImage;
  }

static Image getTinyImage(int player) {
  if (player==P1) return p1MagnetImage.getScaledInstance(20,20,Image.SCALE_DEFAULT);
  else return p2MagnetImage.getScaledInstance(20,20,Image.SCALE_DEFAULT);
}

}