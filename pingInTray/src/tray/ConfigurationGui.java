package tray;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConfigurationGui extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTextField hostTextField;
	private JTextField intervalTextField;
	private NativePing np = new NativePing();

	public ConfigurationGui(){

		setTitle("Configure Ping In Tray");
		setSize(300, 400);
		ConfigurationPanel panel = new ConfigurationPanel();
		panel.add(new JLabel("Enter the host name"));
		hostTextField = new JTextField(40);
		panel.add(hostTextField);
		panel.add(new JLabel("Enter the interval"));
		intervalTextField = new JTextField(40);
		panel.add(intervalTextField);
		add(panel, BorderLayout.CENTER);

	}

	class ConfigurationPanel extends JPanel implements ActionListener {

		private static final long serialVersionUID = 1L;
		private Component frame;

		public ConfigurationPanel() {

			final JButton b2 = new JButton("Add A Host");
			add(b2, BorderLayout.SOUTH);
			b2.setActionCommand("addHost");
			b2.addActionListener(this);
			final JButton b3 = new JButton("Change Interval");
			add(b3, BorderLayout.SOUTH);
			b3.setActionCommand("addInterval");
			b3.addActionListener(this); 

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			// !! ButtonFrame bf = new ButtonFrame();
			if ("addHost".equals(e.getActionCommand())) {

				setHost(ConfigurationGui.this.hostTextField.getText());
				JOptionPane.showMessageDialog(frame,
						Configuration.getInstance().getHost());
			}
			if ("addInterval".equals(e.getActionCommand())) {

				String intervalString = ConfigurationGui.this.intervalTextField.getText();
				if (!intervalString.equals("")){
					int interval = Integer.parseInt(intervalString);
					if (interval >= 1 ) {
						setInterval(interval*1000);
					}
				}
				JOptionPane.showMessageDialog(frame,
						Configuration.getInstance().getInterval());
			}

		}

	}

	public void setHost(String host) {
		boolean isReachable = np.checkIsReachable(host);
		if(isReachable){ 
			Configuration.getInstance().setHost(host);
		}
	}

	public void setInterval(int interval) {
		Configuration.getInstance().setInterval(interval);
	}


}
