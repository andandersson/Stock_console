import javax.swing.JComboBox;

public class OutputComboBox extends JComboBox {
		
		
		final static String[] availableOutputs = {"compact", "full"};
		
		static JComboBox<String> OutputBox = new JComboBox<>(availableOutputs);
		
		public static JComboBox getBox() {
			return OutputBox;
		}
		
		public static String[] getSeries() {
			return availableOutputs;
		}
		

	}

