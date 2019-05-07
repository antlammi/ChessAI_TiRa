
# ChessAI
The application is a chess engine using XBoard.

## Methodology 
The AI searches through a minimax game tree using an Alpha-Beta pruning implementation. Individual pieces have a base value based on their piece type. Additionally, pieces have their values updated based on their location. Pawns are valued higher based on how close they are to Queening and other pieces are valued based on how many squares they are legally allowed to move to.

### Theoretical Time Complexity For MinmaxAB([source](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning))   
    
*Worst-case:O(b<sup>d</sup>)*       
*Best-case:O(√b<sup>d</sup>)*

Where b is the average number of moves per level in the tree and d is its depth.

Thanks to move ordering being implemented, the average performance for the algorithm should be slightly closer to best-case than worst-case, but an exact estimate is difficult to provide. 

