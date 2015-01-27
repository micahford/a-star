import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class AStar {
	final static int size = 3;
	
	private int[][] board;
	
	
	public AStar(){
		board = new int[size][size];

		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0; i<size*size; i ++){l.add(i);}
		Collections.shuffle(l, new Random(4));
		
		for(int i = 0; i< size; i++){
			for(int j = 0; j < size; j++){
				board[i][j] = l.get(i*size+j);
			}
		}
	}
	
	//swaps the number n with the blank space (number 0)
	public void moveSquare(int n){
		int rowNumber = 0; int colNumber = 0;
		int rowZero = 0; int colZero = 0;
		for(int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				if(board[i][j]==0){
					rowZero=i;
					colZero=j;
				}
				else if (board[i][j]==n){
					rowNumber = i;
					colNumber = j;
				}
			}
		}
		board[rowZero][colZero] = n;
		board[rowNumber][colNumber] = 0;
		
		//return a;
	}
	
	//returns boolean whether the puzzle is solvable
	public boolean isSolvable() {
		int[] boardInOrder = this.orderBoard();
		int N = 0;
		int e = 0;
		for (int i = 0; i < boardInOrder.length; i++){
			int n = 0;
			if ((boardInOrder[i]!=0)||(boardInOrder[i]!=1)){
				for(int j = i; j<boardInOrder.length;j++){
					if(boardInOrder[i]>boardInOrder[j]){
						if(boardInOrder[j]!= 0) n++;
					}
				}
				System.out.print(n + " ");
				N = N + n;

			}
			else if (boardInOrder[i]==0) {e = i+1;}

		}
		if((N+e)%2==0) return true;
		else return false;
	}
	
	//takes the current board (a 2D array and returns a 1D array of the numbers written in order)
	private int[] orderBoard() {
		int[] boardInOrder = new int[9];
		int k = 0;
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				boardInOrder[k] = board[i][j];
				k++;
			}
		}
		return boardInOrder;
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
