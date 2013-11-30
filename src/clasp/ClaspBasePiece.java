package clasp;

import java.awt.*;  //Graphics
import javax.swing.*; //Jcomponent

/**
 * Title:        Clasp Base Piece
 * Description:  Contains Base Mechanism for a Piece
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public abstract class ClaspBasePiece implements ClaspConstants{

static private ImageIcon imgTarget;
static private ImageIcon imgBombed;
static private ImageIcon imgBombed2;
static private ImageIcon imgBombed3;
static private ImageIcon imgP0Piece;
static private ImageIcon imgP1Piece;
static private ImageIcon imgP2Piece;
static protected Image targetImage;
static protected Image bombedImage;
static protected Image bombed2Image;
static protected Image bombed3Image;
static protected Image p0Image;
static protected Image p1Image;
static protected Image p2Image;
static private ImageIcon imgP1LoserPiece;
static private ImageIcon imgP2LoserPiece;
static private ImageIcon imgP0LoserPiece;
static protected Image p1LoserImage;
static protected Image p2LoserImage;
static protected Image p0LoserImage;


//Position of Object in Array
protected int row=-1;
protected int col=-1;

//Protected
protected int player;
protected boolean targetted=false;
protected boolean bombed=false;
protected boolean indestructable=false;

//Private
private int height;
private int width;
private int state=STILL;      //State Object in (e.g. Moving down animation etc)
private int animationStep=0;  //Stores Current step in Animation



//Static Initialisation block - ran only once.
static {
   imgTarget = new ImageIcon(ClaspBasePiece.class.getResource("Target.gif"));
   imgBombed = new ImageIcon(ClaspBasePiece.class.getResource("Bombed.gif"));
   imgBombed2 = new ImageIcon(ClaspBasePiece.class.getResource("Bombed2.gif"));
   imgBombed3 = new ImageIcon(ClaspBasePiece.class.getResource("Bombed3.gif"));
//   imgP0Piece = new ImageIcon(ClaspBasePiece.class.getResource("NullCircle.gif"));
   imgP0Piece = new ImageIcon(ClaspBasePiece.class.getResource("BlackPiece.gif"));
   imgP1Piece = new ImageIcon(ClaspBasePiece.class.getResource("BlueCircle.gif"));
   imgP2Piece = new ImageIcon(ClaspBasePiece.class.getResource("RedCircle.gif"));
   imgP1LoserPiece = new ImageIcon(ClaspBasePiece.class.getResource("LoserBlueCircle.gif"));
   imgP2LoserPiece = new ImageIcon(ClaspBasePiece.class.getResource("LoserRedCircle.gif"));
   imgP0LoserPiece = new ImageIcon(ClaspBasePiece.class.getResource("LostPiece.gif"));
   targetImage = imgTarget.getImage();
   bombedImage = imgBombed.getImage();
   bombed2Image = imgBombed2.getImage();
   bombed3Image = imgBombed3.getImage();
   p0Image = imgP0Piece.getImage();
   p1Image = imgP1Piece.getImage();
   p2Image = imgP2Piece.getImage();
   p1LoserImage = imgP1LoserPiece.getImage();
   p2LoserImage = imgP2LoserPiece.getImage();
   p0LoserImage = imgP0LoserPiece.getImage();
}

public ClaspBasePiece() {
    player=P0;      //Default to non player
    state=STILL;
    animationStep=0;
    height=55;    //default - covers circle
    width=55;     //default - covers circle
    targetted=false;
    bombed=false;
    indestructable=false;
  }

//Get Appropriate image to show
//if mode is GM_SHOW_WIN we are showing the winning pieces so show the
//pieces loser image if it is not part of a winning line
protected Image getImage(int mode) {
    switch (getPlayer()) {
      case P1:
        if ((mode == IMG_SHOW_WIN) && (getState()!=WINNING_LINE)) return p1LoserImage;
        else return getP1Image();;
      case P2:
        if ((mode == IMG_SHOW_WIN) && (getState()!=WINNING_LINE)) return p2LoserImage;
        else return getP2Image();
      default:
        if ((mode == IMG_SHOW_WIN) && (getState()!=WINNING_LINE)) return p0LoserImage;
        return getP0Image();
    }
  }

//Returns the Player One Image
protected Image getP1Image(){
  return p1Image;
  }

//Returns the Player Two Image
protected Image getP2Image(){
  return p2Image;
  }

//Returns the Player Two Image
protected Image getP0Image(){
  return p0Image;
  }

//Returns the Target Image
static protected Image getTargetImage(){
  return targetImage;
  }

//Returns the Target Image
static protected Image getBombedImage(int step){
  if (step==1) return bombedImage;
  if (step==2) return bombed2Image;
  else return bombed3Image;
  }




protected int nextAnimationStep() {
  return animationStep++;
  }
protected int prevAnimationStep() {
  return animationStep--;
  }

protected void startAnimation(int state) {
  animationStep=1;
  this.state=state;
  }

  protected void endAnimation() {
  animationStep=0;
  this.state=STILL;
  }


//Get & Sets
protected void setLocation(int row, int col) {
  this.row=row;
  this.col=col;
}
public int getRow() {
  return this.row;
}
public int getCol() {
  return this.col;
}
protected int getState() {
  return this.state;
}
protected void setState(int state) {
  this.state=state;
}
protected int getPlayer() {
  return this.player;
}
protected void setPlayer(int player) {
  this.player=player;
}
protected int getAnimationStep() {
  return this.animationStep;
}
protected void setAnimationStep(int step) {
  animationStep=step;
}
//Get Dimensions
protected void setHeight(int height) {
  this.height=height;
}
protected void setWidth(int width) {
  this.width=width;
}
protected int getHeight() {
  return this.height;
}
protected int getWidth() {
  return this.width;
}
protected boolean isTargetted() {
  return targetted;
}
protected void setTargetted(boolean value) {
  targetted=value;
}
protected boolean isBombed() {
  return bombed;
}
protected void setBombed(boolean value) {
  bombed=value;
}
protected boolean isIndestructable() {
  return indestructable;
}
protected void setIndestructable(boolean value) {
  indestructable=value;
}
} //end of class
