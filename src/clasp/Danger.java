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

public class Danger extends ClaspBasePiece {

static private ImageIcon imgDanger;
static protected Image dangerImage;

//Static Initialisation block - ran only once.
static {
   imgDanger = new ImageIcon(Danger.class.getResource("Danger.gif"));
   dangerImage = imgDanger.getImage();
}

  public Danger() {
    super();
    //Setup Player
    this.player=P0;
  }

 //Returns the Player One Image
  protected Image getP0Image(){
    return dangerImage;
  }
}