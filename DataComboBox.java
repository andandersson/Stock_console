import javax.swing.JComboBox;

public class DataComboBox extends JComboBox {
	private static String[] availableData = {"1. open", "2. high", "3. low", "4. close", "5. volume"};
	private static String[] adjustedData = {"1. open", "2. high", "3. low", "4. close", "5. adjusted close", "6. volume", "7. dividend amount", "8. split coefficient"};
	private static String[] adjusted2Data = {"1. open", "2. high", "3. low", "4. close", "5. adjusted close", "6. volume", "7. dividend amount"};
	static JComboBox<String> DataCBox = new JComboBox<>(availableData);

	public static JComboBox getBox() {
		return DataCBox;
	}

	public static String[] getSeries(int i) {
		if(i==0){
		return availableData;
		}
		else if(i==1){
			return adjustedData;
		}
		else{
			return adjusted2Data;
		}
	}

}
