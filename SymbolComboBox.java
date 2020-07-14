import javax.swing.JComboBox;

public class SymbolComboBox extends JComboBox {
	

	final static String[] availableSymbols = {"MSFT", "T", "TXN",};
	
	static JComboBox<String> SymbolBox = new JComboBox<>(availableSymbols);
	
	public static JComboBox getBox() {
		return SymbolBox;
	}
	
	public static String[] getSeries() {
		return availableSymbols;
	}
	

}
