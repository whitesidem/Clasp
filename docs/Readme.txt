  Here's a breakdown of the code:

  ClaspApplication: is simply the kickoff point, this contains "void main()" and creates all the required resident classes:

    ClaspGameStatus: Always resident. Keeps track of the overall games status status inc. info of both players (score, grids won etc) and game level reached and current size of grid etc.
                   Points to a number of classes that store the actual data inc (ClaspProperties and ClaspPlayer)

    ClaspConstants:  Stores any global constants that are shared throughout the objects. Including Win Amounts and animation speeds.  

    MainFrame:       Always Resident. This is the main window displayed, that contains the menus, side panel and holds the grid display and checks for left/right etc key presses.

    ClaspView:       Always Resident. This sits in a panel within MainFrame and does all the displaying of the grid graphics, piece graphis and animations etc.

    ClaspModel:      Always Resident. This performs all the main logic for the game and also stores the grid data (a array to store all the pieces on the grid).
                   Controls selecting the column and dropping the piece. Checking for winning lines and calculating the scores.

    ClaspGridActions: Always Resident: This contains extra logic for the grid. Including all special events like landscape starts and random drop of special pieces events. 
                   Controls what the weapons do once they have landed. Controls any special events that occur as a piece is dropping. 


  The remaining classes are all supporting pieces and dialogs...

  Supporting Pieces:

     ClaspBasePiece: This is the class that ALL game pieces inherit from. Holds the basics like giving the correct image depending on the player and storing the pieces position within the grid.

     	Examples of Non-Player pieces:  
		Cash piece
        	Egg piece
   		MeteoriteTrigger piece 
		Teleport piece
		Danger piece

     ClaspPiece: This is the class that is inherited from if a piece is a standard piece or weapon piece. Thus this piece is always owned by Player 1 or 2.

     	Examples of Player pieces:  
		Magnet piece
                Sniper piece
                Joker piece
                SearchDestroy piece
                Assassin piece
                Leveller piece  

     Which piece is selected is controlled by MainFrame, which then puts the piece on the grid via ClaspModel. 
     ClaspModel controls the dropping of the piece on the grid and ClaspGridActions performs all the special actions that occurr as the piece is dropping and when it has landed, depending on the piece type.
     ClaspView displays all the piece graphics to the screen as all this is occurring.
     Once the piece has landed, ClaspModel will change the control over to the other player.  

  Dialogs:

      Splash: The opening splash screen.
      ClaspProperties: The dialog for selecting gris size etc. This streams itself as a file.
      WeaponInfo: Displays how to use the weapons
      WeaponBuyDlg: Controls the buying of weapons and performing new research.
      MainFrame_AboutBox: The about box.
      ViewPlayerInfo: Always shown wihin MainFrame - displays each players scores.       


==================================
Coding the new game pieces.

Adding new pieces depends on whether the new piece is a selectable weapon piece or a random special item.

Special Items:
The random special items are the easiest. Simply add a new class inheriting from ClaspBasePiece.
The code within all these are almost identical, so copy an existing special piece class.
First an image is required - this is loaded from src/clasp file.
The GetP0Image should be coded to return this image.

Next, claspgridactions needs updating (RandomDrops function) to randomly drop this item.
Then ClaspGridActions needs updating to perform whatever logic occurrs when a piece (either lands on this or drops by the side of it)
Or maybe the piece performs a special event when it itself lands.

Weapon Items:
Just as simple to add, but far more code to add...
First inherit from ClaspPiece and update the loading and displaying of the image for player 1 and 2.
Update MainFrame so that it is assigned a number to press to create this piece.
Update ClaspModel so that it can be assigned as the selectPiece to use.
Update ClaspGridActions in the same way as for the Special Items above so it performs events when landed or another piece lands on it.
Update ClaspPlayer and ClaspGame status so that the total amount owned is kept as data per player and research info is added.
Update WeaponBuyDlg dialog so that this piece can be researched and bought.
Update WeaponInfo  dialog so that info on how to use this weapon is displayed
Update MainFrame to display the total amount of this weapon bought.
Update MainFrame so that the total weapons owned is decreased as it is used.
Update SaveGame so status is saved and loaded correctely.

The thing that takes the longest is drawing the images and adding all the code for buying more of this weapon.
The actual images arnt too much of a problem if the look is kept to a simple variation of a circle.
The decision behind new special piece design is based on - will this get in the way of tactics and just turn the grid to mayhem or will it add interest or mild mayhem the rare times it drops.
The decision behind new weapon piece design is based on - is this weapon too powerfull and will it give the player too much of an advantage. note the leveller is powerfull but it is very expensive and you can only ever own 2 at a time.

I use Macromedia Freehand to draw all the images and export them as gifs.
Iue http://www.btinternet.com/%7Efireballxl5/space/planets/index.htm website for free background images






 