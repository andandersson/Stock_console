import javax.swing.JComboBox;

public class IntervalComboBox extends JComboBox {
		
		
		final static String[] availableIntervals = {"1min", "5min", "15min", "30min", "60min"};
		
		static JComboBox<String> IntervalBox = new JComboBox<>(availableIntervals);
		
		public static JComboBox getBox() {
			return IntervalBox;
		}
		
		public static String[] getSeries() {
			return availableIntervals;
		}
		

	}

