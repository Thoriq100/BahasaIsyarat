import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Home {
	
	public static Home instance;

	private JFrame frmHome;
	private Sampling sampling;
//	private Recognize recognize;
	private RecognizeByImage recognizebyimage;
	private Help help;
	private About about;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					instance = new Home();
					instance.frmHome.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
	}
	
	public static void show() {
		instance.frmHome.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHome = new JFrame();
		frmHome.setTitle("PENGENALAN ABJAD JARI");
		frmHome.setResizable(false);
		frmHome.setBounds(100, 100, 600, 400);
		frmHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHome.getContentPane().setLayout(null);
		
		JButton btnSampling = new JButton("TRAINING");
		btnSampling.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSampling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(sampling == null) {
					Sampling.main(null);
					sampling = Sampling.getInstance();
				} else {
					sampling.show();
				}
				
				instance.frmHome.dispose();
			}
		});
		btnSampling.setBounds(50, 35, 200, 80);
		frmHome.getContentPane().add(btnSampling);
		
		JButton btnPengenalan = new JButton("TESTING");
		btnPengenalan.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPengenalan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(recognize == null) {
//					Recognize.main(null);
//					recognize = Recognize.getInstance();
//				} else {
//					recognize.show();
//				}
				
				if(recognizebyimage == null) {
					RecognizeByImage.main(null);
					recognizebyimage = RecognizeByImage.getInstance();
				} else {
					recognizebyimage.show();
				}
				
				instance.frmHome.dispose();
			}
		});
		btnPengenalan.setBounds(344, 35, 200, 80);
		frmHome.getContentPane().add(btnPengenalan);
		
		JLabel lblFettyAuliaSabatini = new JLabel("FETTY AULIA SABATINI (2012081061)");
		lblFettyAuliaSabatini.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFettyAuliaSabatini.setBounds(10, 346, 574, 15);
		frmHome.getContentPane().add(lblFettyAuliaSabatini);
		
		JButton btnHelp = new JButton("HELP");
		btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(help == null) {
					help = new Help();
					Help.main(null);
				} else {
					help.show();
				}
			}
		});
		btnHelp.setBounds(50, 140, 200, 80);
		frmHome.getContentPane().add(btnHelp);
		
		JButton btnAbout = new JButton("ABOUT");
		btnAbout.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(about == null) {
					about = new About();
					About.main(null);
				} else {
					about.show();
				}
			}
		});
		btnAbout.setBounds(344, 140, 200, 80);
		frmHome.getContentPane().add(btnAbout);
		
		JButton btnExit = new JButton("CLOSE");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "APAKAH ANDA AKAN KELUAR", "EXIT", JOptionPane.YES_NO_OPTION);
			    if (reply == JOptionPane.YES_OPTION)
			    {
			    	System.exit(0);
			    }
			}
		});
		btnExit.setBounds(200, 244, 200, 80);
		frmHome.getContentPane().add(btnExit);
	}
}
