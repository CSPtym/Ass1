package ass1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.StrokeAction;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.Control;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Table;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.io.CSVTableWriter;
import prefuse.data.io.GraphMLReader;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.GraphLib;
import prefuse.util.PrefuseLib;
import prefuse.util.StrokeLib;
import prefuse.util.force.ForceSimulator;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JForcePanel;
import prefuse.util.ui.JPrefuseApplet;
import prefuse.util.ui.JValueSlider;
import prefuse.util.ui.UILib;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;

/*class FinalControlListener extends ControlAdapter implements Control {

	public void itemClicked(VisualItem item, MouseEvent e) {
		if (item instanceof NodeItem) {
			JFrame frame = new JFrame("Prefuse");
			frame.setBounds(0, 0, 400, 300);
			
			JPanel jpan = new GraphDummy(p, "babloo");
			frame.getContentPane().add(jpan);
			frame.setVisible(true);

		}
	}

}*/

class NodeRenderer extends ShapeRenderer {
	@Override
	protected Shape getRawShape(VisualItem item) {
		double x = item.getX();
		if (Double.isNaN(x) || Double.isInfinite(x))
			x = 0;
		double y = item.getY();
		if (Double.isNaN(y) || Double.isInfinite(y))
			y = 0;
		Integer value = (Integer) item.get("nofnodes");
		double width = getBaseSize() + item.getSize() + (1.2*value);
		// Center the shape around the specified x and y
		if (width > 1) {
			x = x - width / 2;
			y = y - width / 2;
		}
		if (!item.canGet("value", String.class))
			return ellipse(x, y, width, width);
		Integer v = Integer.parseInt(item.get("value").toString());
		if (v == 0)
			return star((float) x, (float) y, (float) width);
		else if (v == 1)
			return triangle_left((float) x, (float) y, (float) width);
		else if ((int)item.get("id")==257)
		{
			return ellipse(x,y,700.0,800.0);

		}
		else 
			return ellipse(x, y, width, width);
		// to be changed later according to the values of the new node
	}
}

public class polblogs extends JPrefuseApplet {

	private static final String graph = "graph";
	private static final String nodes = "graph.nodes";
	private static final String edges = "graph.edges";

	public void init() {

		UILib.setPlatformLookAndFeel();
		JComponent graphview = demo("polblogs.xml", "label");
		this.setPreferredSize(new Dimension(1024,768));
		graphview.setMinimumSize(new Dimension(1024,768));

		this.getContentPane().add(graphview);
	}



	public static JComponent demo(String datafile, String label) {
		Graph g = null;
		if (datafile == null) {
			g = GraphLib.getGrid(15, 15);
		} else {
			try {
				g = new GraphMLReader().readGraph(datafile);

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return demo(g, label);
	}

	public static void caller(Graph g,String label){
		double[] A=new double[30];
		double[] B=new double[30];
		double[] C=new double[30];
		double[] D=new double[30];
		Directedtriad triad=new Directedtriad();
		
		//System.out.println(triad.triad(g).triad);
		double localundi=triad.localcoeff(g);
		System.out.println("Local Coefficient : "+localundi);
		double triads=triad.triad();
		System.out.println("Triads : "+triads);
		double globalundi=triad.globalcoeff(g);
		System.out.println("Global Coefficient : "+globalundi);
		double polariser=triad.ratio(g);
		System.out.println("Polarising Coefficient : "+polariser);
		 Table table=new Table();

			table.addColumn("Graphs",String.class);
			table.addColumn("Ratios", Double.class);
			table.addColumn("Triads",Double.class);
			table.addColumn("Global Coefficients",Double.class);
			table.addColumn("Local Coefficients",Double.class);

			for(int i=0;i<30;i++)
		{
			Graph h=randomgenerator(g,label);
			D[i]=triad.localcoeff(h);
			A[i]=triad.triad();
			B[i]=triad.ratio(h);
			C[i]=triad.globalcoeff(h);
			//System.out.println(D[i]);
		}


		for (int k=0;k<A.length;k++){
			int row=table.addRow();
			table.set(row,"Graphs","Graph"+k);
			table.set(row,"Ratios",B[k]);
			table.set(row,"Triads",A[k]);
			table.set(row,"Global Coefficients",C[k]);
			table.set(row,"Local Coefficients", D[k]);
		}

		try{
		//	FileWriter fstream = new FileWriter("out.txt");
			//BufferedWriter out = new BufferedWriter(fstream);

			//FileOutputStream f = new FileOutputStream(new File("out1.txt"));
			//OutputStream output = new FileOutputStream("out1.txt");
			CSVTableWriter writer=new CSVTableWriter();
			writer.writeTable(table,"outd.csv");
			/*String[] arr={"\"Graphs\""+",","\"Ratio\""+",","\"Triads\""};
			for (int i=0;i<arr.length;i++){
			out.write(arr[i]);
			}
			out.close();*/
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());

	}

	}	


	public static void triad(Graph g) {
		int n = g.getNodeCount();

		int triad = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				for (int k = 0; k < n; k++)
					if (((g.getEdge(g.getNode(i), g.getNode(j)) != null) | (g
							.getEdge(g.getNode(j), g.getNode(i)) != null))
							&& ((g.getEdge(g.getNode(j), g.getNode(k)) != null) | (g
									.getEdge(g.getNode(k), g.getNode(j)) != null))
							&& ((g.getEdge(g.getNode(k), g.getNode(i)) != null) | (g
									.getEdge(g.getNode(i), g.getNode(k)) != null)))
						triad++;

		triad /= 3;
		System.out.println("triad= " + triad);
	}

	/*
	 * public static void ratio(Graph g) {
	 * 
	 * double polariser=0.; int e=g.getEdgeCount(); int n=g.getNodeCount();
	 * for(int i=0;i<e;i++) if(g.getNode((int)
	 * g.getKey(g.getSourceNode(i))).get(1).equals(g.getNode((int)
	 * g.getKey(g.getTargetNode(i))).get(1)) ) polariser++;
	 * 
	 * System.out.println("polariser= "+polariser); //int nc2=(n*(n-1))/2;
	 * double ratio=polariser/e; System.out.println("ratio="+ratio); }
	 * 
	 * public static Graph randomgenerator(Graph g, String label) { int
	 * e=g.getEdgeCount(); int n=g.getNodeCount(); Graph h=new Graph();
	 * 
	 * for(int i=0;i<n;i++) { h.addNode(); } h.addColumn("label", String.class);
	 * h.addColumn("value", String.class); for(int i=0;i<n;i++) {
	 * h.getNode(i).set(0, g.getNode(i).get(0)); h.getNode(i).set(1,
	 * g.getNode(i).get(1)); }
	 * 
	 * Random rn=new Random(); for(int i=0;i<e;i++) { int a=rn.nextInt(n); int
	 * b=rn.nextInt(n); while(b==a) b=rn.nextInt(n); h.addEdge(a,b); }
	 * 
	 * 
	 * return h;
	 * 
	 * 
	 * 
	 * //System.out.println(g.getEdge(g.getNode(1), g.getNode(0)));
	 * 
	 * }
	 */
	static Graph[] arr;
	public static Graph randomgenerator(Graph g, String label)
	{
		int e=g.getEdgeCount();
		int n=g.getNodeCount();
		Graph h=new Graph();

		for(int i=0;i<n;i++)
		{
			h.addNode();
		}
		h.addColumn("label", String.class);
		h.addColumn("value", String.class);
		h.addColumn("id",Integer.class);
		for(int i=0;i<n;i++)
		{
			h.getNode(i).set(0, g.getNode(i).get(0));
			h.getNode(i).set(1, g.getNode(i).get(1));
			h.getNode(i).set(2,i);
		}
		//System.out.println((n*(n-1)*(n-2))/6);
	//	for(int i=0;i<e;i++)
		//	g.removeEdge(i);
		Random rn=new Random();
		for(int i=0;i<e;i++)
		{
			int a=rn.nextInt(n);
			int b=rn.nextInt(n);
			while(b==a)
			b=rn.nextInt(n);
			h.addEdge(a,b);
		}

		//helper(h,label);
		return h;



		//System.out.println(g.getEdge(g.getNode(1), g.getNode(0)));

	}


	public static JComponent helper(Graph g, String label) {
		
		
		Graph h=new Graph();
		/*
		h.addNode();
		h.addColumn("label", String.class);
		h.addColumn("value", String.class);
		h.addColumn("source", String.class);
		h.addColumn("id", Integer.class);
		for(int i=0;i<g.getNodeCount();i++)
		{
			
			h.getNode(i).set(3, i);
			h.getNode(i).set(0, g.getNode(i).get(0));
			h.getNode(i).set(1, g.getNode(i).get(1));
			h.getNode(i).set(2, g.getNode(i).get(2));
			h.addNode();
		}
		h.removeNode(h.getNodeCount());
		*/
		//g.removec
		Scc obj=new Scc(g);
		Object[] o =obj.Banja(g);
		h = (Graph) o[0];
		arr = (Graph[]) o[1];
		
		
		
		
		
		
		//m.dfs(g);
		//System.out.println(g.getNode(524));
				final Visualization vis = new Visualization();
		//g=check;
		VisualGraph vg = vis.addGraph(graph, h);
		 caller(g,label);
		/*
		 * g.addColumn("degree",Integer.class); for (int
		 * i=0;i<g.getNodeCount();i++){
		 * g.getNode(i).set("degree",g.getNode(i).getDegree()); }
		 */

		vis.setValue(edges, null, VisualItem.INTERACTIVE, Boolean.FALSE);
		TupleSet focusGroup = vis.getGroup(Visualization.FOCUS_ITEMS);
		focusGroup.addTupleSetListener(new TupleSetListener() {
			public void tupleSetChanged(TupleSet ts, Tuple[] add, Tuple[] rem) {
				for (int i = 0; i < rem.length; ++i)
					((VisualItem) rem[i]).setFixed(false);
				for (int i = 0; i < add.length; ++i) {
					((VisualItem) add[i]).setFixed(false);
					((VisualItem) add[i]).setFixed(true);
				}
				vis.run("draw");
			}
		});

		// set up the renderers

		// ShapeRenderer shape1=new ShapeRenderer(20);
		NodeRenderer rn = new NodeRenderer();
		EdgeRenderer edge = new EdgeRenderer(prefuse.Constants.EDGE_TYPE_LINE,
				prefuse.Constants.EDGE_ARROW_FORWARD);
		edge.setArrowHeadSize(10, 10);
		vis.setRendererFactory(new DefaultRendererFactory(rn, edge));

		// -- set up the actions ----------------------------------------------

		int maxhops = 5, hops = 5;
		final GraphDistanceFilter filter = new GraphDistanceFilter(graph, hops);

		int[] palette = new int[] { ColorLib.rgba(255,0,102,150),
				ColorLib.rgba(102,0,255,200), ColorLib.rgba(255,255,204,200) };
		// map nominal data values to colors using our provided palette
		DataColorAction filler = new DataColorAction(nodes, "value",
				Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
		int[] color = new int[] { ColorLib.rgb(61,0,153),
				ColorLib.rgb(26, 255, 0), ColorLib.rgb(200, 180, 255) };
		DataColorAction strokecolor = new DataColorAction(nodes, "value",
				Constants.NOMINAL, VisualItem.STROKECOLOR, color);

		DataColorAction edgecolor = new DataColorAction(edges, "value",
				Constants.NOMINAL, VisualItem.STROKECOLOR, color);
		filler.add("_fixed", ColorLib.rgba(255, 100, 100, 0));// Giving red
																// color to
																// focussed node
		filler.add("_highlight", ColorLib.rgb(255, 255, 153));// Giving yellow
																// colors to
																// it's
																// neighbours
		strokecolor.add("_highlight", ColorLib.rgb(0, 0, 0));
		edgecolor.add("_highlight", ColorLib.rgb(0, 0, 0));

		ActionList draw = new ActionList();
		draw.add(filter);
		draw.add(filler);
		draw.add(strokecolor);
		draw.add(edgecolor);
		draw.add(new ColorAction(nodes, VisualItem.TEXTCOLOR, ColorLib.rgb(0,
				0, 0)));
		// draw.add(new ColorAction(nodes, VisualItem.STROKECOLOR,
		// ColorLib.rgba(131,170,241,150)));
		draw.add(new ColorAction(edges, VisualItem.FILLCOLOR, ColorLib
				.gray(200)));
		draw.add(new ColorAction(edges, VisualItem.STROKECOLOR, ColorLib.rgb(
				232, 204, 204)));
		ForceDirectedLayout fdl = new ForceDirectedLayout(graph);

		ForceSimulator fsim = fdl.getForceSimulator();
		fsim.getForces()[0].setParameter(0, -8.2f);
		fsim.getForces()[0].setParameter(1, -8.2f);
		// fsim.getForces()[1].setParameter(0, -8.2f);
		ActionList animate = new ActionList(Activity.INFINITY);
		animate.add(fdl);
		animate.add(filler);
		animate.add(strokecolor);
		animate.add(edgecolor);
		animate.add(new RepaintAction());

		// finally, we register our ActionList with the Visualization.
		// we can later execute our Actions by invoking a method on our
		// Visualization, using the name we've chosen below.
		vis.putAction("draw", draw);
		vis.putAction("layout", animate);
		vis.runAfter("draw", "layout");

		// --------------------------------------------------------------------
		// STEP 4: set up a display to show the visualization

		Display display = new Display(vis);
		display.setSize(1000, 700);
		display.pan(500, 350);
		// display.setForeground(Color.GRAY);

		display.setBackground(Color.WHITE);

		// main display controls
		display.addControlListener(new FocusControl(1));
		display.addControlListener(new DragControl());
		display.addControlListener(new PanControl());
		//display.addControlListener(new FinalControlListener());
		display.addControlListener(new ZoomControl());
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new ZoomToFitControl());
		display.addControlListener(new NeighborHighlightControl());

		// --------------------------------------------------------------------
		// STEP 5: launching the visualization

		// create a panel for editing force values

		final JFastLabel name1 = new JFastLabel();
		name1.setPreferredSize(new Dimension(390, 30));
		name1.setMaximumSize(new Dimension(390, 30));
		name1.setVerticalAlignment(SwingConstants.TOP);

		name1.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
		name1.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 12));
		final JFastLabel aff1 = new JFastLabel();
		aff1.setPreferredSize(new Dimension(390, 30));
		aff1.setMaximumSize(new Dimension(390, 30));
		aff1.setVerticalAlignment(SwingConstants.TOP);
		aff1.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
		aff1.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 16));
		final StrokeLib stroke = new StrokeLib();
		display.addControlListener(new ControlAdapter() {
			public void itemEntered(VisualItem item, MouseEvent e) {
				if (item instanceof NodeItem) {
					String label = (String) item.get("label");
					name1.setText(label);
					BasicStroke p = stroke.getStroke(2);
					item.setStroke(p);
					int aff = Integer.parseInt((String) item.get("value"));
					if (aff == 0) {
						aff1.setText("Value:0");
						item.setStrokeColor(ColorLib.rgba(131, 170, 241, 250));
						item.setStroke(p);
					} else if (aff == 1) {
						aff1.setText("Value:1");
						item.setStrokeColor(ColorLib.rgba(140, 255, 25, 200));
						item.setStroke(p);
					} else {
						aff1.setText("Value:2");
						item.setStrokeColor(ColorLib.rgb(225, 195, 225));
						item.setStroke(p);
					}
				}
			}

			public void itemExited(VisualItem item, MouseEvent e) {

				BasicStroke p = stroke.getStroke(0);
				item.setStroke(p);
				item.setStrokeColor(ColorLib.rgba(131, 170, 241, 150));
				aff1.setText(null);
				name1.setText(null);
			}
		});

		display.addControlListener(new ControlAdapter() {
			public void itemClicked(VisualItem item, MouseEvent e) {
				if (item instanceof NodeItem) {
					JFrame frame = new JFrame("Prefuse");
					frame.setBounds(0, 0, 400, 300);
					Integer id=(Integer)item.get("id");
					System.out.println(id);

					JPanel jpan = new GraphDummy(arr[id], "label");
					frame.getContentPane().add(jpan);
					frame.setVisible(true);

				}
			}
		});
		Box info = UILib
				.getBox(new Component[] { name1, aff1 }, false, 0, 2, 0);
		info.setBorder(BorderFactory.createTitledBorder("Node Info"));
		info.setAlignmentX((float) (0.4));
		info.setAlignmentY((float) 10.0);
		info.setMaximumSize(new Dimension(420, 60));

		JTextField star = new JTextField("Star --0");
		JTextField triangle = new JTextField("Triangle -- 1");
		JTextField Circle = new JTextField("Circle -- 2");
		Box pf = new Box(BoxLayout.Y_AXIS);
		pf.add(star);
		pf.add(triangle);
		pf.add(Circle);

		pf.setBorder(BorderFactory.createTitledBorder("Values"));
		pf.setMaximumSize(new Dimension(300, 30));
		final JForcePanel fpanel = new JForcePanel(fsim);
		final JValueSlider slider = new JValueSlider("Distance", 0, maxhops,
				hops);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				filter.setDistance(slider.getValue().intValue());
				vis.run("draw");
			}
		});
		slider.setBackground(Color.WHITE);
		slider.setPreferredSize(new Dimension(300, 30));
		slider.setMaximumSize(new Dimension(300, 30));

		Box cf = new Box(BoxLayout.Y_AXIS);
		cf.add(slider);
		cf.setBorder(BorderFactory.createTitledBorder("Connectivity Filter"));

		fpanel.add(cf);

		fpanel.add(pf);
		fpanel.add(info);

		fpanel.add(Box.createVerticalGlue());

		// display.setAlignmentX(CENTER_ALIGNMENT);
		// create a new JSplitPane to present the interface
		JSplitPane split = new JSplitPane();
		split.setLeftComponent(display);
		split.setRightComponent(fpanel);

		// split.setTopComponent(opanel);
		split.setOneTouchExpandable(true);
		split.setContinuousLayout(false);
		//split.setDividerLocation(530);
		//split.setDividerLocation(800);
		split.setResizeWeight(0.9);

		// position and fix the default focus node
		NodeItem focus = (NodeItem) vg.getNode(0);
		PrefuseLib.setX(focus, null, 800);
		PrefuseLib.setY(focus, null, 400);
		focusGroup.setTuple(focus);

		// now we run our action list and return
		return split;
	}
	static Graph[] array;

	public static JComponent demo(Graph g, String label) {
		// create a new, empty visualization for our data



		/*Graph debug=new Graph();
		for(int i=0;i<8;i++)
		debug.addNode();
		debug.addColumn("label", String.class);
		debug.addColumn("value", String.class);
		debug.addColumn("source", String.class);
		debug.getNode(0).set(0, "a");
		debug.getNode(1).set(0, "b");
		debug.getNode(2).set(0, "c");
		debug.getNode(3).set(0, "d");
		debug.getNode(4).set(0, "e");
		debug.getNode(5).set(0, "f");
		debug.getNode(6).set(0, "g");
		debug.getNode(7).set(0, "h");
		
		debug.getNode(0).set(1, "a");
		debug.getNode(1).set(1, "b");
		debug.getNode(2).set(1, "c");
		debug.getNode(3).set(1, "d");
		debug.getNode(4).set(1, "e");
		debug.getNode(5).set(1, "f");
		debug.getNode(6).set(1, "g");
		debug.getNode(7).set(1, "h");
		
		debug.getNode(0).set(2, "a");
		debug.getNode(1).set(2, "b");
		debug.getNode(2).set(2, "c");
		debug.getNode(3).set(2, "d");
		debug.getNode(4).set(2, "e");
		debug.getNode(5).set(2, "f");
		debug.getNode(6).set(2, "g");
		debug.getNode(7).set(2, "h");

		debug.addEdge(0, 1);
		debug.addEdge(1, 2);
		debug.addEdge(2, 3);
		debug.addEdge(3, 2);
		debug.addEdge(3, 7);
		debug.addEdge(7, 3);
		debug.addEdge(7, 6);
		debug.addEdge(2, 6);
		debug.addEdge(6, 5);
		debug.addEdge(5, 6);
		debug.addEdge(1, 5);
		debug.addEdge(4, 5);
		debug.addEdge(4, 0);
		debug.addEdge(1, 4);*/

		//System.out.println("challll "+debug.getEdge(6, 7));




		//System.out.println("sas" +check.getEdgeCount());
		//System.out.println("sas" +check.getNodeCount());

		return (helper(g, label));
	}
} // end of class GraphView