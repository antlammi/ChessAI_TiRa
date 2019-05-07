## Unit Testing
### Overall Test Coverage

![Image](https://github.com/antlammi/ChessAI_TiRa/blob/master/documentation/Test%20Coverage%206.png)

### Model
Currently unit test coverage is around 87% (for branches, 92% for instructions) with most of the classes in the model package being thoroughly tested.

The tests for all the Pieces follow the same basic pattern. Either the opening position or a specifically setup boardstate is prepared, followed by comparing the results of the getLegalMoves() method to a correct list of string representations of moves. Where the color of the piece is relevant (mainly the Pawn class) symmetrical tests are done for both white and black pieces.     
#### Coverage for Model
![Image](https://github.com/antlammi/ChessAI_TiRa/blob/master/documentation/Test%20Coverage%203.png)

### Engine
Currently unit test coverage is around 57% (for branches, 69% for instructions). This number is however, not very representative. Minmax and MinmaxAB are tested very thoroughly, as is HighestScore. The coverage is low mostly due to the lack of tests for the Human class. While some tests for the class might make sense, there is a chance it will be made completely obsolete by the time the project is complete, so testing it now seems of questionable value.

The tests for the Engine classes are built with the following pattern: A scenario is setup (typically one that caused issues at some point in development). The Engine is asked for the next move, and an assertion is made. There are essentially three types of assertions used. In the first, the string representation of the move is asserted not to be a predetermined bad move. Typically these are horrible blunders like hanging a Queen for no reason. In the second, the move is asserted to be a specific good move (typically a mate in X or a material gain). Finally, in the third, the assertion is simply assert(true), as the purpose of these tests is to see if an exception is thrown (again in scenarios where the exception was thrown previously).

Some of the tests for Minmax and MinmaxAB are also ran at different depths with different assertions made in some cases.

Finally, there are some tests in MinmaxAB that compare the speed of finding moves with MinmaxAB to the speed at which Minmax finds the move. There should be no scenario where MinmaxAB is slower.


#### Coverage for Engine
![Image](https://github.com/antlammi/ChessAI_TiRa/blob/master/documentation/Test%20Coverage%205.png)

More or less the only instructions not covered in Minmax and MinmaxAB are to do with the section of code that dynamically changes the depth based on how long it took to find the move. Pictured below.
![Image](https://github.com/antlammi/ChessAI_TiRa/blob/master/documentation/Test%20Coverage%204.png)

### Connection

The connection package does not have any automated tests, but has been sufficiently tested manually. Most of the functionality is rather trivial, and should not require much change under any circumstances, making the value of automated testing somewhat questionable.
