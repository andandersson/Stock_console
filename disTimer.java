import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

public class disTimer implements ActionListener {
	JComponent target;




	public disTimer(JComponent target){
		this.target = target;
	}


	public void actionPerformed(ActionEvent e){
		target.setEnabled(true);

	}

}
