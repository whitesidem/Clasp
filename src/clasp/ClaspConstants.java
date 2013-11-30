package clasp;

/**
 * Title:        Clasp Version 2
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public interface ClaspConstants {
  //Grid Mode
  public static final int GM_CHOOSE_COL = 0;
  public static final int GM_PERFORM_ACTION = 1;
  public static final int GM_SHOW_WIN = 2;
  public static final int GM_RACE = 3;

 //Image Modes
  public static final int IMG_STD = 0;
  public static final int IMG_SHOW_WIN = 1;


 //Players
  public static final int P1 = 0;   //used as Array offset so dont change
  public static final int P2 = 1;   //used as Array offset so dont change
  public static final int P0 = 2;

  //State
  public static final int STILL = 0;
  public static final int ANIM_MOVE_DOWN = 1;
  public static final int WINNING_LINE = 2;
  public static final int ANIM_MOVE_ACROSS = 3;

  public final static int DROP_SPEED = 1;               //Milliseconds delay
  public final static int ANIM_MOVE_DOWN_CHUNKS = 25;    //Moves down pixels
  public final static int ANIM_MOVE_ACROSS_CHUNKS = 15;  //Moves across pixels

  public final static int ONE_SECOND = 1000;
  public final static int COL_SPACER = 55;
  public final static int ROW_SPACER = 50;

  public final static int CLASP_CENTER = 0;
  public final static int CLASP_RIGHT = 1;
  public final static int CLASP_LEFT = 2;


  public final static int CLASP_LINE_SCORE=40;      //Score per X across
  public final static long CLASP_WIN_LIMIT=7500L;   //Win Limit for a board
  public final static int JOKER_FOUND_MULTI=5;      //Joker Found Multiplier
  public final static int JOKER_WIN_MULTI=10;       //Joker Win Multiplier


  //Grid Level Modes
  public static int LM_STD = 0;
  public static int LM_LANDSCAPE = 1;
  public static int LM_RAND_DROPS = 2;
  public static int LM_RACE = 3;

}