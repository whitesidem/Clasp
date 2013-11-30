package clasp;

import java.awt.*;  //Graphics
import javax.swing.*; //Jcomponent


/**
 * Title:        Clasp Version 2
 * Description:  Assassin Game Piece
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */



public class AssassinPiece extends ClaspPiece {

static private ImageIcon imgP1CrossPiece;
static private ImageIcon imgP2CrossPiece;
static private ImageIcon imgP1PlusPiece;
static private ImageIcon imgP2PlusPiece;
static private ImageIcon imgP1CrossFirePiece;
static private ImageIcon imgP2CrossFirePiece;
static private ImageIcon imgP1PlusFirePiece;
static private ImageIcon imgP2PlusFirePiece;
static protected Image p1CrossImage;
static protected Image p2CrossImage;
static protected Image p1PlusImage;
static protected Image p2PlusImage;
static protected Image p1CrossFireImage;
static protected Image p2CrossFireImage;
static protected Image p1PlusFireImage;
static protected Image p2PlusFireImage;

private boolean crossStatus;
private boolean fireStatus;


//Static Initialisation block - ran only once.
static {
   imgP1CrossPiece = new ImageIcon(AssassinPiece.class.getResource("BlueAssassinCross.gif"));
   imgP2CrossPiece = new ImageIcon(AssassinPiece.class.getResource("RedAssassinCross.gif"));
   imgP1PlusPiece = new ImageIcon(AssassinPiece.class.getResource("BlueAssassinPlus.gif"));
   imgP2PlusPiece = new ImageIcon(AssassinPiece.class.getResource("RedAssassinPlus.gif"));
   imgP1CrossFirePiece = new ImageIcon(AssassinPiece.class.getResource("BlueAssassinCrossFire.gif"));
   imgP2CrossFirePiece = new ImageIcon(AssassinPiece.class.getResource("RedAssassinCrossFire.gif"));
   imgP1PlusFirePiece = new ImageIcon(AssassinPiece.class.getResource("BlueAssassinPlusFire.gif"));
   imgP2PlusFirePiece = new ImageIcon(AssassinPiece.class.getResource("RedAssassinPlusFire.gif"));

   p1CrossImage = imgP1CrossPiece.getImage();
   p2CrossImage = imgP2CrossPiece.getImage();
   p1PlusImage = imgP1PlusPiece.getImage();
   p2PlusImage = imgP2PlusPiece.getImage();

   p1CrossFireImage = imgP1CrossFirePiece.getImage();
   p2CrossFireImage = imgP2CrossFirePiece.getImage();
   p1PlusFireImage = imgP1PlusFirePiece.getImage();
   p2PlusFireImage = imgP2PlusFirePiece.getImage();
}

//Returns the Player One Image
protected Image getP1Image(){
  if (isCross()) {
    if (isFire()) return p1CrossFireImage;
    else return p1CrossImage;
    }
  if (isFire()) return p1PlusFireImage;
  else return p1PlusImage;
  }

//Returns the Player Two Image
protected Image getP2Image(){
  if (isCross()) {
    if (isFire()) return p2CrossFireImage;
    else return p2CrossImage;
    }
  if (isFire()) return p2PlusFireImage;
  else return p2PlusImage;
  }

//Constructor
public AssassinPiece(int player) {
    super(player);
    crossStatus=false;      //default to displaying plus assassin
    fireStatus=false;       //default to not Currently Firing
  }

protected void setCross(boolean value){
  crossStatus=value;
  }
protected boolean isCross(){
  return crossStatus;
  }
protected void setFire(boolean value){
  fireStatus=value;
  }
protected boolean isFire(){
  return fireStatus;
  }

static Image getTinyImage(int player) {
  if (player==P1) return p1CrossImage.getScaledInstance(20,20,Image.SCALE_DEFAULT);
  else return p2CrossImage.getScaledInstance(20,20,Image.SCALE_DEFAULT);
}

}