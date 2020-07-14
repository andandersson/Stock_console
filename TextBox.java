import javax.swing.*;

public class TextBox{
	public static JTextArea textArea = new JTextArea(500, 300);
	
	private static JScrollPane sp = new JScrollPane(textArea,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	public static JScrollPane getScrollPane() {
		return sp;
	}
	

}
