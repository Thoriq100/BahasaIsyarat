import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class Sampling extends JFrame implements WindowListener {
	static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmSampling;
	private MatOfByte matOfByte = new MatOfByte();
	private BufferedImage bufImage = null;
	private InputStream in;
	private Mat frameaux = new Mat();
	private VideoCapture video = null;
	private ImagePanel vpanel, previewPanel;
	private List<String> character;
	
	public static Sampling instance;
	private JLabel label;
	private JLabel lblInputNama;
	private JTextField txtName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					instance = new Sampling();
					instance.frmSampling.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static Sampling getInstance() {
		return instance;
	}

	/**
	 * Create the application.
	 */
	public Sampling() {
		character = new ArrayList<String>();
		character.add("A");
		character.add("B");
		character.add("C");
		character.add("D");
		character.add("E");
		character.add("F");
		character.add("G");
		character.add("H");
		character.add("I");
		character.add("K");
		character.add("L");
		character.add("M");
		character.add("N");
		character.add("O");
		character.add("P");
		character.add("Q");
		character.add("R");
		character.add("S");
		character.add("T");
		character.add("U");
		character.add("V");
		character.add("W");
		character.add("X");
		character.add("Y");
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSampling = new JFrame();
		frmSampling.setResizable(false);
		frmSampling.setTitle("PENGENALAN ABJAD JARI");
		frmSampling.setBounds(100, 100, 600, 400);
		frmSampling.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSampling.getContentPane().setLayout(null);
		frmSampling.addWindowListener(this);
		
		vpanel = new ImagePanel(new ImageIcon("figs/320x240.gif").getImage());
		vpanel.setBounds(10, 11, 350, 350);
		frmSampling.getContentPane().add(vpanel);
		
		JButton btnCapture = new JButton("CAPTURE");
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rect rect = new Rect(0, 0, 350, 350);
				Mat crop = new Mat(frameaux, rect);
				
				Mat resizeimage = new Mat();
				Size sz = new Size(150,150);
				Imgproc.resize(crop, resizeimage, sz);
	        	Imgcodecs.imwrite("asset/preview.jpg", resizeimage);
	        	BufferedImage bufImg = null;
				try {
					bufImg = ImageIO.read(new File("asset/preview.jpg"));
				} catch (IOException ioe) {
					// TODO Auto-generated catch block
					ioe.printStackTrace();
				}
				
				previewPanel.updateImage(new ImageIcon(bufImg).getImage());
			}
		});
		btnCapture.setBounds(402, 11, 150, 35);
		frmSampling.getContentPane().add(btnCapture);
		
		label = new JLabel("FETTY AULIA SABATINI (2012081061)");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(370, 346, 216, 15);
		frmSampling.getContentPane().add(label);
		
		lblInputNama = new JLabel("INPUT NAMA :");
		lblInputNama.setBounds(402, 57, 150, 14);
		frmSampling.getContentPane().add(lblInputNama);
		
		txtName = new JTextField();
		txtName.setBounds(402, 71, 150, 20);
		frmSampling.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JButton btnSimpan = new JButton("SIMPAN");
		btnSimpan.setBounds(402, 102, 150, 35);
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtName.getText().length() > 0) {
					int reply = JOptionPane.showConfirmDialog(null, "APAKAH ANDA AKAN MENYIMPANNYA?", "SAVE", JOptionPane.YES_NO_OPTION);
				    if (reply == JOptionPane.YES_OPTION)
				    {
				    	Mat copy = Imgcodecs.imread("asset/preview.jpg");
						Imgcodecs.imwrite("asset/sample/"+txtName.getText()+".jpg", copy);
				    }
				} else {
					JOptionPane.showMessageDialog(null, "MASUKKAN NAMA FILE !");
				}
			}
		});
		frmSampling.getContentPane().add(btnSimpan);
		
		JButton btnKembali = new JButton("KEMBALI");
		btnKembali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home.show();
				instance.frmSampling.dispose();
			}
		});
		btnKembali.setBounds(402, 148, 150, 35);
		frmSampling.getContentPane().add(btnKembali);
		
		previewPanel = new ImagePanel(new ImageIcon("figs/320x240.gif").getImage());
		previewPanel.setBounds(402, 194, 150, 150);
		frmSampling.getContentPane().add(previewPanel);
		
		video = new VideoCapture(0);

	    if(video.isOpened())
	    {
	    	new CaptureThread().start();
	    }
	}
	
	public void show() {
		frmSampling.setVisible(true);
	}
	
	class CaptureThread extends Thread {
	    @Override
	    public void run()
	    {
	    	while(true) {
	    		if(video.isOpened()) {
	    			video.read(frameaux);
	    			//	          video.retrieve(frameaux);
	    			//	          Imgproc.resize(frameaux, frame, frame.size());
	    			//			  Imgproc.cvtColor(frameaux, frame, Imgproc.COLOR_BGR2GRAY);
	    			//	          Imgproc.GaussianBlur(frame, frame, new Size(7,7), 1,1);
	    			//	          int rows = frame.rows();
	    			//	          for(int r=0; r<rows; ++r){
	    			//	        	  int cols = frame.cols();
	    			//	  			for(int c=0; c<cols; ++c) 
	    			//	  				// 0<H<0.25  -   0.15<S<0.9    -    0.2<V<0.95   
	    			//	  				if( (frame.get(r, c)[0]>5) && (frame.get(r,c)[0] < 17) && (frame.get(r,c)[1]>38) && (frame.get(r,c)[1]<250) && 
	    			//	  						(frame.get(r,c)[2]>51) && (frame.get(r,c)[2]<242) ); // do nothing
	    			//	  				else 
	    			//	  					for(int i=0; i<3; ++i)	
	    			//	  						frame.get(r,c)[i] = 0;
	    			//	          }
	    			//	          
	    			//	          /* BGR CONVERSION AND THRESHOLD */
	    			//	          Mat frame_gray = new Mat();
	    			//	          Imgproc.cvtColor(frame, frame, Imgproc.COLOR_HSV2BGR);
	    			//	          Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGR2GRAY);
	    			//	          Imgproc.threshold(frame_gray, frame_gray, 60, 255, Imgproc.THRESH_BINARY);
	    			//	          Imgproc.morphologyEx(frame_gray, frame_gray, Imgproc.MORPH_ERODE, new Mat(3,3,1), new Point(-1, -1), 3);
	    			//	          Imgproc.morphologyEx(frame_gray, frame_gray, Imgproc.MORPH_OPEN, new Mat(7,7,1), new Point(-1, -1), 1);
	    			//	          Imgproc.morphologyEx(frame_gray, frame_gray, Imgproc.MORPH_CLOSE, new Mat(9,9,1), new Point(-1, -1), 1);
	    			//	          
	    			//	          Imgproc.medianBlur(frame_gray, frame_gray, 15);
	    			////	          Imgproc.imshow("Threshold", frame_gray);
	    			//	          Imgcodecs.imencode(".jpg", frame, matOfByte);
	    			//	          
	    			//	          Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2HSV);
	    				          
	    			//	          int value = threshold.getValue();
	    			//	          Imgproc.Canny(frame, frame, value, value*5, 5, false);
	    			//	          Imgproc.Canny(frame, frame, 10, 300, 3, false);
	    			//	          Mat dest = new Mat();
	    			//	          Core.add(dest, Scalar.all(0), dest);
	    			//	          frame.copyTo(dest, frame);
	    			//	          
	    			Imgcodecs.imencode(".jpg", frameaux, matOfByte);
	    			byte[] byteArray = matOfByte.toArray();
	    			
	    			try {
	    				in = new ByteArrayInputStream(byteArray);
	    				bufImage = ImageIO.read(in);
	    			} catch(Exception ex) {
	    				ex.printStackTrace();
	    			}
	    			vpanel.updateImage(bufImage);
	    				          
	    			try{ Thread.sleep(5); } catch(Exception ex){}
	    		}
	    	}
	    }
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		video.release();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
