import javax.swing.*;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.EventObject;
import org.json.*;
import javax.swing.Timer;

//Creating the GUI as well as fetching the Data

public class Window extends JPanel implements ActionListener{
//	String[][] dataArr;
	JSONObject[] grafData = null;
	double[] maxVal = {0,0,0,0,0,0,0,0};
	private final int SEC = 20;

	private String getAPIKey(int index) {
		//this method is fetching the data 
		
		String apiKey = "&apikey=P6DOYJ8TLKZ2WSJ8";
		String APIURL = "https://www.alphavantage.co/query?function=";
		if (index == 0) {
			//for INTRADAY
			APIURL += timeCBox.getItemAt(timeCBox.getSelectedIndex()) + "&symbol=";
			APIURL += SymbolCBox.getItemAt(SymbolCBox.getSelectedIndex()) + "&interval=";
			APIURL += IntervalCBox.getItemAt(IntervalCBox.getSelectedIndex()) + "&output=";
			APIURL+= OutputCBox.getItemAt(OutputCBox.getSelectedIndex());
		}
		else if (index >= 1 && index <3){
			//for DAILY & DAILY_ADJUSTED
			APIURL += timeCBox.getItemAt(timeCBox.getSelectedIndex()) + "&symbol=";
			APIURL += SymbolCBox.getItemAt(SymbolCBox.getSelectedIndex()) + "&output=";
			APIURL+= OutputCBox.getItemAt(OutputCBox.getSelectedIndex());
		}
		else if(index >= 3){
			//for WEEKLY - MONTHLY ADJUSTED
			APIURL += timeCBox.getItemAt(timeCBox.getSelectedIndex()) + "&symbol=";
			APIURL += SymbolCBox.getItemAt(SymbolCBox.getSelectedIndex());
		}
		APIURL += apiKey;
		return APIURL;
	}
	
	public String[] update() {
		//Method to get initial data and to update data
		JSONObject o = new JSONObject();

		try {
			//Uses getAPIKey to construct URL and creates connection to that
			URL u = new URL(getAPIKey(timeCBox.getSelectedIndex()));   
			URLConnection c = u.openConnection();
			//Creates BufferedReader that takes input from URL
			BufferedReader in = new BufferedReader(
					new InputStreamReader(c.getInputStream()));
			StringBuffer buff = new StringBuffer();
			String input;

			while ((input = in.readLine()) != null){
				buff.append(input + "\n");
			}
			//creates new JSONObject from buffer and transfers to JSONObject outside try{}
			//then closes connection
			JSONObject a = new JSONObject(buff.toString());
			o = a;
			in.close();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		//removes the meta data block
		o.remove("Meta Data");
		//retrieves key used to access level containing data points
		String[] keys = JSONObject.getNames(o);
		
		
	
		
		JSONObject ts = o.getJSONObject(keys[0]);
		
		
		String[] out = JSONObject.getNames(ts);
		
		
		//sorts data points according to date
		
		Arrays.sort(out);
		JSONObject[] grafDat = new JSONObject[8];

		for (int i=0; i<8; i++){
			grafDat[i]= new JSONObject();
		}

		String[] test = new String[8];
		for (int i =0; i < test.length; i++) {
			test[i] = "==== Showing data for " + dataCBox.getItemAt(i) + " =====" + "\n";
		}


		if( (timeCBox.getSelectedIndex()== 0) || (timeCBox.getSelectedIndex()== 1) || (timeCBox.getSelectedIndex()== 3)
				|| (timeCBox.getSelectedIndex()== 5) ){
			
			for (int y = 0; y < 5; y++) {
				for (int i = 0; i < out.length; i++) {
				
					grafDat[y].put(out[i], ts.getJSONObject(out[i]).getDouble((String)dataCBox.getItemAt(y)));
					
					if (maxVal[y] < grafDat[y].getDouble(out[i])){
						maxVal[y] = grafDat[y].getDouble(out[i]);
					}
					test[y] += out[i] + ": " +
					ts.getJSONObject(out[i]).getDouble((String)dataCBox.getItemAt(y)) +"\n";
				}
			}
		}
	
		else if (timeCBox.getSelectedIndex() == 2){
			for (int y = 0; y < 7; y++) {
				for (int i = 0; i < out.length; i++) {
					grafDat[y].put(out[i], ts.getJSONObject(out[i]).getDouble((String)dataCBox.getItemAt(y)));
					if (maxVal[y] < grafDat[y].getDouble(out[i])){
						maxVal[y] = grafDat[y].getDouble(out[i]);
					}
					System.out.println("out[i]: " +out[i] + " dataCBox.getItemAt(y): "+ dataCBox.getItemAt(y));

					test[y] += out[i] + ": " + ts.getJSONObject(out[i]).getDouble((String)dataCBox.getItemAt(y)) +"\n";
					}

			}
		}
		else{
			for (int y = 0; y < out.length; y++) {
				for (int i = 0; i < out.length; i++) {
					System.out.println("out[i: "+ out[i]);
					grafDat[y].put(out[i], ts.getJSONObject(out[i]).getDouble((String)dataCBox.getItemAt(y)));
					if (maxVal[y] < grafDat[y].getDouble(out[i])){
						maxVal[y] = grafDat[y].getDouble(out[i]);
					}
					test[y] += out[i] + ": " + ts.getJSONObject(out[i]).getDouble((String)dataCBox.getItemAt(y)) +"\n";
					}
			}
		}
		this.grafData = grafDat;
		TextBox.textArea.setText(test[dataCBox.getSelectedIndex()]);

		return test;
	}

	JLabel dataLabel = new JLabel("Data Series", Font.PLAIN);
	JComboBox<String> dataCBox = new JComboBox<>(DataComboBox.getSeries(0));
	JLabel timeLabel = new JLabel("Time Series", Font.PLAIN);
	JComboBox<String> timeCBox = new JComboBox<>(TimeSeriesComboBox.getSeries());
	JLabel symbolLabel = new JLabel("Symbol", Font.PLAIN);
	JComboBox<String> SymbolCBox = new JComboBox<>(SymbolComboBox.getSeries());
	JLabel intervalLabel = new JLabel("Time Interval", Font.PLAIN);
	JComboBox<String> IntervalCBox = new JComboBox<>(IntervalComboBox.getSeries());
	JLabel outputLabel = new JLabel("Output Size", Font.PLAIN);
	JComboBox<String> OutputCBox = new JComboBox<>(OutputComboBox.getSeries());
	String[] data = new String[8];
	JButton queryButton = new JButton("---- Do Query ---");
	JPanel window = new JPanel();
	PlotPanel graf = new PlotPanel(); //PLOTPANEL; graf!
	JPanel grafPanel = new JPanel();
	JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, window, grafPanel);
	Timer disableTimer = new Timer(SEC * 1000, new disTimer(queryButton));
	DefaultComboBoxModel model0 = new DefaultComboBoxModel( DataComboBox.getSeries(0) );
	DefaultComboBoxModel model1 = new DefaultComboBoxModel( DataComboBox.getSeries(1));
	DefaultComboBoxModel model2 = new DefaultComboBoxModel( DataComboBox.getSeries(2));


	public Window() {
		
		//Constructor
		disableTimer.setRepeats(false);
		grafPanel.add(graf);
		add(split);
		split.setMinimumSize(new Dimension(500,800));
		grafPanel.setMinimumSize(new Dimension(450,450));
		grafPanel.setPreferredSize(new Dimension(600,600));
		graf.setMinimumSize(new Dimension(300,300));
		graf.setPreferredSize(new Dimension(500,500));
		grafPanel.setLocation(300, 0);
		split.setResizeWeight(0.5);
		graf.repaint();

		GridBagConstraints c = new GridBagConstraints();


		Insets inset = new Insets(10, 0, 10, 0);
		window.setSize(600, 800);
		window.setLayout(new GridBagLayout());

		//dataLabel
		c.gridx = 0;
		c.gridy = 0;
		c.insets = inset;
		window.add(dataLabel, c);

		//dataCBox
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		dataCBox.addActionListener(this);
		window.add(dataCBox, c);


		//timeLabel
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.CENTER;
		window.add(timeLabel, c);

		//timeCBox
		c.gridx = 1;
		c.gridy = 1;
		timeCBox.addActionListener(this);
		window.add(timeCBox, c);

		//symbolLabel
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		window.add(symbolLabel, c);

		//SymbolCBox
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		window.add(SymbolCBox, c);

		//intervalLabel
		timeLabel.setHorizontalAlignment(JLabel.RIGHT);
		c.gridx = 0;
		c.gridy = 3;
		window.add(intervalLabel, c);

		//IntervalCBox
		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		window.add(IntervalCBox, c);

		//outputLabel
		timeLabel.setHorizontalAlignment(JLabel.RIGHT);
		c.gridx = 0;
		c.gridy = 4;
		window.add(outputLabel, c);

		//OutputCBox
		c.gridx = 1;
		c.gridy = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		window.add(OutputCBox, c);

		//QueryButton
		c.gridx = 1;
		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		queryButton.addActionListener(this);
		window.add(queryButton, c);

		JScrollPane ScroPane = TextBox.getScrollPane();
		TextBox.textArea.setEditable(false);
		ScroPane.setPreferredSize(new Dimension(200,400));
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.insets = new Insets(0, 10, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		window.add(ScroPane, c);

		window.setVisible(true);
		graf.setVisible(true);
		split.setVisible(true);

	}



	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == timeCBox) {
			int sel = timeCBox.getSelectedIndex();
			if(sel == 2){
				dataCBox.setModel(model1);
			}
			else if((sel== 4)||(sel==6)){
				dataCBox.setModel(model2);
			}
			else{
				dataCBox.setModel(model0);
			}
			if (timeCBox.getSelectedIndex() == 0) {
				IntervalCBox.setEnabled(true);
			}
			else if (timeCBox.getSelectedIndex() != 0){
				IntervalCBox.setEnabled(false);
				if (timeCBox.getSelectedIndex() >= 3) {
					OutputCBox.setEnabled(false);
				}
				else {
					OutputCBox.setEnabled(true);
				}
			}
		}
		if (e.getSource() == queryButton) {
			queryButton.setEnabled(false);
			disableTimer.start();
			data = update();   
			graf.regraph(grafData, dataCBox.getSelectedIndex(), maxVal);
		}

		if (e.getSource() == dataCBox) {
			graf.reselect(dataCBox.getSelectedIndex());
			TextBox.textArea.setText(data[dataCBox.getSelectedIndex()]);
		}
	}


	public static void main(String[] args) {
		JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new Window());
        f.setMinimumSize(new Dimension(800,750));
        f.setVisible(true);

	}
}
