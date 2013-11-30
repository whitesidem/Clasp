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

public class Egg extends ClaspBasePiece {

static private ImageIcon imgEggPiece;
static private ImageIcon imgP1EggPiece;
static private ImageIcon imgP2EggPiece;
static private ImageIcon imgEggCrack1Piece;
static private ImageIcon imgEggCrack2Piece;
static protected Image EggImage;
static protected Image EggCrack1Image;
static protected Image EggCrack2Image;
static protected Image p1EggImage;
static protected Image p2EggImage;
static public final int EGG_HATCH_RAND = 25;   //Chance of egg hatching
static public final int EHM_RND = 0;           //Random Chances of Egg hatching
static public final int EHM_ALL = 1;           //Hatch All Remaining Eggs

private boolean CrackStatus;
private int hatchPlayer;

//Static Initialisation block - ran only once.
static {
   imgEggPiece = new ImageIcon(Egg.class.getResource("Egg.gif"));
   imgEggCrack1Piece = new ImageIcon(Egg.class.getResource("Egg2.gif"));
   imgEggCrack2Piece = new ImageIcon(Egg.class.getResource("Egg3.gif"));
   imgP1EggPiece = new ImageIcon(Egg.class.getResource("BlueEgg.gif"));
   imgP2EggPiece = new ImageIcon(Egg.class.getResource("RedEgg.gif"));
   EggImage = imgEggPiece.getImage();
   EggCrack1Image = imgEggCrack1Piece.getImage();
   EggCrack2Image = imgEggCrack2Piece.getImage();
   p1EggImage = imgP1EggPiece.getImage();
   p2EggImage = imgP2EggPiece.getImage();
}

  public Egg() {
    super();
    //Setup Player
    this.player=P0;
    CrackStatus=false;       //default to not Currently Firing
  }


//Returns the Player One Image
protected Image getP0Image(){
  if (isCrack()) {
    if (getAnimationStep()==2) return EggCrack2Image;
    if (getAnimationStep()==1) return EggCrack1Image;
    else {
      if (hatchPlayer==ClaspConstants.P1) return p1EggImage;
      else return p2EggImage;
      }
    }
  else return EggImage;
  }

protected void setCrack(boolean value){
  CrackStatus=value;
  }

protected boolean isCrack(){
  return CrackStatus;
  }
  public void setHatchPlayer(int hatchPlayer) {
    this.hatchPlayer = hatchPlayer;
  }
  public int getHatchPlayer() {
    return hatchPlayer;
  }


}
