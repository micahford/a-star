import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		int d = 24;
		Scanner scan = new Scanner(new File(d + ".txt"));
		int[] order = new int[9];
		
		int numBoardsSolved = 0;
		int totalNodes = 0;
		
		for(int n = 0; n < 100; n ++) {
			order = stringToArray(scan.nextLine());
			Set<Integer> closedSet = new HashSet<Integer>();
			AStar a = new AStar();
			Queue<AStar> pq = new PriorityQueue<AStar>();
			//int[] order = {0,1,5,3,2,4,6,7,8};
			//AStar a = new AStar(order);
			while(!a.isSolvable()){
				a = new AStar();
			}
			
			a = new AStar(order);
			
			System.out.println(a);
			pq.add(a);
			int nodesGenerated = 1;
			while(pq.peek().heuristic2()!=0){
				AStar top = pq.poll();
				closedSet.add(top.getHash());
				List<AStar> possibleMoves = top.possMoves(top);
				for(int i = 0; i< possibleMoves.size(); i++){
					AStar currentNode = possibleMoves.get(i);
					if(!closedSet.contains(currentNode.getHash())){
						pq.add(possibleMoves.get(i));
						nodesGenerated++;
					}
				}
			}
			
			int movesAway = pq.peek().getCost();
//			
//			//writing to file:
//			File log = new File(movesAway+ ".txt");
//			PrintWriter out;
//			try {
//				out = new PrintWriter(new FileWriter(log, true));
//				out.append( print(a) + "\n");
//				out.close();
//				
//			} 
//			catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}			  
			if(movesAway == d ){ //only enter this is it actually is d moves away
				numBoardsSolved++;
				totalNodes += nodesGenerated;
			}
			
			
			//moves to get there:
			System.out.printf("%s\nNumber of moves to solution: %d\n", print(a), pq.peek().getCost());
			//nodes"
			System.out.printf("Nodes generated: %d\n", nodesGenerated);
			//System.out.println(pq);
		}
		System.out.println("Number of boards solved: " + numBoardsSolved);
		System.out.println("Average Nodes generated " + (float)totalNodes/numBoardsSolved);

	}

	private static int[] stringToArray(String s){
		int[] toReturn = new int[9];
		int counter =0;
		for(int i = 0; i < s.length(); i++){
			if (s.substring(i,i+1).matches("[0-9]")){
				toReturn[counter] = Integer.parseInt(s.substring(i,i+1));
				counter++;
			}
		}
		return toReturn;
	}

	//print in nice array format.
	private static String print(AStar a){
		String toReturn="{";
		for(int i =0; i<a.size; i++){
			for(int j = 0; j < a.size; j++){
				toReturn+= a.getBoard()[i][j] + ", ";
			}
		}
		int length = toReturn.length();
		toReturn = toReturn.substring(0, length-2) + "}";
		return toReturn;
	}
}
