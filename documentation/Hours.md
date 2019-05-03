## Week 1
|Date |Duration|Task|
|-----|--------|----|
|13.3.|2h      |Researching possible topics for the project and their implementations|
|14.3.|2h      | 〃|
|14.3.|1h      |Writing weekly report, starting on documentation and setting up git repository|
|Total|5h      ||

## Week 2   
|Date |Duration|Task|
|-----|--------|----|
|19.3.|1h      |Preliminary design research|
|21.3.|4h      |Searching for possible Chess implementations to work with|
|22.3.|1h      |Configuring XBoard, and researching its implementation|
|22.3.|0.3h    |Writing weekly report|
|22.3.|3h      |Writing internal model of Chess for the engine, still in progress|
|Total|9.3h     ||
## Week 3
|Date |Duration|Task|
|-----|--------|----|
|27.3.|1h      |Additional research into XBoard implementation|
|28.3.|1h      |Configuring gradle for the project|
|29.3.|1h      |Additional general code for internal Chess model|
|29.3.|2h      |Writing basic movement implementation for Rooks|
|29.3.|1h      |Tests and bugfixes for Rook movement|
|29.3.|1h      |Starting work on movement implementation for other pieces|
|29.3.|0.3h    |Writing weekly report|
|29.3.|1h      |Basic movement for Pawns with tests (no en passant or promotion)|
|Total|8.3h     ||
## Week 4

|Date|Duration|Task|
|----|--------|----|
|2.4.|0.5h    |Slight refactor for Rook movement|
|2.4.|2h      |Movement for Bishop and Knight|
|2.4.|0.5h    |Tests for Bishop movement|
|3.4.|0.5h    |Pawn promotion|
|3.4.|0.5h    |Movement for Queen|
|3.4.|0.5h    |Movement for King (without implementing method isInCheck())|
|4.4.|1.5h    |Player class added to provide all available moves|
|4.4.|1h      |Bugfixes for various pieces' movement and Player class|
|5.4.|0.5h    |Bugfix for pawn promotion|
|5.4.|1h      |Code cleanup (moved boardstate and current square to private variables for the pieces)|
|5.4.|0.5h    |Bugfix for pawn opening move and a test to sure it keeps working|
|5.4.|1h      |Initial implementation for King isInCheck|
|5.4.|1.5h    |New tests for Queen, King, Knight|
|5.4.|0.5h    |New tests for Pawn|
|5.4.|1h      |More bugfixing and tests|
|5.4.|1h      |Documentation stuff|
|Total|14h    ||
## Week 5

|Date |Duration|Task|
|-----|--------|----|
|9.4. |0.3h    |Minor fix in King isInCheck()|
|10.4.|3h      |Method legalMoves() for player to make sure moves dont leave king in check|
|11.4.|2h      |Fixing bugs throughout the project. Moves *should* always be legal now.|
|11.4.|0.5h    |Additional tests for King as part of bugfixing|
|11.4.|1h      |Planning minmax implementation|
|12.4.|0.5h    |Prep work for minmax (Pieces given scores)|
|12.4.|2h      |Program restructuring|
|12.4.|1.5h    |Locating and fixing hard to find bug|
|12.4.|1h      |Tests for Player class, fixing test in Pawn and a bug in pawn class itself|
|12.4.|0.6h    |Documentation stuff|
|Total|12.4h||

## Week 6
|Date |Duration|Task|
|-----|--------|----|
|13.4.|4.5h    |Building and testing HighestScore (basically just checks own moves and opponents followup)|
|13.4.|2h      |Initial implementation for minmax|
|14.4.|4h      |Bug hunting and tests, mostly for minmax|
|15.4.|3h      |More bug hunting for Minmax|
|16.4.|2.5h    |Bug hunting and testing Minmax, adding possibility of dynamically changing maximum depth|
|16.4.|3h      |(Finally) added castling, fixed a bug where updated piece values were not used in minmax, more tests|
|17.4.|1h      |Tinkering with piece values to fix strange behavior|
|17.4.|0.5h    |Found and fixed bug in Knight movement, added test for it|
|17.4.|1.5h    |Implemented minmaxAB and added tests for it (mostly same scenarios as minmax and some performance tests)|
|17.4.|1h      |Changing minmax and minmaxAB to check for mates on final depth, more tests|
|17.4.|1h      |New test scenario for minmaxAB|
|20.4.|2h      |Human.java class added to make playing vs engine possible, new tests|
|22.4.|2h      |Basic move ordering implemented (moves with captures are looked at first)|
|26.4.|0.5h    |Additional level to move ordering|
|26.4.|2.5h    |Documentation stuff|
|Total|31h||

## Week 7
|Date |Duration|Task|
|-----|--------|----|
|28.4 |4.5h    |Work on XBoard connection|
|29.4.|4h      |Debugging bizarre issues with XBoard|
|30.4.|1h      |Close to locating problem, appears to be caused by something in Player.getLegalMoves() but more work to do still..|
|1.5. |2h      |Problem located and fixed, engine now works with XBoard|
|1.5. |1.5h    |Engine can play as either side on XBoard, Javadoc added|
|3.5. |5h      |Work on optimizing engine performance. Many bugs remain.|
|3.5. |2h      |Attempted to fix bugs, but just kept discovering more. Resetting to last stable build and working from there|
