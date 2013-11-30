package clasp;
import java.awt.*;  //Graphics
import javax.swing.*; //Jcomponent

/**
 * Title:        Egg Piece
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class Teleport extends ClaspBasePiece {

static private ImageIcon imgTeleportPiece;
static private ImageIcon imgTeleportOn1Piece;
static private ImageIcon imgTeleportOn2Piece;
static private ImageIcon imgTeleportOn3Piece;
static protected Image TeleportImage;
static protected Image TeleportOn1Image;
static protected Image TeleportOn2Image;
static protected Image TeleportOn3Image;

private boolean OnStatus;

//Static Initialisation block - ran only once.
static {
   imgTeleportPiece = new ImageIcon(Teleport.class.getResource("Teleport.gif"));
   imgTeleportOn1Piece = new ImageIcon(Teleport.class.getResource("Teleport1.gif"));
   imgTeleportOn2Piece = new ImageIcon(Teleport.class.getResource("Teleport2.gif"));
   imgTeleportOn3Piece = new ImageIcon(Teleport.class.getResource("Teleport3.gif"));
   TeleportImage = imgTeleportPiece.getImage();
   TeleportOn1Image = imgTeleportOn1Piece.getImage();
   TeleportOn2Image = imgTeleportOn2Piece.getImage();
   TeleportOn3Image = imgTeleportOn3Piece.getImage();
}

  public Teleport() {
    super();
    //Setup Player
    this.player=P0;
    OnStatus=false;       //default to not Currently Firing
  }


//Returns the Player One Image
protected Image getP0Image(){
  if (isOn()) {
    if (getAnimationStep()==3) return TeleportOn3Image;
    if (getAnimationStep()==2) return TeleportOn2Image;
    else return TeleportOn1Image;
    }
  else return TeleportImage;
  }

protected void setOn(boolean value){
  OnStatus=value;
  }

protected boolean isOn(){
  return OnStatus;
  }


}
