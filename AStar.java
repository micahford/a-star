import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AStar {
	final static int size = 3;
	
	private int[][] board;
	
	
	public AStar(){
		board = new int[size][size];

		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0; i<size*size; i ++){l.add(i);}
		Collections.shuffle(l);
		
		for(int i = 0; i< size; i++){
			for(int j = 0; j < size; j++){
				board[i][j] = l.get(i*size+j);
			}
		}
	}
	
	//swaps the number n with the blank space (number 0)
	public AStar moveSquare(int n, AStar a){
		int rowNumber = 0; int colNumber = 0;
		int rowZero = 0; int colZero = 0;
		for(int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				if(a.board[i][j]==0){
					rowZero=i;
					colZero=j;
				}
				else if (a.board[i][j]==n){
					rowNumber = i;
					colNumber = j;
				}
			}
		}
		a.board[rowZero][colZero] = n;
		a.board[rowNumber][colNumber] = 0;
		
		return a;
	}
	
	//prints off the current board.
 	public String toString(){
 		String toReturn = "";
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				if(board[i][j]==0) toReturn += " ";
				else toReturn += board[i][j];
			}
			toReturn += "\n";
		}
		return toReturn;
	}
}

