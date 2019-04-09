## Structure
### Model

The model package is in charge of providing the engine with the rules of chess and providing it with a list of moves from which
to choose the best ones. The classes contained within the package are mostly self explanatory, with different types of pieces 
providing their own types of movement, the code for which is contained in the Move class. The board builds the playing field, 
which is a two dimensional array of Squares, which in turn mainly provide their location and contain a Piece, and so on. Diagrams
coming soon to a github page near you.

### Engine

The engine package contains the logic for the chess engine itself, or how the engine chooses what it considers the best one 
from a list of available moves. Details on implementation of this to be added later.

### Connection

The connection package handles connecting the application to the XBoard app. Details on the implementation of this to be added
later.
