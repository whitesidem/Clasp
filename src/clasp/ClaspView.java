package clasp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;   //Sleep
import java.util.*;   //Observer
import java.awt.geom.*; //Shapes

/**
 * Title:        Clasp View Class
 * Description:  Draws grid and pieces on the grid.
 * Copyright:    Copyright (c) 2002
 * @author M.Whiteside
 * @version 1.0
 */

public class ClaspView extends JComponent implements Observer, ClaspConstants{

  protected static final int IMAGE_RED=0;
  protected static final int IMAGE_BLUE=1;
  protected static final int IMAGE_YELLOW=2;
  protected static final int IMAGE_GREEN=3;
  protected static final int IMAGE_EARTH=4;
  protected static final int IMAGE_RACE=5;

  public final static int GRID_X_OFFSET = 20;
  public final static int GRID_Y_OFFSET = 55;


  //Private entities
  private Image gridImage;
  private int imageType;
  private ClaspGameStatus gameStatus;
  private javax.swing.Timer timer;
  private boolean isPieceDropping=false;
  private boolean isPieceSliding=false;


  //Owned Components
  BorderLayout borderLayout = new BorderLayout();


  //Constructor for View passing application
  public ClaspView(ClaspGameStatus gameStatus) {
    this.gameStatus=gameStatus;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Initialise view / create layout and components etc
  private void jbInit() throws Exception {
    this.setLayout(borderLayout);
    this.setBackground(Color.black);
  }


  //==============Observer Update==========================
  //Use this to post paint required events
  //Not used very ofter as most displays demand instand update via the
  //Instant piece display routines
  public void update(Observable o, Object oPiece)
  {
    if (oPiece==null)
      repaint();                      //Repaint entire page
    else
      switch(gameStatus.getClaspModel().getGridMode()) {
        case GM_CHOOSE_COL:           //Repaint column selection row
            repaint(new Rectangle( getXPos(1), 0, (COL_SPACER * gameStatus.getGridCols()),ROW_SPACER));
          break;
        default:                      //Repaint boundry around single piece
          repaint(this.getBounds((ClaspBasePiece)oPiece)); //Repaint only passed area
        }
  }

  //==============Instant Piece display Update==========================
  //Use this when paint must occurr straight away
  public void paintPiece(Object oPiece)
  {
    Graphics GD=getGraphics();    //get current graphics context
    GD.setClip(this.getBounds((ClaspBasePiece)oPiece)); //get area to repaint
    paint(GD);                    //repaint clipped area
    GD.dispose();
  }

  //==============Instant Grid Display Update==========================
  //Use this when paint of entire grid must occurr straight away
  public void paintGrid(){
    Graphics GD=getGraphics();    //get current graphics context
    GD.setClip(new Rectangle(getXPos(1),getYPos(1),(COL_SPACER*gameStatus.getGridCols()),(ROW_SPACER*gameStatus.getGridRows()))); //get area to repaint
    paint(GD);                    //repaint clipped area
    GD.dispose();
  }

  //==============Instant Full View Update==========================
  //Use this when paint of entire grid plus surround must occurr straight away
  public void paintFullViewArea(){
    Graphics GD=gameStatus.getMainFrame().getGraphics();    //get current graphics context
    GD.setClip(gameStatus.getMainFrame().getBounds());
    gameStatus.getMainFrame().paint(GD);                    //repaint clipped area
    GD.dispose();

    GD=getGraphics();    //get current graphics context
    GD.setClip(this.getBounds());
    paint(GD);                    //repaint clipped area
    GD.dispose();
  }

  //==============Instant Grid Display Update==========================
  //Use this when paint of choose piece area must occurr straight away
  //Used when dropping a piece to remove choose piece from display.
  public void paintChoosePieceArea(){
    Graphics GD=getGraphics();    //get current graphics context
    int col = gameStatus.getClaspModel().getColSelected();
    GD.setClip(new Rectangle(getXPos(col),0,COL_SPACER,ROW_SPACER)); //get area to repaint
    paint(GD);                    //repaint clipped area
    GD.dispose();
  }


  //Find Bounds that surround the piece
 private Rectangle getBounds(ClaspBasePiece piece) {
    return (new Rectangle(getXPos(piece.getCol()),getYPos(piece.getRow()),piece.getWidth(),piece.getHeight()));
  }

  //Get Bounds of a single cell in the grid
  private Rectangle getBounds(int row, int col) {
    return (new Rectangle(getXPos(col),getYPos(row),COL_SPACER,ROW_SPACER));
  }

  //Get on screen X co-ord for a piece passing column
  protected int getXPos(int col) {
    return ((col-1)*COL_SPACER) + GRID_X_OFFSET;
  }

  //Get on screen Y co-ord for a piece passing row
  protected int getYPos(int row) {
    return ((row-1)*ROW_SPACER) + GRID_Y_OFFSET;
  }

  //Get X co-ord passing piece
  private int getXPos(ClaspBasePiece piece) {
    if (isPieceSliding==true) {
      if ((piece.getAnimationStep())>=((int)(COL_SPACER/ANIM_MOVE_DOWN_CHUNKS)))return (piece.getCol()*COL_SPACER)  + GRID_X_OFFSET;
      else return ((piece.getCol()-1)*COL_SPACER)+(piece.getAnimationStep()*ANIM_MOVE_ACROSS_CHUNKS)  + GRID_X_OFFSET; //Make sure it lands correctly at end of animation
    }
    return ((piece.getCol()-1)*COL_SPACER) + GRID_X_OFFSET;
  }

  //Get Y co-ord passing piece
  private int getYPos(ClaspBasePiece piece) {
    if (isPieceDropping==true) {
      if ((piece.getAnimationStep())>=((int)(ROW_SPACER/ANIM_MOVE_DOWN_CHUNKS)))return (piece.getRow()*ROW_SPACER)  + GRID_Y_OFFSET;
      else return ((piece.getRow()-1)*ROW_SPACER)+(piece.getAnimationStep()*ANIM_MOVE_DOWN_CHUNKS)  + GRID_Y_OFFSET; //Make sure it lands correctly at end of animation
    }
    return ((piece.getRow()-1)*ROW_SPACER)  + GRID_Y_OFFSET;
  }


  //===============Paint method for the grid==================
  public void paint(Graphics g){
    if (gameStatus.view_initialised==false) return;
    //Draw Grid
    ClaspBasePiece piece;
    Graphics2D g2D=(Graphics2D)g;
    g2D.drawImage(gridImage,1,1,this);
    int imageMode=IMG_STD;      //Mode of image to show
    if (gameStatus.getClaspModel().getGridMode()==GM_SHOW_WIN) imageMode=IMG_SHOW_WIN;
    //Draw all pieces
    for (int y=0; y<gameStatus.getGridRows(); y++) {
      for (int x=0; x<gameStatus.getGridCols(); x++) {
        piece=gameStatus.getClaspModel().getGridCell(x,y);
        if (piece != null) {
          g2D.drawImage(piece.getImage(imageMode),getXPos(piece),getYPos(piece),this);
          if (piece.isTargetted()) g2D.drawImage(piece.getTargetImage(),getXPos(piece),getYPos(piece),this);
          if (piece.isBombed()) g2D.drawImage(piece.getBombedImage(piece.getAnimationStep()),getXPos(piece),getYPos(piece),this);
          }
      }
    }

    //Draw Grid Lines
    if (imageType==IMAGE_EARTH) g2D.setPaint(Color.white);
    else if (imageType==IMAGE_RACE) g2D.setPaint(Color.green);
    else g2D.setPaint(Color.black);
    g2D.setStroke(new BasicStroke(2));
    Rectangle2D.Double rect;
    for (int i=0; i<gameStatus.getGridRows(); i++) {
      for (int j=0; j<gameStatus.getGridCols(); j++) {
        rect=new Rectangle2D.Double(getXPos(j+1),getYPos(i+1),COL_SPACER,ROW_SPACER);
        g2D.draw(rect);
      }
    }

    //Show piece during select column mode
    if (gameStatus.getClaspModel().getGridMode()==GM_CHOOSE_COL) {
      piece=gameStatus.getClaspModel().getChoosePiece();
      if (piece!=null) {
        int xpos = gameStatus.getClaspModel().getColSelected();
        g2D.drawImage(piece.getImage(IMG_STD),getXPos(xpos),2,this);
        }
    }

    super.paint(g);
  }

//Do animation for sliding a piece
protected void doSlidePieceAnimation(final int row, final int col, final int colInc){
  final ClaspBasePiece piece = gameStatus.getClaspModel().getGridCell(col-1,row-1);
  long time = System.currentTimeMillis();
  long interval = DROP_SPEED;
  piece.startAnimation(ANIM_MOVE_ACROSS);
  Graphics GD=getGraphics();    //get current graphics context
  isPieceSliding=true;
  piece.setAnimationStep(0);
  try {
    while(true) {                   //while dropping animation
      pause(DROP_SPEED);            //Pause between Animation frames
      piece.setAnimationStep(piece.getAnimationStep()+colInc);
      //Immediate repaint routine
      GD.setClip(new Rectangle( getXPos(col)-COL_SPACER,getYPos(row),(COL_SPACER*3),ROW_SPACER)); //get area to repaint
      paint(GD);                    //repaint clipped area
      if (Math.abs(piece.getAnimationStep())>((int)Math.abs(COL_SPACER/ANIM_MOVE_ACROSS_CHUNKS))) break;
      if (Math.abs(piece.getAnimationStep())>(((int)Math.abs(COL_SPACER/ANIM_MOVE_ACROSS_CHUNKS)))) break;
    }                               //End of while animation
  }
  finally {
    GD.dispose();
    isPieceSliding=false;
    }
  }

//Do animation for dropping a piece
protected void doDropPieceAnimation(final int row, final int col){
  final ClaspBasePiece piece = gameStatus.getClaspModel().getGridCell(col-1,row-1);
  long time = System.currentTimeMillis();
  long interval = DROP_SPEED;
  piece.startAnimation(ANIM_MOVE_DOWN);
  Graphics GD=getGraphics();    //get current graphics context
  isPieceDropping=true;
  try {
    while(true) {                   //while dropping animation
      pause(DROP_SPEED);            //Pause between Animation frames
      //Immediate repaint routine
      GD.setClip(new Rectangle( getXPos(col),getYPos(row)-ROW_SPACER,COL_SPACER,(ROW_SPACER*3))); //get area to repaint
      paint(GD);                    //repaint clipped area
      piece.nextAnimationStep();    //increment animation step
      if (piece.getAnimationStep()>((int)(ROW_SPACER/ANIM_MOVE_DOWN_CHUNKS))) break;
    }                               //End of while animation
  }
  finally {
    GD.dispose();
    isPieceDropping=false;
    }
}

//Pause events for passed milliseconds
public static void pause(int interval) {
  long time = System.currentTimeMillis();
  time+=interval;
  try {
    //delay
    Thread.sleep(Math.max(0,time-System.currentTimeMillis()));
    }
  catch (InterruptedException e) {
    //Ignore
    }
  }

//Set Image display to approriate image
  protected void SetGridImage(int img){
    if (imageType==img) return;   //Already Set to this Mode
    imageType=img;
    switch(img){
      case IMAGE_RED:
        gridImage = new ImageIcon(ClaspView.class.getResource("redPlanet.jpeg")).getImage();
      break;
      case IMAGE_BLUE:
        gridImage = new ImageIcon(ClaspView.class.getResource("bluePlanet.jpeg")).getImage();
      break;
      case IMAGE_YELLOW:
        gridImage = new ImageIcon(ClaspView.class.getResource("yellowPlanet.jpeg")).getImage();
      break;
      case IMAGE_GREEN:
        gridImage = new ImageIcon(ClaspView.class.getResource("greenPlanet.jpeg")).getImage();
      break;
      case IMAGE_RACE:
        gridImage = new ImageIcon(ClaspView.class.getResource("MoonSurface.gif")).getImage();
      break;
      default:
        gridImage = new ImageIcon(ClaspView.class.getResource("earth.gif")).getImage();
      break;
      }
    }

}









