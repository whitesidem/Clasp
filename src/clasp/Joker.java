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

public class Joker extends ClaspPiece {

static private ImageIcon imgP1Joker;
static private ImageIcon imgP2Joker;
static protected Image p1JokerImage;
static protected Image p2JokerImage;
//Static Initialisation block - ran only once.
static {
   imgP1Joker = new ImageIcon(Joker.class.getResource("BlueJokerPiece.gif"));
   imgP2Joker = new ImageIcon(Joker.class.getResource("RedJokerPiece.gif"));
   p1JokerImage = imgP1Joker.getImage();
   p2JokerImage = imgP2Joker.getImage();
}

  public Joker(int player) {
    super(player);
    setIndestructable(true);      //Make it indestructable
  }

//Returns the Player One Image
protected Image getP1Image(){
  return p1JokerImage;
  }

//Returns the Player Two Image
protected Image getP2Image(){
  return p2JokerImage;
  }



}

