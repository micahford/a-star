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
	
	public AStar(AStar a){
		int[] order = a.orderBoard();
		AStar a2 = new AStar(order);
	}
	
	public AStar(int[] order){
		board = new int[size][size];
		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0; i<order.length; i++){
			l.add(order[i]);
		}
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
		a.board[rowZero][colZero] = n;
		a.board[rowNumber][colNumber] = 0;
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
		int [] currentBoard = a.orderBoard();
		if (!isOutOfBounds(r-1,c,a)){
			AStar move1 = new AStar(currentBoard);
			System.out.println(move1.board[r-1][c]);
			move1=moveSquare(move1.board[r-1][c],move1);
			l.add(move1);
		}
		if (!isOutOfBounds(r,c+1,a)){
			AStar move2 = new AStar(currentBoard);
			System.out.println(move2.board[r][c+1]);
			move2=moveSquare(move2.board[r][c+1],move2);
			l.add(move2);
		}
		if (!isOutOfBounds(r+1,c,a)){
			AStar move3 = new AStar(currentBoard);
			move3=moveSquare(move3.board[r+1][c],move3);
			l.add(move3);
		}
		if (!isOutOfBounds(r,c-1,a)){
			System.out.println("here4");
			AStar move4 = new AStar(currentBoard);
			move4=moveSquare(move4.board[r][c-1],move4);
			l.add(move4);
		}
		//
	//	for (int j=0; j<l.size(); j++){
	//		System.out.println(l.get(j));
	//	}
		//
		return l;
	}
	
	//checks if a location is out of bounds
	public boolean isOutOfBounds(int r, int c, AStar a){
		boolean isOutOfBounds=false;
		if ((r>a.size) || (r<0)){
			isOutOfBounds=true;
		}
		else if ((c>a.size) || (c<0)){
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
	
		//this returns the h1 value for a given board, as defined by AIMA (the number of misplaced tiles)
	public int heuristic1(){
		int[] CORRECT_ORDER = {0,1,2,3,4,5,6,7,8,9};
		int[] current_order = this.orderBoard();
		int n = 0; //number out of order.
		for(int i = 0;i < current_order.length; i++){
			if(current_order[i]!=0){ //don't check if blank space is in order.
				if(current_order[i]!=CORRECT_ORDER[i]){
					n++;
				}
			}
		}


		return n;
	}
	//finds the row and column for correct location of n.
	private int[] rowAndCol(int n){
		int[] rowCol = new int[2];
		int[][] CORRECT_BOARD = {{0,1,2},{3,4,5},{6,7,8}};
		for(int i =0; i < size; i ++){
			for(int j = 0; j < size; j++){
				if(CORRECT_BOARD[i][j]==n){
					rowCol[0]=i; rowCol[1]=j;
				}
			}
		}
		return rowCol;
	}

	//returns the h2 value for a given board, as defined by AIMA (manhatten score)
	public int heuristic2(){
		int[][] CORRECT_BOARD = {{0,1,2},{3,4,5},{6,7,8}};
		int M = 0;//sum of the manhattan scores
		System.out.printf("Manhattan Scores: ");

		for(int i =0;i < size; i++){
			for(int j = 0; j < size; j++){
				int currentTile = board[i][j];
				if(currentTile != 0){
					int[] destination = rowAndCol(currentTile);
					int k = destination[0]; int l = destination[1];
					int m = Math.abs(i-k) + Math.abs(j-l);
					M += m;
					System.out.printf("%d, ", m);
				}
			}
		}
		
		return M;
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
