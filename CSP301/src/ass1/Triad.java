package ass1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.tuple.TableNode;

class Tuple1 {
	int triad;
	double sum;
}

public class Triad {
	static int triad = 0;

	public static Tuple1 triad(Graph g) {
		int count = g.getNodeCount();
		double[] arr = new double[count];
		double sum = 0;
		Set<Node>[] hash = new HashSet[count];
		for (int hashset = 0; hashset < count; hashset++) {
			hash[hashset] = new HashSet();
		}
		
		for (int nodeid = 0; nodeid < count; nodeid++) {
			int neighbours = g.getNode(nodeid).getDegree();
			double denominator = (double) (neighbours) * (neighbours - 1) / 2;
			Iterator iter = g.getNode(nodeid).neighbors();
			while (iter.hasNext()) {
				Node neighbour = (Node) iter.next();
				if (nodeid < Integer.parseInt(neighbour.get("id").toString())) {
					HashSet copy = new HashSet(hash[nodeid]);
					copy.retainAll(hash[Integer.parseInt(neighbour.get("id").toString())]);	
					
					triad += copy.size();
					hash[(Integer.parseInt(neighbour.get("id").toString()))].add(g.getNode(nodeid));
				}
			}
		}
		sum = (double) sum / (double) count;
		Tuple1 p = new Tuple1();
		p.triad = triad;
		p.sum = sum;
		return p;

	}
	public static double coefficient(Graph g){
		int n=g.getNodeCount();
		double d=n*(n-1)*(n-2)/6;
		if (d>0)
			return (double)((double)(triad(g).triad))/d;
		else
			return 0;
	}

}
