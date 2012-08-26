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

	public Tuple1 triad(Graph g) {
		int count = g.getNodeCount();
		double[] arr = new double[count];
		double sum = 0;
		Set<Node>[] hash = new HashSet[count];
		for (int hashset = 0; hashset < count; hashset++) {
			hash[hashset] = new HashSet();
		}
		int triads[] = new int[count];
		for (int nodeid = 0; nodeid < count; nodeid++) {
			int neighbours = g.getNode(nodeid).getDegree();
			double denominator = (double) (neighbours) * (neighbours - 1) / 2;
			Iterator iter = g.getNode(nodeid).neighbors();
			while (iter.hasNext()) {
				Node neighbour = (Node) iter.next();
				if (nodeid < Integer.parseInt(neighbour.get("id").toString())) {
					HashSet copy = new HashSet(hash[nodeid]);
					copy.retainAll(hash[Integer.parseInt(neighbour.get("id").toString())]);	
					triads[nodeid]+=copy.size();
					triads[Integer.parseInt(neighbour.get("id").toString())]+=copy.size();
					Iterator<Node> i = copy.iterator();
					while (i.hasNext())
					{
						Node temp =(Node)i.next();
						triads[Integer.parseInt(temp.get("id").toString())]++;
					}
					triad += copy.size();
					hash[(Integer.parseInt(neighbour.get("id").toString()))].add(g.getNode(nodeid));
				}
			}
			
			if (denominator > 0) {
				arr[nodeid] = (double) (triads[nodeid]) / denominator;
				System.out.print(triads[nodeid]+" ");
				System.out.println(triad);
			} else {
				arr[nodeid] = 0.0;
			}
			sum += arr[nodeid];
		}
		sum = (double) sum / (double) count;
		Tuple1 p = new Tuple1();
		p.triad = triad;
		p.sum = sum;
		return p;

	}

}
