## Unit Testing

Currently Unit Test coverage is around 83% with most of the classes in the model package being tested more or less thoroughly.

The tests for all the pieces follow the same basic pattern. Either the opening position or a specifically boardstate is setup, followed by comparing the results of the getLegalMoves() method to a correct list of string representations of moves. Where the color of the piece is relevant (mainly the Pawn class) symmetrical tests are done for both white and black pieces.     

Some classes could benefit from more testing but the main goal is to prevent previously found issues from reoccurring later.    
### Test Coverage
![Image](https://github.com/antlammi/ChessAI_TiRa/blob/master/documentation/Test%20Coverage.png)
![Image](https://github.com/antlammi/ChessAI_TiRa/blob/master/documentation/Test%20Coverage%202.png)
