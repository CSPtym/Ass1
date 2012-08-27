package ass1;

import java.util.HashSet;
import java.util.Iterator;

import prefuse.data.Graph;
import prefuse.data.tuple.TableNode;

public class Scc {
	static int[] exittime;
	//static int n;
	
	
	public Scc(Graph g) {
		exittime = new int[g.getNodeCount()];
		
	}
	static int count;
	static int time = 0;
	static int index=0;
	static int koitobachao=0;
	static Graph[] array=new Graph[1000];
	static int[] array2=new int[1000];
	public static void dfshelper(Graph g,int i)
	{
		time++;
		//System.out.print(i + "-->");
	//	System.out.println(i);
		g.getNode(i).set(3, "g");
		Iterator it = g.getNode(i).outNeighbors();
		while (it.hasNext())
		{
			 TableNode n = (TableNode) it.next();
			 if(((String) n.get(3)).equals("w"))
				 dfshelper(g,(int) n.get(5));
			 
			 //System.out.println(n.get(5));
		}
		time++;
		g.getNode(i).set(3, "b");
		//System.out.println(i + "-->" +time + "\n");
		g.getNode(i).set(4, time);
		exittime[index++]=i;
		
	}
	public static void dfs(Graph g)
	{
		g.addColumn("colour", String.class);
		g.addColumn("exittime", Integer.class);
		g.addColumn("id", Integer.class);
		for(int i=0;i<g.getNodeCount();i++)
			{g.getNode(i).set(3, "w");
			g.getNode(i).set(5, i);
			}
		int i=0;
		while(i<g.getNodeCount())
		{
			while((i<g.getNodeCount())&&(g.getNode(i).get(3)!="w"))
				{i++;}
		//System.out.println("\n"+i + "-->");
			if(i<g.getNodeCount())
					{
					dfshelper(g,i);
					}
		i++;
	//	System.out.println(i);
	//	System.out.println("lo");
		}
		for(int la=0;la<index;la++)
		{
		//	System.out.println(exittime[la]);
		}
	}
	
	public static Graph ulta(Graph g)
	{
		int e=g.getEdgeCount();
		Graph h=new Graph(true);
		
		int n=g.getNodeCount();
		for(int i=0;i<n;i++)
		{
			h.addNode();
		}
		h.addColumn("label", String.class);
		h.addColumn("value", String.class);
		h.addColumn("source", String.class);

		h.addColumn("colour", String.class);
		h.addColumn("exittime", Integer.class);
		h.addColumn("id", Integer.class);
		for(int i=0;i<n;i++)
		{
			h.getNode(i).set(0, g.getNode(i).get(0));
			h.getNode(i).set(1, g.getNode(i).get(1).toString());
			h.getNode(i).set(2, g.getNode(i).get(2));
			h.getNode(i).set(3, g.getNode(i).get(3));
			h.getNode(i).set(4, g.getNode(i).get(4));
			h.getNode(i).set(5, g.getNode(i).get(5));
		}
		for(int i=0;i<e;i++)
			{h.addEdge((int) g.getEdge(i).getTargetNode().get(5),(int) g.getEdge(i).getSourceNode().get(5));}
			
		//System.out.print(h.getEdge(6, 7));
		
		//helper(h,label);
		return h;
	}
	
	
	
	public static Graph dfs2(Graph g)
	{
		
		int n=g.getNodeCount();
		int i=n-1;
		g.addColumn("count", Integer.class);
		g.addColumn("khudkiid", Integer.class);
		count=0;
		while(i>=0)
		{
			//System.out.println("yeee");
			//System.out.println(g.getNode(exittime[i]).get(3));
			while((i>=0)&&(!(g.getNode(exittime[i]).get(3).equals("b"))))
		{
			//	System.out.println("yeee");

				i--;
		}
			if(i>=0)
			{	
				
				//System.out.println(i);
				//System.out.println("\n"+i + "-->");
				
				koitobachao=0;
				array[count]=new Graph(true);
				array[count].addColumn("label", String.class);
				array[count].addColumn("value", String.class);
				array[count].addColumn("source", String.class);
	
				array[count].addColumn("colour", String.class);
				array[count].addColumn("exittime", Integer.class);
				array[count].addColumn("id", Integer.class);
				array[count].addColumn("count", Integer.class);
				//array[count].addColumn("khudkiid", Integer.class);
				array[count].addNode();
				//specifications
				array[count].getNode(0).set(0, g.getNode(exittime[i]).get(0));
				array[count].getNode(0).set(1, g.getNode(i).get(1).toString());
				array[count].getNode(0).set(2, g.getNode(i).get(2));
				array[count].getNode(0).set(3, g.getNode(i).get(3));
				array[count].getNode(0).set(4, g.getNode(i).get(4));
				array[count].getNode(0).set(5, g.getNode(exittime[i]).get(5));
				array[count].getNode(0).set(6, 0);
				g.getNode(exittime[i]).set(6, count);
				g.getNode(exittime[i]).set(7, 0);
				dfs2helper(g,exittime[i],count);
				//array[count].addEdge(0, koitobachao-1);
		//		System.out.print(b  + "-- >");
				array2[count]=koitobachao;
				i--;
				count++;
			}
			
		}
		
		System.out.println(count);
		System.out.println("n="+n);
		int k=0;

		for (k =0;k<count;k++)
		{
			if(array2[k]==3)
				break;
			
		/*for(int j=0;j<array2[k];j++)
		System.out.print(array[k].getNode(j).get(5)+"-->");
		System.out.println();*/
		}
		//for(int ke=0;ke<500;ke++)
		//System.out.println( array[524].getEdge(ke));
		return array[k];
		
		
		//System.out.println("ye dheko "+g.getNode(2).get(6));
		//return g;
	}
	static int u=0;
	public static void dfs2helper(Graph g,int i,int count)
	{
		
		koitobachao++;
		
		g.getNode(i).set(3, "w");
		
		//System.out.println(count +":" +u++);
		//System.out.println(u++);
		Iterator it = g.getNode(i).outNeighbors();
		while (it.hasNext())
		{
			 TableNode n = (TableNode) it.next();
			 
			 
			 
			 
					  
					  
			 if(((String) n.get(3)).equals("b"))
			 {
				 array[count].addNode();
				 array[count].getNode(koitobachao).set(0, n.get(0));
					array[count].getNode(koitobachao).set(1, n.get(1));
					array[count].getNode(koitobachao).set(2, n.get(2));
					array[count].getNode(koitobachao).set(3, n.get(3));
					array[count].getNode(koitobachao).set(4, n.get(4));
					array[count].getNode(koitobachao).set(5, n.get(5));
					array[count].getNode(koitobachao).set(6, count);
					//array[count].getNode(koitobachao).set(7, koitobachao);
					g.getNode((int)n.get(5)).set(6,count);
					g.getNode((int)n.get(5)).set(7, koitobachao);
				 //array[count].addEdge(koitobachao,koitobachao-1 );

				
					  Iterator iterate=n.outNeighbors();
					  while(iterate.hasNext())
					  {
						 // System.out.println(u++);
						  TableNode ne=(TableNode) iterate.next();
						 // System.out.println("count "+ne.get(6));
						 // System.out.println(ne.get(7));
						  if((ne.get(6)!=null)&&((int)g.getNode((int)ne.get(5)).get(6)==count))
							  array[count].addEdge(array[count].getNode((int)ne.get(7)),array[count].getNode((int)n.get(7)));  
					  }
							  
					  Iterator iterat=n.inNeighbors();
					  while(iterat.hasNext())
					  {
						//  System.out.println(u++);
						  TableNode ne=(TableNode) iterat.next();
						 // System.out.println("count "+ne.get(6));
						  if((ne.get(6)!=null)&&((int)g.getNode((int)ne.get(5)).get(6)==count))
							  array[count].addEdge(array[count].getNode((int)n.get(7)) ,array[count].getNode((int)ne.get(7))); 
								  
					  }	  
				//System.out.println("accha oo");			  
				 dfs2helper(g,(int) n.get(5),count);
			 }
		}
	}
	public static Graph func(Graph g)
	{
		Graph answer=new Graph(true);
		
		answer.addColumn("label", String.class);
		answer.addColumn("idg", Integer.class);
		answer.addColumn("value", String.class);
		answer.addColumn("source", String.class);
		answer.addColumn("nofnodes", Integer.class);
		answer.addColumn("id",Integer.class);
		for(int i=0;i<count;i++)
		{
			answer.addNode();
			answer.getNode(i).set(5, i);
		}
		for(int i=0;i<count;i++)
		{
			if(array2[i]==1)
			{
				answer.getNode(i).set(0, array[i].getNode(0).get(0));
				answer.getNode(i).set(1, (int)array[i].getNode(0).get(5));
				answer.getNode(i).set(2, array[i].getNode(0).get(1));
				answer.getNode(i).set(3, array[i].getNode(0).get(2));
				answer.getNode(i).set(4, 1);
				Iterator it=g.getNode((int)array[i].getNode(0).get(5)).inNeighbors();
				while(it.hasNext())
				{
					TableNode n=(TableNode) it.next();
					if(i!=(int)n.get(6))
					answer.addEdge((int)n.get(6),i);
				}
				/*Iterator itr=g.getNode((int)array[i].getNode(0).get(5)).outNeighbors();
				while(itr.hasNext())
				{
					TableNode n=(TableNode) itr.next();
				
					//System.out.println(n  + ":" + n.get(6));
					if(i!=(int)n.get(6))
					answer.addEdge(i,(int)n.get(6));
				}*/
			}
			else
			{
				String label="";
				String source="";
				int idg=0;
				for (int j=0;j<array2[i];j++)
				{
					label =label+ " " + j + ". " + array[i].getNode(j).get(0);
					source=source+ " " + j + ". " + array[i].getNode(j).get(2);
					idg+=((int)array[i].getNode(j).get(5))*(Math.pow(g.getNodeCount(), j));
					//array[i].getNode(j)
				}
				answer.getNode(i).set(0, label);
				answer.getNode(i).set(3, source);
				answer.getNode(i).set(1, idg);
				answer.getNode(i).set(2, "2");
				answer.getNode(i).set(4, array2[i]);
				
				for(int k=0;k<array2[i];k++)
				{
					Iterator it=g.getNode((int)array[i].getNode(k).get(5)).inNeighbors();
					while(it.hasNext())
					{
						TableNode n=(TableNode) it.next();
						if(i!=(int)n.get(6))
						answer.addEdge((int)n.get(6),i);
					}
				/*	Iterator itr=g.getNode((int)array[i].getNode(k).get(5)).outNeighbors();
					while(itr.hasNext())
					{
						TableNode n=(TableNode) itr.next();
						if(i!=(int)n.get(6))
						answer.addEdge(i,(int)n.get(6));
					}*/
				}
				
			}
			
		}
		// 524 ko dhek lena
		//System.out.println(answer.getNodeCount());
		//System.out.println("edges = "+answer.getEdgeCount());
		return answer;
	}
	static Graph[] arr;
	public static Graph[] connectedcomponent()
	{
		return array;
	}
	public static Object[] Banja (Graph  g)
	{
		dfs(g);
		Graph h=ulta(g);
		Graph tutochal=dfs2(h);
		g.addColumn("count", Integer.class);
		g.addColumn("khudkiid", Integer.class);
		//System.out.println(h.getNode(0).get(6));
		for(int p=0;p<g.getNodeCount();p++)
		{
			g.getNode(p).set(6, (int)h.getNode(p).get(6));
			g.getNode(p).set(7, (int)h.getNode(p).get(7));
		}
		Graph answer=func(g);
		//Graph h=randomgenerator(g,label);
		//answer.
		//System.out.println("challll "+answer.getEdge(1,2));
		//System.out.println(answer.getEdge((int)g.getNode(8).get(6),(int)g.getNode(640).get(6)));
		
		Graph check=new Graph(true);
		check.addColumn("label", String.class);
		check.addColumn("idg", Integer.class);
		check.addColumn("value", String.class);
		check.addColumn("source", String.class);
		check.addColumn("nofnodes", Integer.class);
		check.addColumn("id",Integer.class);
		answer.addColumn("idcheck",Integer.class);
		int i=0;
		
		 array=connectedcomponent();
		arr=new Graph[1000];
		for(int o=0;o<answer.getNodeCount();o++)
		{
			Iterator iter=answer.getNode(o).inNeighbors();
			Iterator itera=answer.getNode(o).outNeighbors();
			if(!iter.hasNext()&&(!itera.hasNext()));
				//answer.removeNode(o);
			else
			{
				check.addNode();
				check.getNode(i).set(0, answer.getNode(o).get(0));
				check.getNode(i).set(1, answer.getNode(o).get(1));
				check.getNode(i).set(2, answer.getNode(o).get(2));
				check.getNode(i).set(3, answer.getNode(o).get(3));
				check.getNode(i).set(4, answer.getNode(o).get(4));
				check.getNode(i).set("id", i);
				answer.getNode(o).set(6, i);
				arr[i]=array[o];
				i++;
			}
		}
		
		for(int lo=0;lo<(answer.getEdgeCount());lo++)
			check.addEdge(((int)answer.getEdge(lo).getSourceNode().get(6)), ((int)answer.getEdge(lo).getTargetNode().get(6)));

		return new Object[]{check,arr};
	}
}
