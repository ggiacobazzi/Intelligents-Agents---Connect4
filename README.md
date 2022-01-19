# Intelligents-Agents---Connect4-README
Programming assignment for the Complex System course at UP FAMNIT focused on the implementation of intelligent agents in the Connect4 game made using Java


## 1 Instructions

### 1.1 To compile and run

- Unzip the archive with all the files in a new folder
- Go into the folder using a terminal

```
cd "name" change directory
ls / dir to list the contents of the folder (Linux OS/Windows OS respectively)
```
- Run the following commands from a terminal with Java 8 (and Java set
  as an environmental variable if run from a Windows OS) installed on the
  computer:

```
javac *.java
```
```
jar cfm Connect4.jar META-INF/MANIFEST.MF *.class
```
```
java -jar Connect4.jar
```
In case there are problems with the compilation it is possible to use directly the
precompiled .jar file situated in the ./out folder

### 1.2 To play

- Use the ”Players” menu to select the mode of the game and then click
  ”New” in the ”File” menu to initialize the environment. After that click
  on the ”Start” button situated at the bottom of the window to start the
  game.
  - If a ”Human” mode is selected, the moves button will be activated
  after clicking the start button
  - AI will play alone after clicking on the start button or after a human
  move.
- Using the ”Options” menu it is possible to run simulations of AI games
  and save the output in a .txt file located in the main folder. The default
  number of simulations is 100.


1.2.1 Miscellaneous

- The first player plays Red tokens, while the second one Yellow ones.
- At the bottom of the window it is possible to track the number of the
  respective Players’ moves and the time elapsed to make a move, in case
  an AI is playing.
- The simulations process between the agents are not immediate, so it is
  necessary to wait a little bit, especially when using a pruned minimax
  agent.

1.2.2 Problems

- Sometimes the new game action could get buggy: in that case it is sug-
  gested to restart the application.
- If the simulation process completion window does not appear after a while
  it is suggested to restart the application and retry the operation. It is
  possible to check the simulation progression from the terminal.
- In ”AI vs AI” modes, the tokens are displayed in the grid after the game
  has finished.



