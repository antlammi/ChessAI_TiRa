
# ChessAI
The application is a chess engine using XBoard.

## Methodology 
The AI will search through a minimax game tree using an Alpha-Beta pruning implementation for optimization.   
### Theoretical Time Complexity ([source](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning))   
    
*Best-case:O(b<sup>d</sup>)*       
*Worst-case:O(√b<sup>d</sup>)*

Where b is the branching factor of the tree and d is its depth.
