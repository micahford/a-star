import java.util.HashSet;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class Main {

	public static void main(String[] args) {
		Set<Integer> closedSet = new HashSet<Integer>();
		
		int[] test = {7,2,4,5,0,6,8,3,1};
		AStar a = new AStar();
		
		
		Queue<AStar> pq = new PriorityQueue<AStar>();
		int[] order = {1,2,0,3,4,7,6,8,5};
		//AStar a = new AStar(order);
		while(!a.isSolvable()){
			a = new AStar();
		}
		System.out.println(a);
		
		pq.add(a);
		int nodesGenerated = 1;
		while(pq.peek().heuristic1()!=0){			
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
		System.out.println(closedSet);
		//should be solution:
		System.out.println(pq.peek());
		//moves to get there:
		System.out.printf("Number of moves to solution: %d\n", pq.peek().getCost());
		//nodes"
		System.out.printf("Nodes generated: %d\n", nodesGenerated);

		
		//System.out.println(pq);
		

		
		
		
	}

}
