import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class About {

	private JFrame frmAbout;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About window = new About();
					window.frmAbout.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public About() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAbout = new JFrame();
		frmAbout.setResizable(false);
		frmAbout.setTitle("About");
		frmAbout.setBounds(100, 100, 450, 300);
		frmAbout.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAbout.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("TORIQ ZIADI RACHMAN");
		lblNewLabel.setBounds(10, 11, 250, 14);
		frmAbout.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("NIM : 06533");
		lblNewLabel_1.setBounds(10, 36, 250, 14);
		frmAbout.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Teknik Informatika (S1)");
		lblNewLabel_2.setBounds(10, 61, 250, 14);
		frmAbout.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("TEKNIK INFORMATIKA");
		lblNewLabel_3.setBounds(10, 75, 250, 14);
		frmAbout.getContentPane().add(lblNewLabel_3);
		
		JLabel lblUniversitasKuningan = new JLabel("ITATS");
		lblUniversitasKuningan.setBounds(10, 94, 250, 14);
		frmAbout.getContentPane().add(lblUniversitasKuningan);
		
		JLabel lblNewLabel_4 = new JLabel("Copyright (c) 2016 | TORIQ ZIADI RACHMAN");
		lblNewLabel_4.setBounds(10, 119, 250, 14);
		frmAbout.getContentPane().add(lblNewLabel_4);
	}
	
	public void show() {
		frmAbout.setVisible(true);
	}
}
