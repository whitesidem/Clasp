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

public class Trophy2 extends ClaspPiece {

static private ImageIcon imgTrophy;
static protected Image trophyImage;

//Static Initialisation block - ran only once.
static {
   imgTrophy = new ImageIcon(Trophy2.class.getResource("Shield2.gif"));
   trophyImage = imgTrophy.getImage();
}

  public Trophy2(int player) {
    super(player);
  }

//Returns the Player One Image
protected Image getP1Image(){
  return trophyImage;
  }

//Returns the Player Two Image
protected Image getP2Image(){
  return trophyImage;
  }


}