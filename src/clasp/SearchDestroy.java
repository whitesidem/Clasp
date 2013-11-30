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

public class SearchDestroy extends ClaspPiece {

static private ImageIcon imgP1SchPiece;
static private ImageIcon imgP2SchPiece;
static private ImageIcon imgP1SchFirePiece;
static private ImageIcon imgP2SchFirePiece;
static private ImageIcon imgP1SchFire2Piece;
static private ImageIcon imgP2SchFire2Piece;
static protected Image p1SchImage;
static protected Image p2SchImage;
static protected Image p1SchFireImage;
static protected Image p2SchFireImage;
static protected Image p1SchFire2Image;
static protected Image p2SchFire2Image;

private boolean fireStatus;


//Static Initialisation block - ran only once.
static {
   imgP1SchPiece = new ImageIcon(SearchDestroy.class.getResource("BlueSearchSeek.gif"));
   imgP2SchPiece = new ImageIcon(SearchDestroy.class.getResource("RedSearchSeek.gif"));
   imgP1SchFirePiece = new ImageIcon(SearchDestroy.class.getResource("BlueSearchSeekFire.gif"));
   imgP2SchFirePiece = new ImageIcon(SearchDestroy.class.getResource("RedSearchSeekFire.gif"));
   imgP1SchFire2Piece = new ImageIcon(SearchDestroy.class.getResource("BlueSearchSeekFire2.gif"));
   imgP2SchFire2Piece = new ImageIcon(SearchDestroy.class.getResource("RedSearchSeekFire2.gif"));
   p1SchImage = imgP1SchPiece.getImage();
   p2SchImage = imgP2SchPiece.getImage();
   p1SchFireImage = imgP1SchFirePiece.getImage();
   p2SchFireImage = imgP2SchFirePiece.getImage();
   p1SchFire2Image = imgP1SchFire2Piece.getImage();
   p2SchFire2Image = imgP2SchFire2Piece.getImage();
}

  public SearchDestroy(int player) {
    super(player);
    fireStatus=false;       //default to not Currently Firing
  }


//Returns the Player One Image
protected Image getP1Image(){
  if (isFire()) {
    if (getAnimationStep()==1) return p1SchFireImage;
    else return p1SchFire2Image;
    }
  else return p1SchImage;
  }

//Returns the Player Two Image
protected Image getP2Image(){
  if (isFire()) {
    if (getAnimationStep()==1) return p2SchFireImage;
    else return p2SchFire2Image;
    }
  else return p2SchImage;
  }

protected void setFire(boolean value){
  fireStatus=value;
  }
protected boolean isFire(){
  return fireStatus;
  }

static Image getTinyImage(int player) {
  if (player==P1) return p1SchImage.getScaledInstance(20,20,Image.SCALE_DEFAULT);
  else return p2SchImage.getScaledInstance(20,20,Image.SCALE_DEFAULT);
}

}





