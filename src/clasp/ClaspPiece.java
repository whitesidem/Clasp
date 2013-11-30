package clasp;

import java.awt.*;  //Graphics
import javax.swing.*; //Image Icon

/**
 * Title:        Clasp Standard Piece
 * Description:  Defines Standard Piece - can be player one or player 2.
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class ClaspPiece extends ClaspBasePiece implements ClaspConstants{

boolean metoriteTriggered=false;

  public ClaspPiece(int player) {
    super();
    //Setup Player
    this.player=player;
    metoriteTriggered=false;
  }

protected boolean isMeteoriteTriggered() {
  return metoriteTriggered;
}
protected void setMeteoriteTriggered(boolean value) {
  metoriteTriggered=value;
}


}