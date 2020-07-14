import javax.swing.*;

public class TimeSeriesComboBox extends JComboBox {
	final static String[] availableSeries = {"TIME_SERIES_INTRADAY", "TIME_SERIES_DAILY", 
			"TIME_SERIES_DAILY_ADJUSTED", "TIME_SERIES_WEEKLY", "TIME_SERIES_WEEKLY_ADJUSTED", 
			"TIME_SERIES_MONTHLY", "TIME_SERIES_MONTHLY_ADJUSTED"};
	
	static JComboBox<String> DataSeriesBox = new JComboBox<>(availableSeries);
	
	public static JComboBox getBox() {
		return DataSeriesBox;
	}
	
	public static String[] getSeries() { 
		return availableSeries;
	}
	

}
