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
	public AStar moveSquare(int n, AStar a){
		int rowNumber = 0; int colNumber = 0;
		int rowZero = 0; int colZero = 0;
		
		for(int i = 0; i < a.size; i++){
			for (int j = 0; j < a.size; j++){
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
		board[rowZero][colZero] = n;
		board[rowNumber][colNumber] = 0;
		return a;
	}
	
	//returns list containing coordinates of the blank space
	private int[] blankLoc(AStar a){
		int[] location = null;
		for (int i=0; i<a.size; i++){
			for (int j=0; j<a.size; j++){
				if (a.board[i][j]==0){
					location[0]=i;
					location[1]=j;
				}
			}
		}
		return location;
	}
	
	
	
	//generates successor states
	public List<AStar> possMoves(AStar a){
		List<AStar> l= new ArrayList<AStar>();
		int [] location = a.blankLoc(a);
		int r=location[0];
		int c=location[1];
		
		for (int i=0; i<4; i++){
			
		}
		return l;
	}
	
	//checks if a location is out of bounds
	private boolean isOutOfBounds(int r, int c, AStar a){
		boolean isOutOfBounds=false;
		if ((r+1>a.size) || (r-1<a.size)){
			isOutOfBounds=true;
		}
		else if ((c+1>a.size) || (c-1<a.size)){
			isOutOfBounds=true;
		}
		return isOutOfBounds;
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
