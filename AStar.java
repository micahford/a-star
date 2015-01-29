
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class AStar implements Comparable<AStar> {
	final static int size = 3;
	private int[][] board;
	private int cost;
	private int hash;
	public AStar(){
		cost = 0;
		board = new int[size][size];
		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0; i<size*size; i ++){l.add(i);}
		Collections.shuffle(l, new Random());
		for(int i = 0; i< size; i++){
			for(int j = 0; j < size; j++){
				board[i][j] = l.get(i*size+j);
			}
		}
		hash = this.hashval();
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
		hash = hashval();
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
		a.hash = this.hashCode(); //update the hashcode
		return a;
	}
	//returns list containing coordinates of the blank space
	private int[] blankLoc(AStar a){
		int[] location = {-1,-1};
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
			move1.cost = a.cost + 1;
			move1=moveSquare(move1.board[r-1][c],move1);
			l.add(move1);
		}
		if (!isOutOfBounds(r,c+1,a)){
			AStar move2 = new AStar(currentBoard);
			move2.cost = a.cost + 1;
			move2=moveSquare(move2.board[r][c+1],move2);
			l.add(move2);
		}
		if (!isOutOfBounds(r+1,c,a)){
			AStar move3 = new AStar(currentBoard);
			move3.cost = a.cost + 1;
			move3=moveSquare(move3.board[r+1][c],move3);
			l.add(move3);
		}
		if (!isOutOfBounds(r,c-1,a)){
			AStar move4 = new AStar(currentBoard);
			move4.cost = a.cost + 1;
			move4=moveSquare(move4.board[r][c-1],move4);
			l.add(move4);
		}
		return l;
	}
	//checks if a location is out of bounds
	public boolean isOutOfBounds(int r, int c, AStar a){
		boolean isOutOfBounds=false;
		if ((r>a.size-1) || (r<0)){
			isOutOfBounds=true;
		}
		else if ((c>size-1) || (c<0)){
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
				//System.out.print(n + " ");
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
		int M = 0;//sum of the manhattan scores
		//System.out.printf("Manhattan Scores: ");
		for(int i =0;i < size; i++){
			for(int j = 0; j < size; j++){
				int currentTile = board[i][j];
				if(currentTile != 0){
					int[] destination = rowAndCol(currentTile);
					int k = destination[0]; int l = destination[1];
					int m = Math.abs(i-k) + Math.abs(j-l);
					M += m;
					// System.out.printf("%d, ", m);
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
	
	//@override
	//change for different heuristics
	public int compareTo(AStar a){
		int heuristic = 1;
		if (this.fValue(heuristic) > a.fValue(heuristic)) return 1;
		else if (this.fValue(heuristic
				) == a.fValue(heuristic)) return 0;
		else return -1;
	}
	public boolean equals(AStar a){
		int[]a1 = this.orderBoard();
		int[]a2 = a.orderBoard();
		for(int i = 0; i < a1.length; i++){
			if(a1[i]!=a2[i]) {return false;}
		}
		return true;
	}
	//f(n) = g(n) + h(n);
	public int fValue(int heuristic){
		int h = 0;
		int g = this.cost;
		if(heuristic == 1){return g + this.heuristic1();}
		else if(heuristic == 2){return g + this.heuristic2();}
		else {
			System.out.println("ERROR. use h1 or h2");
			return 999;}
	}
	public int getCost(){
		return this.cost;
	}
	public int getHash(){
		return hashval();
	}
	public int[][]getBoard(){
		return this.board;
	}
	
	
	private int hashval(){
		String val= "";
		for(int i = 0; i<size; i++){
			for(int j = 0; j < size; j++){
				val+=this.board[i][j];
			}
		}
		return Integer.parseInt(val);
	}
}
