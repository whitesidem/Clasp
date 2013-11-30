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

public class Leveller extends ClaspPiece {

static private ImageIcon imgP1LevPiece;
static private ImageIcon imgP2LevPiece;
static private ImageIcon imgP1LevFirePiece;
static private ImageIcon imgP2LevFirePiece;
static private ImageIcon imgLevFire2Piece;
static protected Image p1LevImage;
static protected Image p2LevImage;
static protected Image p1LevFireImage;
static protected Image p2LevFireImage;
static protected Image levFire2Image;

private boolean fireStatus;

//Static Initialisation block - ran only once.
static {
   imgP1LevPiece = new ImageIcon(Leveller.class.getResource("BlueLeveller.gif"));
   imgP2LevPiece = new ImageIcon(Leveller.class.getResource("RedLeveller.gif"));
   imgP1LevFirePiece = new ImageIcon(Leveller.class.getResource("BlueLevellerFire.gif"));
   imgP2LevFirePiece = new ImageIcon(Leveller.class.getResource("RedLevellerFire.gif"));
   imgLevFire2Piece = new ImageIcon(Leveller.class.getResource("LevellerFire2.gif"));
   p1LevImage = imgP1LevPiece.getImage();
   p2LevImage = imgP2LevPiece.getImage();
   p1LevFireImage = imgP1LevFirePiece.getImage();
   p2LevFireImage = imgP2LevFirePiece.getImage();
   levFire2Image = imgLevFire2Piece.getImage();
}

  public Leveller(int player) {
    super(player);
    fireStatus=false;       //default to not Currently Firing
  }


//Returns the Player One Image
protected Image getP1Image(){
  if (isFire()) {
    if (getAnimationStep()==1) return p1LevFireImage;
    else return levFire2Image;
    }
  else return p1LevImage;
  }

//Returns the Player Two Image
protected Image getP2Image(){
  if (isFire()) {
    if (getAnimationStep()==1) return p2LevFireImage;
    else return levFire2Image;
    }
  else return p2LevImage;
  }

protected void setFire(boolean value){
  fireStatus=value;
  }
protected boolean isFire(){
  return fireStatus;
  }

static Image getTinyImage(int player) {
  if (player==P1) return p1LevImage.getScaledInstance(20,20,Image.SCALE_DEFAULT);
  else return p2LevImage.getScaledInstance(20,20,Image.SCALE_DEFAULT);
}

}
