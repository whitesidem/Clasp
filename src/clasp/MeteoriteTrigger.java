package clasp;

/**
 * Title:        Clasp Version 2
 * Description:  Meteorite Trigger
 *               Falls randomly - if collected causes storm that
 *               destroys all top items of opposite colour
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */
import java.awt.*;  //Graphics
import javax.swing.*; //Jcomponent

public class MeteoriteTrigger extends ClaspBasePiece {

static private ImageIcon imgMTrigger;
static protected Image mTriggerImage;

//Static Initialisation block - ran only once.
static {
  imgMTrigger = new ImageIcon(MeteoriteTrigger.class.getResource("MeteoriteTrigger.gif"));
  mTriggerImage = imgMTrigger.getImage();
  }

  public MeteoriteTrigger() {
    super();
    //Setup Player
    this.player=P0;
  }

 //Returns the Player One Image
  protected Image getP0Image(){
    return mTriggerImage;
  }


}