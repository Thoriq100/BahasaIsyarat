import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Help {

	private JFrame frmHelp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Help window = new Help();
					window.frmHelp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Help() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHelp = new JFrame();
		frmHelp.setResizable(false);
		frmHelp.setTitle("Help");
		frmHelp.setBounds(100, 100, 450, 300);
		frmHelp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmHelp.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("GUIDE");
		lblNewLabel.setBounds(10, 11, 46, 14);
		frmHelp.getContentPane().add(lblNewLabel);
		
		JTextArea txtrPertamaMasuk = new JTextArea();
		txtrPertamaMasuk.setLineWrap(true);
		txtrPertamaMasuk.setText("- Pertama Masuk ke menu Sampling untuk membuat sample data huruf A-Y agar akurasi pengenalan hurufnya lebih baik.\r\n- Dalam pembuatan samling sebaiknya diikuti dengan pencahayaan yang baik agar obyeknya lebih mudah untuk dikenali oleh sistem\r\n- Pada menu sampling di samping akan muncul gambar yang harus diikuti untuk setiap huruf dari A - Y.\r\n- Setelah semua sampling selesai dibuat maka program pengenalan bisa dilakukan dengan baik.");
		txtrPertamaMasuk.setRows(10);
		txtrPertamaMasuk.setBounds(10, 28, 414, 222);
		frmHelp.getContentPane().add(txtrPertamaMasuk);
	}
	
	public void show() {
		frmHelp.setVisible(true);
	}
}
