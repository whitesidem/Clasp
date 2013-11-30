package clasp;

/**
 * Title:        Clasp Propertie Storage
 * Description:  Stores the Properties for the game and gives
 * methods for storing and loading thos properties to XML
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

import java.io.*;


public class ClaspProperties implements Cloneable, java.io.Serializable {
    public final static int GRID_COLS = 9;
    public final static int GRID_ROWS = 8;
    public final static int CLASP_FULL_LINE=4;      //Number across to get

    private static final String file = "Clasp.bin";
    private int gridRows;
    private int gridCols;
    private int fullLine;
    private boolean randGrid;

  public ClaspProperties() {
    setDefaults();
  }

protected void setDefaults(){
   gridRows = GRID_ROWS;
   gridCols = GRID_COLS;
   fullLine = CLASP_FULL_LINE;
   randGrid = false;       //Random Grid Sizes
  }

public Object clone() throws CloneNotSupportedException {
  return super.clone();
}

protected int getGridRows(){
  return(gridRows);
}
protected void setGridRows(int rows){
  gridRows=rows;;
}
protected int getGridCols(){
  return(gridCols);
}
protected void setGridCols(int cols){
  gridCols=cols;
}
protected int getFullLine(){
  return(fullLine);
}
protected void setFullLine(int fullLine){
  this.fullLine=fullLine;
}

//Stream Object to Property File
public static void writeObject(ClaspProperties props){
   try
    {
      // Create the object output stream
      ObjectOutputStream objectOut =
                    new ObjectOutputStream(
                    new BufferedOutputStream(
                    new FileOutputStream(file)));
      objectOut.writeObject(props);            // Write first object
      objectOut.flush();                       //Force full write to take place now
      objectOut.close();                         // Close the output stream
    }
    catch(NotSerializableException e)
    {
      System.err.println(e);
    }
    catch(InvalidClassException e)
    {
      System.err.println(e);
    }
    catch(IOException e)
    {
      System.err.println(e);
    }
}

//Read Property File into Object
public static ClaspProperties readObject(){
    ClaspProperties props=null;
    try
    {
      ObjectInputStream objectIn =
                     new ObjectInputStream(
                     new BufferedInputStream(
                     new FileInputStream(file)));

      props = (ClaspProperties)objectIn.readObject();
      objectIn.close();                          // Close the input stream
    }
    catch(IOException e)
    {
      return null;
    }
    catch(ClassNotFoundException e)
    {
      System.err.println(e);
    }
    return props;
}
  public void setRandGrid(boolean randGrid) {
    this.randGrid = randGrid;
  }
  public boolean isRandGrid() {
    return randGrid;
  }



}

