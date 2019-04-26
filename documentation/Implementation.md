## Structure
### Model

The model package is in charge of providing the engine with the rules of chess and providing it with a list of moves from which
to choose the best ones. The classes contained within the package are mostly self explanatory, with different types of pieces 
providing their own types of movement, the code for which is contained in the Move class. The board builds the playing field, 
which is a two dimensional array of Squares, which in turn mainly provide their location and contain a Piece, and so on.

### Engine

The engine package contains the logic for the chess engine itself, or how the engine chooses what it considers the best one 
from a list of available moves. It has a few different classes, each of which is its own implementation of how to choose the best move. For now among these is the class enabling a human player to manually input moves.

### Connection

The connection package handles connecting the application to the XBoard app. Details on the implementation of this to be added
later.


### MinmaxAB
Briefly the core idea of the algorithm: The best moves for both players are evaluated until the maximum depth is reached or the game ends. Four values are maintained: maxScore, minScore, alpha and beta. Individual positions are evaluated by comparing the value of both players pieces (or in the case the game ends, a move's score is set to 1000000 (mate), or 0.0 (stalemate) as a special case)

Variable maxScore refers to the best score available for the player initiating the search. In this implementation it is received through the move class as an actual move needs to be provided to the game.   

Variable minScore refers to the best score available to the opposing player, again provided by the move itself.     

Variable alpha is the best score the player initiating the search is assured of based on moves already looked at.
Variable beta is the best score the opponent is assured of based on moves already looked at.    

Using alpha and beta some subtrees can be completely ignored, as a better move is guaranteed to exist.    


## Example

Here I will provide a slightly simplified example of how the Engine (specifically MinmaxAB) chooses the best move. 
On to the example:    
- the Game.java class calls getMove() in MinmaxAB  


- minimaxAB(this.state, 0, this.maxplayer, null, -Double.MAX_VALUE, Double.MAX_VALUE) is called in MinmaxAB to start the algorithm  
- If the previous move was not null (initial call) the previous move is executed
- minimaxAB calls getLegalMoves() in Player to receive an array with the available moves in the current position
  - getLegalMoves() in Player calls getLegalMoves() for all of its pieces and combines the results into an array
    - Each of the moves in the pieces are first constructed (a call to the Move class) and compiled into an array by getMoves()
    - Then the legality of these moves is evaluated for the current position and non-legal moves are removed based on the movement of         the given piece by getLegalMoves in the piece
  - getLegalMoves() in Player further evaluates the moves, removing moves that would leave the king in check
  - getLegalMoves() in Player sorts the moves based on whether the moves include a capture of a minor/major piece, a pawn, or no
  capture and then returns the array
- if the array is empty, minimaxAB checks to see if the king is checked in the current position using checkForMate()
- if checkForMate() is true, the previous player's score is set to 1000000 to signify a mate in the position and the previous 
move is returned 
- If it is false, the previous player's score is set to 0.0 and the previous move is returned
- if current depth equals maxdepth the latest move is returned
- minimaxAB now iterates on the array of moves provided by getLegalMoves()
- minimaxAB(copystate, depth + 1, minimizing, currentMove, alpha, beta) is called for each move in the array (minimizing 
refers to the opponent and copystate is a copy of the current boardstate, the minimizing player's movescore is a separate 
variable called minscore)
- the previous steps are repeated until maximum depth is reached or the game ends(mate or stalemate)
- for the move returned by minimaxAB.getPlayer().getScore() is called to determine the score of the move
- if the score is > maxScore (or < minScore for the opponent's moves) minimax saves the move in a variable 
- Next minmaxAB checks to see if moveScore > alpha, in which case the value of alpha is now moveScore, followed by a check to 
see if alpha > beta the loop is broken and no further moves need to be investigated
- The best move is returned by minimaxAB
        
