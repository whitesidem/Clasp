package clasp;

import java.util.*;   //Observable
import javax.swing.*;   //JOptionPane Dialogs
import java.awt.*;

/**
 * Title:        Clasp Model for Controlling the Grid
 * Description:  Has all the buisiness logic for controlling the grid itself
 *               except for the visual side that is done in the ClaspView
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class ClaspModel extends Observable implements ClaspConstants {

//Constants
  private final static int NO_JOKER=0;
  private final static int JOKER_FOUND=1;
  private final static int JOKER_WIN=2;
  private final static int LANDSCAPE_OCCURANCE=5;
  private final static int RAND_DROP_OCCURANCE=5;

//Private members
  private int levelMode=-1;                   //Special level mode indicator
  private ClaspBasePiece choosePiece;      //Piece for selecting and Dropping
  private ClaspBasePiece[][] gridPieces;   //Storage for Arena
  private int gridMode;                    //Current Grid Action Mode
  private boolean processing = false;
  private ClaspGameStatus gameStatus;
  private int colSelected=-1;                 //Column Currently selected
  private ClaspGridActions gridActions;
  private int p1JokerStatus=NO_JOKER;
  private int p2JokerStatus=NO_JOKER;
  private boolean firstMove=false;        //True for first move of each grid
  protected int storePreRaceRows;
  protected int storePreRaceCols;
  protected boolean needRaceRestore=false;


  static Random rand= new Random();

//Constructor
public ClaspModel(ClaspGameStatus gameStatus) {
    this.gameStatus=gameStatus;                             //All main units point to the main controller
    gridPieces= new ClaspBasePiece[gameStatus.getGridRows()][gameStatus.getGridCols()];   //Full Grid 8 rows, 9 cols
    gridActions=new ClaspGridActions(this,gameStatus);
}


//Start new level for grid initialisation
protected void startNewGrid() {
  int opt;
  boolean password_start=true;                        //Indicator to see if password start question needed

  this.gameStatus.getMainFrame().requestFocus();                 //This must have focus for keypressing to work

  //First grid - load from saved game option
  if (gameStatus.view_initialised==false) {           //First time in give load option
      opt=JOptionPane.showConfirmDialog(gameStatus.getMainFrame(),
        "Do you Want to Continue from Last Game",
        "Load Game Question",
        JOptionPane.YES_NO_OPTION);
      if (opt==JOptionPane.YES_OPTION){
        password_start=false;                         //Continuing from last game so no password start
        gameStatus.loadGame();                        //Load any previously saved info
        }
    }
  else gameStatus.saveGame();                         //Store Changed Properties to File if not first ever grid

  //Set Game Mode
  gameStatus.nextGameLevel();                       //increment level
  setProcessing(true);                              //Dis-Allow Other Processes
  firstMove=true;                                   //setup this as first move
  //Set Level Mode
  if (gameStatus.getGameLevel()>3) {                //Only allow different starts after so many standard level
    int land_rnd=(rand.nextInt(LANDSCAPE_OCCURANCE));  //random for Landscapes Occurance
    int drop_rnd=(rand.nextInt(RAND_DROP_OCCURANCE));  //random for Rand Drop Occurance
    if (land_rnd==1) setlevelMode(LM_LANDSCAPE);  //Landscape Level
    else if (drop_rnd==1) setlevelMode(LM_RAND_DROPS);  //Random Drop Level
    else setlevelMode(LM_STD);                        //Standard Level mode
    }
  else setlevelMode(LM_STD);                        //Standard Level mode

  clearGrid();                                      //Clear Grid of all pieces
  gameStatus.getMainFrame().setGridShape(MainFrame.GRID_RAND);  //Change grid Shape if using random grids
  if (gameStatus.view_initialised==false) {
    if (password_start==true) {
      gameStatus.getWeaponPassword(ClaspConstants.P1);           //Start game weapons
      gameStatus.getWeaponPassword(ClaspConstants.P2);
      }
    gameStatus.view_initialised=true;               //Allow painting to take place
    gameStatus.getMainFrame().setVisible(true);
    }
  gameStatus.getMainFrame().updateCtrlPanel();      //Show Current Scores etc
  gameStatus.getClaspView().paintFullViewArea();    //Paint newly cleared grid with correct background
  gridActions.NewGridSetup();                       //Functions like dropping landscapes
  setProcessing(false);                             //Allow Other Processes
  gameStatus.setCurrPlayer((gameStatus.getGameLevel()%2==0)?P2:P1);
  gameStatus.getMainFrame().repaint();
  setUpSelectPiece();//Start with Select Piece


    this.gameStatus.getMainFrame().requestFocus();                 //This must have focus for keypressing to work


  }


//Place choose piece on grid - ready for new player
private void setUpSelectPiece(){
  int player=gameStatus.getCurrPlayer();
  setChoosePiece(new SelectPiece(player));
  int opt;
  if (firstMove==true) {
    //Give option to Use Joker
    if (gameStatus.getTotalJoker(player)>0) {
      opt=JOptionPane.showConfirmDialog(gameStatus.getMainFrame(),
        ((player==ClaspConstants.P1)?"Blue Team ":"Red Team ") + "Do you Want to Use your Joker",
          "Joker Question",
          JOptionPane.YES_NO_OPTION);
      if (opt==JOptionPane.YES_OPTION) setChoosePiece(new Joker(player));
      }
    }
  selectFreeColumn(CLASP_CENTER);
  gameStatus.getMainFrame().updatePieceDisplay();

  }

//Called when grid is full
private void endGrid() {
    gridActions.hatchEggs(Egg.EHM_ALL);                 //Hatch All remaining eggs present
    JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"!!The Grid is Full!! - Lets see who won this one");
    calcScore();                                  //Calc Score for this grid
    buyWeapons(P1);
    buyWeapons(P2);
    startNewGrid();
    }

private void buyWeapons(int player) {
    WeaponBuyDlg weapDlg = new WeaponBuyDlg(gameStatus.getMainFrame(),player,gameStatus);
    weapDlg.pack();
    //    weapDlg.setSize( new Dimension(360,180));
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = weapDlg.getSize();
    weapDlg.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    weapDlg.show();
  }

//Clear Grid and contents
private void clearGrid(){
  for (int y=0; y<gameStatus.getGridRows(); y++) {
    for (int x=0; x<gameStatus.getGridCols(); x++) {
      setGridCell(x,y,null);
      }
    }
  gameStatus.getClaspView().repaint();        //Show clear Grid
  }


//Select a free column position for dropping.
//Pass whether to search for space at CENTER / RIGHT or LEFT
//Returns new column position
//Returns -1 if no free columns exist at all which indicates end of current game
public void selectFreeColumn(int position) {
  int newPosition=getColSelected();
  //See if grid is full or not.
  boolean freeSpace = false;
  for (int x=0;x<gameStatus.getGridCols();x++) {
    if (getGridCell(x,0)==null) {
      freeSpace=true;
      break;
      }
    }
  //grid is full
  if (freeSpace==false) {

    endGrid();
    return;
    }
  //Now find next logical free space to left / right or nearest center
  while (true) {
    switch (position) {
      case CLASP_CENTER:
        newPosition=(gameStatus.getGridCols()/2)+1;
        position=CLASP_RIGHT;     //Next time move right if center is full
        break;
      case CLASP_RIGHT:
        newPosition++;
        if (newPosition>gameStatus.getGridCols()) newPosition=1;
        break;
      case CLASP_LEFT:
        newPosition--;
        if (newPosition<1) newPosition=gameStatus.getGridCols();
        break;
    } //end switch position
    //Found a spare place to drop
    if (getGridCell(newPosition-1,0)==null) {
      break;
      }
  } //end of while find logical space
  setGridMode(GM_CHOOSE_COL);           //set to choosing position mode
  setColSelected(newPosition);          //set position chosen
} //end of select free column method

//perform drop - highest level call. - could be interface call
protected void performDrop() {
  gridActions.randomDrops();     //Perform Random Drops
  int player=gameStatus.getCurrPlayer();
  ClaspBasePiece piece;
  piece=getChoosePiece();
  if (piece.getClass().getName().equals("clasp.Magnet")==true) gameStatus.decTotalMagnet(player);
  if (piece.getClass().getName().equals("clasp.SearchDestroy")==true) gameStatus.decTotalSch(player);
  if (piece.getClass().getName().equals("clasp.AssassinPiece")==true) gameStatus.decTotalAssassin(player);
  if (piece.getClass().getName().equals("clasp.Sniper")==true) gameStatus.decTotalSniper(player);
  if (piece.getClass().getName().equals("clasp.Leveller")==true) gameStatus.decTotalLeveller(player);
  if (piece.getClass().getName().equals("clasp.Joker")==true) gameStatus.decTotalJoker(player);
  if (piece.getClass().getName().equals("clasp.Trophy1")==true) gameStatus.decTotalTrophy1(player);
  if (piece.getClass().getName().equals("clasp.Trophy2")==true) gameStatus.decTotalTrophy2(player);

  setChoosePiece(null);
  gameStatus.getClaspView().paintChoosePieceArea();              //remove choose piece from view
  setGridMode(GM_PERFORM_ACTION);                 //performing drop action mode
  setProcessing(true);            //Stop all other actions whilst animating
  addPiece(piece,getColSelected());    //add the piece to grid to start the drop

  //functionality that happens after drop has finished
  gridActions.hatchEggs(Egg.EHM_RND);                                //Chance to Hatch Any eggs present
  while (gridActions.searchDestroy()==true){ //Check if any search destroy effects have happened - if they have that will call gravity at the end.
    doGravity();                            //Doing gravity may cause more search destroy events
    }
  //functionality that may occur after finished drop
  //like random piece fallings
  gridActions.randomDrops();
  //Setup for next players turn
  setProcessing(false);           //Allow Other Processes
  gridActions.checkResearch();    //See if Research is finished
  firstMove=false;                //First Move Over
  gameStatus.switchPlayer();
  setUpSelectPiece();
  }



//Add a piece dropped from the top
protected void addPiece(ClaspBasePiece piece,int column) {
  //if column> GRID_ACROSS of column < 1 throw exception
  //if piece is null throw exception
  setPieceLocation(piece,1,column);     //Place Piece on Grid
  doDropPiece(1,column);
  }

//Add piece to specific location
public void setPieceLocation(ClaspBasePiece piece, int row, int col) {
  setGridCell(col-1,row-1,piece);                 //put piece into grid
  piece.setLocation(row,col);                     //set location in object
  gameStatus.getClaspView().paintPiece(piece);    //Paint this piece before it drops
  }

//Move piece to new location - removing from last location
public void movePiece(ClaspBasePiece piece, int newRow, int newCol) {
  setGridCell(piece.getCol()-1,piece.getRow()-1,null);
  setPieceLocation(piece,newRow,newCol);                //Place in new Position
  }

//Drop piece functionality
protected void doDropPiece(int row, int col){
  while (true) {        //Loop untill hit bottom
  //landed at bottom
  if (row >= gameStatus.getGridRows()){
   endDropPiece(row,col); //piece on bottom row
   return;
   }

  //Side actions need checking
  gridActions.CheckSideActions(row,col);
  if (getGridCell(col-1,row-1)==null) return; //piece no longer exists - maybe shot by sniper

  //landed on another piece
  if (getGridCell(col-1,row)!=null) {
    endDropPiece(row,col);    //piece landed on another piece
    return;
    }
  try {
    gameStatus.getClaspView().doDropPieceAnimation(row, col);    //Start Animation
  }
  finally {
    ClaspBasePiece piece;
    piece=getGridCell(col-1,row-1);
    piece.endAnimation();           //End Animation Mode
    row++;
    movePiece(piece,row,col);     //Physically move the piece in the grid
    }
  }     //drop loop
  }


//DO Piece Has landed functionality here then gravity for all other pieces
protected void endDropPiece(int row, int col){
  gridActions.doLandedAction(row,col);      //Do Landed Action passing Grid
  doGravity();                    //Check Gravity for other pieces
  }


//Drop All pieces with gaps below them - stopping after each one to do animation
//return true if a gap was found
protected void doGravity(){
  ClaspBasePiece piece;
  gravity:
  for (int y=0; y<gameStatus.getGridRows()-1; y++) {
    for (int x=0; x<gameStatus.getGridCols(); x++) {
      piece=getGridCell(x,y);
      if (piece != null) {
        if (getGridCell(x,y+1)==null) {
          doDropPiece(y+1,x+1);
          break gravity;;
        }
      }
    }
  }
 }



protected void restructureGrid(ClaspProperties oldProps){
  ClaspProperties newProps = gameStatus.getClaspProperties();
  ClaspBasePiece[][] newGridPieces= new ClaspBasePiece[newProps.getGridRows()][newProps.getGridCols()];   //New Full Grid
  int row_counter=1;
  int col_counter=1;
  int new_y_row=newProps.getGridRows()-1;
  ClaspBasePiece piece;
  for (int y=oldProps.getGridRows()-1; y>=0; y--,new_y_row--,row_counter++) {
    if (row_counter>newProps.getGridRows()) break;
    col_counter=1;
    for (int x=0; x<oldProps.getGridCols(); x++, col_counter++) {
      if (col_counter>newProps.getGridCols()) continue;
        piece=gridPieces[y][x];
        if (piece!=null){
          newGridPieces[new_y_row][x]=piece;
          piece.setLocation(new_y_row+1,x+1);            //set location in object
          }
      }
    }
  gridPieces=newGridPieces;
  if (colSelected>newProps.getGridCols()) {
    selectFreeColumn(CLASP_CENTER);                   //Select a free column default to Center
    }
  gameStatus.getClaspView().repaint();        //Show clear Grid
}



//Get & Sets
  protected ClaspBasePiece getGridCell(int x, int y){
    return gridPieces[y][x];
  }

  protected void setGridCell(int x, int y, ClaspBasePiece piece){
    gridPieces[y][x]=piece;
  }

  protected ClaspBasePiece getChoosePiece(){
    return choosePiece;
  }

  private void setChoosePiece(ClaspBasePiece piece){
    choosePiece=piece;  //note - this can be set to null
  }

  private void setlevelMode(int mode) {
    levelMode=mode;
    if (levelMode==LM_RACE) gameStatus.getMainFrame().setTitle("Zig/Zag/Zog - \"The Zog National Races\"");
    else gameStatus.getMainFrame().setTitle("Zig/Zag/Zog - \"The Zog Matrix\"");

    if (levelMode==LM_LANDSCAPE){
      gameStatus.getMainFrame().statusBar.setMessageText("Alien Planet");
      gameStatus.getClaspView().SetGridImage(ClaspView.IMAGE_GREEN);
      }
    else if (levelMode==LM_RAND_DROPS){
      gameStatus.getMainFrame().statusBar.setMessageText("Sun Spot Disturbances");
      gameStatus.getClaspView().SetGridImage(ClaspView.IMAGE_YELLOW);
      }
    else if (levelMode==LM_RACE){
      gameStatus.getMainFrame().statusBar.setMessageText("A day at the Races");
      gameStatus.getClaspView().SetGridImage(ClaspView.IMAGE_RACE);
      }
    else {
      gameStatus.getMainFrame().statusBar.setMessageText("Standard");
      if(gameStatus.getGridsWon(P1)>gameStatus.getGridsWon(P2)) gameStatus.getClaspView().SetGridImage(ClaspView.IMAGE_BLUE);
      else if(gameStatus.getGridsWon(P1)<gameStatus.getGridsWon(P2)) gameStatus.getClaspView().SetGridImage(ClaspView.IMAGE_RED);
      else gameStatus.getClaspView().SetGridImage(ClaspView.IMAGE_EARTH);
      }
  }

  protected int getLevelMode() {
    return(levelMode);
  }


  protected boolean isProcessing(){
    return processing;
  }
  private void setProcessing(boolean state){
    processing=state;
  }

  protected int getColSelected(){
    return colSelected;
  }
  protected void setColSelected(int selected){
    colSelected=selected;
    setChanged();
    notifyObservers(getChoosePiece());
  }

  protected int getGridMode(){
    return gridMode;
  }
  protected void setGridMode(int mode){
    if (gridMode==mode) return;
    if (gridMode==GM_CHOOSE_COL) {
      gameStatus.getClaspView().repaint();
    }
    gridMode=mode;
    }


private void calcScore() {
  long cashTotal[]={0,0};               //Player total cash for this grid
  short totalLines[]={0,0};             //Player Total Lines
  long winBonus=0L;                     //Bonus for Winning
  int currSide=P0;                      //Current Player
  p1JokerStatus=NO_JOKER;                //Check Joker Status
  p2JokerStatus=NO_JOKER;                //Check Joker Status
  ClaspBasePiece piece;
  int winningPlayer=P0;

  //Loop Rows and columns
  for (int row=1; row<=gameStatus.getGridRows(); row++) {
    for (int col=1; col<=gameStatus.getGridCols(); col++) {
      piece=getGridCell(col-1,row-1);
      if (piece==null) continue;        //Should never happen
      currSide=piece.getPlayer();       //Get Current Players Side P1 or P2
      if (currSide==P0) continue;               //Ignore Non-Players
      if((checkWinDir(currSide,col,row,1,0,true))==true){         //Check If Win to East
        checkWinDir(currSide,col,row,1,0,false);        //Set Pieces to Won Status
        totalLines[currSide]++;
        }
      if((checkWinDir(currSide,col,row,1,1,true))==true){         //Check If Win to SE
        checkWinDir(currSide,col,row,1,1,false);        //Set Pieces to Won Status
        totalLines[currSide]++;
        }
      if((checkWinDir(currSide,col,row,0,1,true))==true){         //Check If Win to South
        checkWinDir(currSide,col,row,0,1,false);        //Set Pieces to Won Status
        totalLines[currSide]++;
        }
      if((checkWinDir(currSide,col,row,-1,1,true))==true){         //Check If Win to SW
        checkWinDir(currSide,col,row,-1,1,false);        //Set Pieces to Won Status
        totalLines[currSide]++;
        }
     }  //End of inner loop (col)
  }     //End of outer loop (row)

  //Store Winning Player
  if(totalLines[P1]>totalLines[P2])winningPlayer=P1;
  else if(totalLines[P2]>totalLines[P1])winningPlayer=P2;
  else winningPlayer=P0;

  //Calc total Cash win for grid
  cashTotal[P1]=((long)totalLines[P1])*((long)(CLASP_LINE_SCORE));
  cashTotal[P2]=((long)totalLines[P2])*((long)(CLASP_LINE_SCORE));
  if(cashTotal[P1]>CLASP_WIN_LIMIT) cashTotal[P1]=CLASP_WIN_LIMIT;
  if(cashTotal[P2]>CLASP_WIN_LIMIT) cashTotal[P2]=CLASP_WIN_LIMIT;

  winBonus=5L*CLASP_LINE_SCORE;             	//Bonus For Winning
  if(gameStatus.getGameLevel()>8)winBonus=10L*CLASP_LINE_SCORE;   //Increased Bonus
  else if(gameStatus.getGameLevel()>16)winBonus=15L*CLASP_LINE_SCORE;   //Increased Bonus
  else if(gameStatus.getGameLevel()>32)winBonus=20L*CLASP_LINE_SCORE;   //Increased Bonus
  else if(gameStatus.getGameLevel()>64)winBonus=25L*CLASP_LINE_SCORE;   //Increased Bonus

  //Show Win Details
  setGridMode(GM_SHOW_WIN);
  gameStatus.getClaspView().repaint();        //Show clear Grid

  //Joker Status
  if(winningPlayer==P1){
    if (p1JokerStatus==JOKER_FOUND) {
      cashTotal[P1]*=JOKER_FOUND_MULTI;
      JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"Blue Team - Played A Joker in this Grid and Won the Grid. Bonus = Total Cash Win Multiplied by " + JOKER_FOUND_MULTI + " = " + cashTotal[P1]);
      }
    else if (p1JokerStatus==JOKER_WIN)  {
      cashTotal[P1]*=JOKER_WIN_MULTI;
      JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"Blue Team - Played A Joker in A winning Line in this Grid and Won the Grid. Total Cash Win Multiplied by " + JOKER_WIN_MULTI +" = " + cashTotal[P1]);
      }
    }
  else if(winningPlayer==P2){
    if (p2JokerStatus==JOKER_FOUND) {
      cashTotal[P2]*=JOKER_FOUND_MULTI;
      JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"Red Team - Played A Joker in this Grid and Won the Grid. Bonus = Total Cash Win Multiplied by " + JOKER_FOUND_MULTI +" = " + cashTotal[P2]);
      }
    if (p2JokerStatus==JOKER_WIN) {
      cashTotal[P2]*=JOKER_WIN_MULTI;
      JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"Red Team - Played A Joker in A winning Line in this Grid and Won the Grid. Total Cash Win Multiplied by " + JOKER_WIN_MULTI +" = " + cashTotal[P2]);
      }
    }

  //Add any Grid Win Bonus
  if(winningPlayer!=P0){
    JOptionPane.showMessageDialog(gameStatus.getMainFrame(),((winningPlayer==P1)?"Blue Team ":"Red Team ") + "Wins the grid and gets a win bonus of " + winBonus);
    cashTotal[winningPlayer]+=(long)(winBonus);
    gameStatus.increaseGridsWon(winningPlayer);
    if ((gameStatus.getGridsWon(winningPlayer)%5)==0) {
      gameStatus.increaseTotalJoker(winningPlayer);
      JOptionPane.showMessageDialog(gameStatus.getMainFrame(),((winningPlayer==P1)?"Blue Team":"Red Team") + ", you have won a Joker...");
      WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), winningPlayer, WeaponBuyDlg.JOKER);    //Show info if the weapon reseached
      }
    }
  else JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"It's a Draw");

  //Increase Overall Scores
  gameStatus.increaseTotalLines(P1,totalLines[P1]);
  gameStatus.increaseTotalLines(P2,totalLines[P2]);
  gameStatus.increaseCashTotal(P1,cashTotal[P1]);
  gameStatus.increaseCashTotal(P2,cashTotal[P2]);
  JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"Blue Team:" + totalLines[P1] + " = £" + cashTotal[P1] + " & Red Team:" + totalLines[P2] + " = £" + cashTotal[P2]);

  //Races Option
  if(winningPlayer!=P0) setupRace(winningPlayer);        //Go to races if a player has won a Grid

  }  //End of Calc Score Function


//Check up to (full line pieces from start) to see if a whole
//line of the same type as passed side exists.
//Pass the Current side checking and the co-ord position to check from
//Also pass a status to see whether we are checking for a full line or highlighting the full line.
//Return true if a full line is found.
//pass the increment for x and y to determine direction of check
private boolean checkWinDir(final int cs,final int col, final int row, int colInc, int rowInc, boolean checking) {
  int y=row;
  int x=col;
  int cntr=0;       //Only do loop for number of aligned counters
  int JokerStatus=NO_JOKER; //Search for Joker each time
  ClaspBasePiece piece;
  while(true) {
    cntr++;
    if (cntr>gameStatus.getFullLine()) {  //Must of found 4 in a row - this is a winning line
      if (cs==P1) {
        if (JokerStatus==JOKER_FOUND) p1JokerStatus=JOKER_WIN;  //Joker Was part of a winning line
        }
      else if (cs==P2) {
        if (JokerStatus==JOKER_FOUND) p2JokerStatus=JOKER_WIN;  //Joker Was part of a winning line
        }
      return(true);
      }
    if(checking) {
      if (y>gameStatus.getGridRows()) return(false);
      if (x>gameStatus.getGridCols()) return(false);
      if (x<1) return(false);
      if (y<1) return(false);
      piece=getGridCell(x-1,y-1);
      if (piece==null) return(false);
      if(piece.getPlayer()!=cs) return(false);   //Not same Side
      if(piece.getClass().getName().equals("clasp.Joker")) {  //Found A joker
        JokerStatus=JOKER_FOUND;
        if (cs==P1) {
          if (p1JokerStatus==NO_JOKER) p1JokerStatus=JOKER_FOUND; //Joker Found for this player - so far not in a winning line
          }
        else if (cs==P2) {
          if (p2JokerStatus==NO_JOKER) p2JokerStatus=JOKER_FOUND; //Joker Found for this player - so far not in a winning line
          }
        }
      }
    else getGridCell(x-1,y-1).setState(WINNING_LINE);             //Set Piece to be part of a winning line
    y+=rowInc;
    x+=colInc;
    }
  }



//Switch to Joker piece and update display
protected void switchToJokerPiece(){
  if (getGridMode()!=GM_CHOOSE_COL) return;   //only available if in select column mode
  setChoosePiece(new Joker(gameStatus.getCurrPlayer()));
  setChanged();
  notifyObservers(getChoosePiece());
  }

//Switch to Trophy1 piece and update display
protected void switchToTrophy1(){
  if (getGridMode()!=GM_CHOOSE_COL) return;   //only available if in select column mode
  setChoosePiece(new Trophy1(gameStatus.getCurrPlayer()));
  setChanged();
  notifyObservers(getChoosePiece());
  }

//Switch to Trophy2 piece and update display
protected void switchToTrophy2(){
  if (getGridMode()!=GM_CHOOSE_COL) return;   //only available if in select column mode
  setChoosePiece(new Trophy2(gameStatus.getCurrPlayer()));
  setChanged();
  notifyObservers(getChoosePiece());
  }

//Switch to standard piece and update display
protected void switchToStdPiece(){
  if (getGridMode()!=GM_CHOOSE_COL) return;   //only available if in select column mode
  setChoosePiece(new ClaspPiece(gameStatus.getCurrPlayer()));
  setChanged();
  notifyObservers(getChoosePiece());
  }

//Switch to assassin piece and update display
protected void switchToAssassin(){
  if (getGridMode()!=GM_CHOOSE_COL) return;   //only available if in select column mode
  setChoosePiece(new AssassinPiece(gameStatus.getCurrPlayer()));
  setChanged();
  notifyObservers(getChoosePiece());
  }

//Switch to Search Destroy piece and update display
protected void switchToSearchDestroy(){
  if (getGridMode()!=GM_CHOOSE_COL) return;   //only available if in select column mode
  setChoosePiece(new SearchDestroy(gameStatus.getCurrPlayer()));
  setChanged();
  notifyObservers(getChoosePiece());
  }

//Switch to Leveller piece and update display
protected void switchToLeveller(){
  if (getGridMode()!=GM_CHOOSE_COL) return;   //only available if in select column mode
  setChoosePiece(new Leveller(gameStatus.getCurrPlayer()));
  setChanged();
  notifyObservers(getChoosePiece());
  }

//Switch to Magnet piece and update display
protected void switchToMagnet(){
  if (getGridMode()!=GM_CHOOSE_COL) return;   //only available if in select column mode
  setChoosePiece(new Magnet(gameStatus.getCurrPlayer()));
  setChanged();
  notifyObservers(getChoosePiece());
  }

//Switch to Magnet piece and update display
protected void switchToSniper(){
  if (getGridMode()!=GM_CHOOSE_COL) return;   //only available if in select column mode
  setChoosePiece(new Sniper(gameStatus.getCurrPlayer()));
  setChanged();
  notifyObservers(getChoosePiece());
  }

//Setup Race
protected void setupRace(int player) {
   JOptionPane.showMessageDialog(gameStatus.getMainFrame(),((player==P1)?"Blue Team ":"Red Team ") + ", it's time to gamble at \" The World Races\"...");
  //Set Game Mode
  setlevelMode(LM_RACE);
  setProcessing(true);                              //Dis-Allow Other Processes
  clearGrid();                                      //Clear Grid of all pieces
  setGridMode(GM_RACE);                             //set to racing mode

  //Store current grid and set to race grid
  storePreRaceRows=gameStatus.getClaspProperties().getGridRows();
  storePreRaceCols=gameStatus.getClaspProperties().getGridCols();
  gameStatus.getMainFrame().setGridShape(MainFrame.GRID_RACE);         //Change grid Shape if using random grids
  needRaceRestore=true;                                                 //Must restore grid next time

  RaceActions raceActions=new RaceActions(this,gameStatus,player);
  raceActions.setup();
}

} //End Of Class
