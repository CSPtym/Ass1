package ass1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.soap.Node;

import prefuse.data.Graph;
import prefuse.data.tuple.TableNode;

public class Differentedges {

	static int index=0;
	public static int[] dedges(Graph g)
	{	
		g.addColumn("edgelabel",Integer.class);
		int e=g.getEdgeCount();
		int [] dedges=new int[e];
		for(int i=0;i<e;i++)
		{
			if(!(g.getEdge(i).getSourceNode().get("value")).equals(g.getEdge(i).getTargetNode().get("value")))
			{	
				g.getEdge(i).set("edgelabel",0);
				dedges[index++]=i;
			}
			else {
				g.getEdge(i).set("edgelabel",1);
			}
		
		}
			return dedges;
	}
	
public static int index()
{
	return index;
}
}
