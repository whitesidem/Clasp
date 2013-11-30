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

public class Boost extends ClaspBasePiece {

static private ImageIcon imgBoostPiece;
static private ImageIcon imgBoost2Piece;
static private ImageIcon imgBoost3Piece;
static private ImageIcon imgBoost4Piece;
static protected Image BoostImage;
static protected Image Boost2Image;
static protected Image Boost3Image;
static protected Image Boost4Image;

private boolean BoostStatus;

//Static Initialisation block - ran only once.
static {
   imgBoostPiece = new ImageIcon(Boost.class.getResource("BoostPiece.gif"));
   imgBoost2Piece = new ImageIcon(Boost.class.getResource("BoostPiece2.gif"));
   imgBoost3Piece = new ImageIcon(Boost.class.getResource("BoostPiece3.gif"));
   imgBoost4Piece = new ImageIcon(Boost.class.getResource("BoostPiece4.gif"));

   BoostImage = imgBoostPiece.getImage();
   Boost2Image = imgBoost2Piece.getImage();
   Boost3Image = imgBoost3Piece.getImage();
   Boost4Image = imgBoost4Piece.getImage();
}

  public Boost() {
    super();
    //Setup Player
    this.player=P0;
    BoostStatus=false;       //default to not Currently Firing
  }


//Returns the Player One Image
protected Image getP0Image(){
  if (isBoosting()) {
    if (getAnimationStep()==3) return Boost4Image;
    if (getAnimationStep()==2) return Boost3Image;
    else return Boost2Image;
    }
  else return BoostImage;
  }

protected void setBoost(boolean value){
  BoostStatus=value;
  }

protected boolean isBoosting(){
  return BoostStatus;
  }


}