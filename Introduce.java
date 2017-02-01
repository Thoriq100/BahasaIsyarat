import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Introduce {
	public static Introduce instance;

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					instance = new Introduce();
					instance.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Introduce() {
		initialize();
	}
	
	public static void show() {
		instance.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnHome = new JButton("<html><center>Implementasi Metode Haar Like Feature Dan Metode PCA Dalam Pengenalan Abjad Jari Indonesia Untuk Tunarungu</center></html>");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home.main(null);
				Introduce.instance.frame.dispose();
			}
		});
		btnHome.setVerticalAlignment(SwingConstants.TOP);
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 29));
		btnHome.setBounds(10, 11, 414, 219);
		frame.getContentPane().add(btnHome);
		
		JLabel lblNewLabel = new JLabel("TORIQ ZIADI RACHMAN (2015.1.06533)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 238, 414, 14);
		frame.getContentPane().add(lblNewLabel);
	}
}
