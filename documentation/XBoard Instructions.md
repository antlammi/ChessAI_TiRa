1. Download and install [XBoard](https://www.gnu.org/software/xboard/) for Unix systems or [WinBoard](http://www.open-aurec.com/wbforum/viewtopic.php?t=51528) for Windows    
2. Download the [latest version](https://github.com/antlammi/ChessAI_TiRa/releases/download/1.0/kufbot.jar)
3. Open XBoard app
4. Select "Engine/Edit Engine List..." and add the following line
  ```
    "kufbot" -fcp "java -jar <location of jar file>" 

  ```
  5. Select "Engine/Load New 1st Engine..." and choose the engine.
  6. You're all set. If you want to play as White, just make an opening move. If you want to play as Black, select "File/New Game" and "Mode/Engine White"

The implementation of the XBoard connection is very basic, so it is recommended you repeat steps 5 or 6 each time you want to play a game.
