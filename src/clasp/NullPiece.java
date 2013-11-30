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

public class NullPiece extends ClaspBasePiece {
static private ImageIcon imgNull;
static protected Image nullImage;

//Static Initialisation block - ran only once.
static {
  imgNull = new ImageIcon(NullPiece.class.getResource("NullPiece.gif"));
  nullImage = imgNull.getImage();
  }

  public NullPiece() {
    super();
    //Setup Player
    this.player=P0;
  }

 //Returns the Player One Image
  protected Image getP0Image(){
    return nullImage;
  }

}
