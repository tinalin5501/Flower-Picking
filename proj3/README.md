# Build Your Own World Design Document

**Partner 1:** Samantha Oh

**Partner 2:** Tina Lin

## Classes and Data Structures
MapGenerator: Used to create the map using the seed input found in the Engine class. 
Avatar: Used to move the avatar around based on the seed input and map.
SavedFile: Used to create, write, and read text files to save game progress.
Serialization: makes the name, seed, and direction serializable
keyboard: Used for interactWithKeyboard in Engine
TETile[][] map: 2D array of TeTiles, the first index corresponds to an x-value, second index corresponds to a y-value
int[][] rooms: 2D array of rooms, where each room index points at an array containing xmin, ymin, xmax, ymax
int[][] roomCenters: 2D array of integers, first index = x and second index = y, (x, y) = center of room

## Algorithms
MapGenerator: class that contains all the methods to generate a pseudorandom world
makeWorld(): returns the TETILE[][] map
makeRooms(): creates a random number of rooms 
rectangleRoom(): puts rectangular room onto the map 
randomRec(): creates random x, y, width, height for the new rectangle
adjust(): creates new random variables if current one will overlap with other rooms
makeHallways(): connects every room
connect(): draws the hallway to connect the rooms
placeFlowers(): places a random number of flowers in random locations
getFlowers(): method to make accessing flower numbers easy
hoverTile(): returns a string describing what tile the mouse hovers over

SavedFile: 
createNew(): creates a new txt file with the direction and seed 
save(): saves direction and seed into created file with name 
loadSeed(): returns the seed 
load(): returns the directions 
hasFile(): checks if file exists 
overwrite(): if all save slots are full and then user chooses to overwrite an existing file
name(): returns the names of existing save files
nameLength(): returns the number of existing save files 

Serialization: 
getSeed(): returns the seed 
getDirection(): returns the directions

keyboard: 
startScreen(): main menu where the user can decide to start a new game, load, or quit 
startGame(): creates a new game, load a previous game, or quits 
lore(): 
endingScreen(): displays the winning screen or the exit screen 
nameScreen(): asks the user for a name
quit(): checks if the user typed :q 
save(): calls the save function in SavedFile
instructions(): displays the instruction screen
render(): renders the map 

Avatar:
moveAvatar(): moves avatar for input string
moveAvatarKey(): tells the avatar what direction to move in
move(): moves the avatar if it can move in that direction
undoGame(): returns to the main game
miniGame(): starts a mini game to heal the avatar from the fire
addWater(): adds a random number of water tiles in random locations in the mini game
getFlower(): method to make accessing flower numbers easy
getLives(): method to make accessing number of lives easy
isAlt(): method that returns boolean describing whether an alternate game is being played or not
getWater(): method to make accessing water numbers easy

## Persistence
The SavedFile class would be called to save and load files. When saving, the information that
is needed to be saved will first be serialized in the Serialization class. Then a txt file will    
be created with the name of the save file. This txt file contains the seed, direction, and flowers 
the user has before quitting. When loading, it will read the txt file and return the three values 
stated above. 

