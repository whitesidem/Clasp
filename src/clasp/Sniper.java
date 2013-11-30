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


public class Sniper extends ClaspPiece {
static private ImageIcon imgP1SniperPiece;
static private ImageIcon imgP2SniperPiece;
static private ImageIcon imgP1RSniper2Piece;
static private ImageIcon imgP2RSniper2Piece;
static private ImageIcon imgP1RSniper3Piece;
static private ImageIcon imgP2RSniper3Piece;
static private ImageIcon imgP1RSniperFirePiece;
static private ImageIcon imgP2RSniperFirePiece;
static private ImageIcon imgP1LSniper2Piece;
static private ImageIcon imgP2LSniper2Piece;
static private ImageIcon imgP1LSniper3Piece;
static private ImageIcon imgP2LSniper3Piece;
static private ImageIcon imgP1LSniperFirePiece;
static private ImageIcon imgP2LSniperFirePiece;

static protected Image p1SniperImage;
static protected Image p2SniperImage;
static protected Image p1LSniper2Image;
static protected Image p2LSniper2Image;
static protected Image p1LSniper3Image;
static protected Image p2LSniper3Image;
static protected Image p1LSniperFireImage;
static protected Image p2LSniperFireImage;
static protected Image p1RSniper2Image;
static protected Image p2RSniper2Image;
static protected Image p1RSniper3Image;
static protected Image p2RSniper3Image;
static protected Image p1RSniperFireImage;
static protected Image p2RSniperFireImage;

private boolean fireStatus;
private boolean landed;
private int direction;
private int odds;       //Odds for whether sniper shoots


//Static Initialisation block - ran only once.
static {
   imgP1SniperPiece = new ImageIcon(Sniper.class.getResource("BlueSniper.gif"));
   imgP2SniperPiece = new ImageIcon(Sniper.class.getResource("RedSniper.gif"));
   imgP1RSniper2Piece = new ImageIcon(Sniper.class.getResource("BlueRSniper2.gif"));
   imgP2RSniper2Piece = new ImageIcon(Sniper.class.getResource("RedRSniper2.gif"));
   imgP1RSniper3Piece = new ImageIcon(Sniper.class.getResource("BlueRSniper3.gif"));
   imgP2RSniper3Piece = new ImageIcon(Sniper.class.getResource("RedRSniper3.gif"));
   imgP1RSniperFirePiece = new ImageIcon(Sniper.class.getResource("BlueRSniperFire.gif"));
   imgP2RSniperFirePiece = new ImageIcon(Sniper.class.getResource("RedRSniperFire.gif"));
   imgP1LSniper2Piece = new ImageIcon(Sniper.class.getResource("BlueLSniper2.gif"));
   imgP2LSniper2Piece = new ImageIcon(Sniper.class.getResource("RedLSniper2.gif"));
   imgP1LSniper3Piece = new ImageIcon(Sniper.class.getResource("BlueLSniper3.gif"));
   imgP2LSniper3Piece = new ImageIcon(Sniper.class.getResource("RedLSniper3.gif"));
   imgP1LSniperFirePiece = new ImageIcon(Sniper.class.getResource("BlueLSniperFire.gif"));
   imgP2LSniperFirePiece = new ImageIcon(Sniper.class.getResource("RedLSniperFire.gif"));

   p1SniperImage = imgP1SniperPiece.getImage();
   p2SniperImage = imgP2SniperPiece.getImage();
   p1RSniper2Image = imgP1RSniper2Piece.getImage();
   p2RSniper2Image = imgP2RSniper2Piece.getImage();
   p1RSniper3Image = imgP1RSniper3Piece.getImage();
   p2RSniper3Image = imgP2RSniper3Piece.getImage();
   p1RSniperFireImage = imgP1RSniperFirePiece.getImage();
   p2RSniperFireImage = imgP2RSniperFirePiece.getImage();
   p1LSniper2Image = imgP1LSniper2Piece.getImage();
   p2LSniper2Image = imgP2LSniper2Piece.getImage();
   p1LSniper3Image = imgP1LSniper3Piece.getImage();
   p2LSniper3Image = imgP2LSniper3Piece.getImage();
   p1LSniperFireImage = imgP1LSniperFirePiece.getImage();
   p2LSniperFireImage = imgP2LSniperFirePiece.getImage();
}

//Returns the Player One Image
protected Image getP1Image(){
  if (direction==CLASP_CENTER) {
      if (hasLanded()) return p1Image;
      return p1SniperImage;
      }
  if (isFire()==false) {
    switch (getAnimationStep()) {
      case 1:
        if (direction==CLASP_LEFT) return p1SniperImage;
        return p1SniperImage;
      case 2:
        if (direction==CLASP_LEFT) return p1LSniper2Image;
        return p1RSniper2Image;
      case 3:
        if (direction==CLASP_LEFT) return p1LSniper3Image;
        return p1RSniper3Image;
      }
    }
  if (direction==CLASP_LEFT) return p1LSniperFireImage;
  return p1RSniperFireImage;
  }

//Returns the Player Two Image
protected Image getP2Image(){
  if (direction==CLASP_CENTER) {
    if (hasLanded()) return p2Image;
    return p2SniperImage;
    }
  if (isFire()==false) {
    switch (getAnimationStep()) {
      case 1:
        if (direction==CLASP_LEFT) return p2SniperImage;
        return p2SniperImage;
      case 2:
        if (direction==CLASP_LEFT) return p2LSniper2Image;
        return p2RSniper2Image;
      case 3:
        if (direction==CLASP_LEFT) return p2LSniper3Image;
        return p2RSniper3Image;
      }
    }
  if (direction==CLASP_LEFT) return p2LSniperFireImage;
  return p2RSniperFireImage;
  }

protected void setFire(boolean value){
  fireStatus=value;
  }
protected boolean isFire(){
  return fireStatus;
  }

protected void setDirection(int dir){
  direction=dir;
  }

protected int getDirection(){
  return direction;
  }

protected void nextOdds(){
  odds+=3;
  }

protected int getOdds(){
  return odds;
  }


public Sniper(int player) {
  super(player);
  fireStatus=false;       //default to not Currently Firing
  setDirection(CLASP_CENTER);
  landed=false;           //display floating image till it lands
  odds=2;                 //starrt a 1 in 2 odds
  }

protected void setLanded(boolean value){
  landed=value;
  }
protected boolean hasLanded(){
  return landed;
  }

static Image getTinyImage(int player) {
  if (player==P1) return p1SniperImage.getScaledInstance(20,20,Image.SCALE_DEFAULT);
  else return p2SniperImage.getScaledInstance(20,20,Image.SCALE_DEFAULT);
}

}