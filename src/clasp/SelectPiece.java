package clasp;
import java.awt.*;  //Graphics
import javax.swing.*; //Jcomponent

/**
 * Title:        Select Piece
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class SelectPiece extends ClaspPiece {

static protected Image SelectP1Image;
static protected Image SelectP2Image;
static private ImageIcon imgP1SelectPiece;
static private ImageIcon imgP2SelectPiece;

static {
   imgP1SelectPiece = new ImageIcon(SelectPiece.class.getResource("BlueSelectPiece.gif"));
   imgP2SelectPiece = new ImageIcon(SelectPiece.class.getResource("RedSelectPiece.gif"));
   SelectP1Image = imgP1SelectPiece.getImage();
   SelectP2Image = imgP2SelectPiece.getImage();
}

  public SelectPiece(int player) {
    super(player);
    }

//Returns the Player One Image
protected Image getP1Image(){
  return SelectP1Image;
  }

//Returns the Player Two Image
protected Image getP2Image(){
  return SelectP2Image;
  }

}