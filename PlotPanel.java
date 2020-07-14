/*
 *
 * Code based on Craig Wood's class PlotTest on Code Ranch
 *
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

import org.json.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlotPanel extends JPanel {
	private int[] exampleVal = {4, 20, 17, 8};

	private final int PAD = 100;
	private final int SPAD = 10;
	private int[] XVals = null;
	private int[] YVals = null;
	private double xScale = 0.00;
	private double yScale = 0.00;
	private double maxValue = 100.0;
	private JSONObject[] stor = null;
	private int select = 0;
	private LinkedList<Double> values = new LinkedList<Double>();
	double[] maxVals = null;
	DecimalFormat df = new DecimalFormat("#.##");

	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        //Create transform for standard drawing and rotated drawing
        AffineTransform oldAT = g2.getTransform();
        g2.rotate(Math.PI/2);
        AffineTransform newAT = g2.getTransform();
        g2.setTransform(oldAT);

        int w = getWidth();
        int h = getHeight();
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(PAD, SPAD, w-SPAD, h-PAD);
        g2.setColor(Color.black);
        g2.drawLine(PAD, SPAD, PAD, h-PAD);
        g2.drawLine(PAD, h-PAD, w-SPAD, h-PAD);


        if (stor == null){
        	xScale = (w - PAD- SPAD)/(exampleVal.length + 1);
        	maxValue = 100.0;
        	yScale = (h - 2*PAD)/maxValue;

        	// The origin location.
        	int x0 = PAD;
        	int y0 = h-PAD;
        	g2.setPaint(Color.black);
        }

        else{
        	values.clear();
        	maxValue = maxVals[select];
        	String[] keys = JSONObject.getNames(stor[select]);
        	xScale = ((double)(w - PAD-SPAD))/((double)(keys.length + 1));
        	yScale = (h-2*PAD)/maxValue;
        	int x0 = PAD;
        	int y0 = h-PAD;
        	g2.setPaint(Color.blue);

        	for (int k = 0; k < stor[select].length(); k++){
        		values.add((stor[select].getDouble((keys[k]))));

        	}
        	XVals = new int[values.size()];
        	YVals = new int[values.size()];
        	int maxY = h;

        	//Dots for graph
        	for (int l = 0; l < values.size(); l++){
        		int x = x0 + (int)(xScale * l+1);
        		XVals[l] = x;
        		int y = y0 - (int)(yScale * values.get(l));
        		if ( y < maxValue){
        			maxY = y;
        		}
        		YVals[l] = y;
        		g2.fillOval(x-2, y-2, 4, 4);
        		}


        	g2.setColor(Color.black);
        	//x-labels
        	g2.setTransform(newAT);
        for(int i=0;i<XVals.length;i++){
        	int k = XVals.length/12;
        	if (i%Math.max(k, 1) == 0){
        	g2.drawString(keys[i], h-PAD+3, -XVals[i]+2);
        	g2.drawLine(h-PAD, -XVals[i], SPAD, -XVals[i]);
        		}
        	}
        g2.setTransform(oldAT);

        //y-labeling
       
        for(int i=0;i<11;i++){
        	if (i==0){
        		g2.drawString("0.00", PAD-35, h-PAD+4);
        	}
        	else{
        		int ylev= (int)(h-PAD + (double)(i)/10*PAD +(double)(i)/10*SPAD - (double)(i)/10*h);
        		g2.drawString(df.format( maxValue * ((double)(i)/9*maxY) ), PAD-45, ylev+4);
        		g2.drawLine(PAD, ylev, w-SPAD, ylev);
        	}
        }

        //line of graph
        g2.setColor(Color.blue);
    	g2.drawPolyline(XVals, YVals, XVals.length);
        }

    }

	public void regraph(JSONObject[] arr, int i, double[] max){
		this.stor = arr;
		this.select = i;
		this.maxVals = max;
		this.repaint();
	}

	public void reselect(int i){
		this.select = i;
		this.repaint();
	}

	public PlotPanel(){
		this.repaint();
	}
}
