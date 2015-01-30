AI Project 1
Micah Brown and Ford Holland

Intructions for code:
All code is written in java.

To run:
-GenerateBoards.java contains the commands to generate a bunch of random boards, and then write those boards to .txt files depending on how many moves they are away from a solution. It solves a board, and then performs the A* search and determines how many edges away the solution is from the starting board. It then writes that board to <num_moves>.txt.
-Main.java solves 100 boards for solution length d, which is a local variable that is set at the top of the program. It then outputs to the console the average nodes generated.
-AStar.java is the class that represents the board.
-IDS_recurse.java contains the commands to run Main.java, except that this only runs the IDS search (using recursion).

