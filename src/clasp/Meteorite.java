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

public class Meteorite extends ClaspPiece {

static private ImageIcon imgP1Meteor;
static private ImageIcon imgP2Meteor;
static protected Image p1MeteorImage;
static protected Image p2MeteorImage;

//Static Initialisation block - ran only once.
static {
   imgP1Meteor = new ImageIcon(Meteorite.class.getResource("BlueMeteorite.gif"));
   imgP2Meteor = new ImageIcon(Meteorite.class.getResource("RedMeteorite.gif"));
   p1MeteorImage = imgP1Meteor.getImage();
   p2MeteorImage = imgP2Meteor.getImage();
}

  public Meteorite(int player) {
    super(player);
  }

//Returns the Player One Image
protected Image getP1Image(){
  return p1MeteorImage;
  }

//Returns the Player Two Image
protected Image getP2Image(){
  return p2MeteorImage;
  }


}