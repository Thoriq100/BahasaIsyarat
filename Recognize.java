import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import org.opencv.videoio.VideoCapture;
import javax.swing.SwingConstants;

public class Recognize extends JFrame implements WindowListener {
	static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmPengenalan;
	public static Recognize instance;
	public ImagePanel vpanel, imgPanel;
	public VideoCapture video;
	private MatOfByte matOfByte = new MatOfByte();
	private BufferedImage bufImage = null;
	private InputStream in;
	private Mat frameaux = new Mat();
	private Boolean begin = true;
	private List<String> character;
	private JLabel lblA;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					instance = new Recognize();
					instance.frmPengenalan.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static Recognize getInstance() {
		return instance;
	}

	/**
	 * Create the application.
	 */
	public Recognize() {
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
		frmPengenalan = new JFrame();
		frmPengenalan.setResizable(false);
		frmPengenalan.setTitle("PENGENALAN ABJAD JARI");
		frmPengenalan.setBounds(100, 100, 600, 500);
		frmPengenalan.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmPengenalan.getContentPane().setLayout(null);
		frmPengenalan.addWindowListener(this);
		
		vpanel = new ImagePanel(new ImageIcon("figs/320x240.gif").getImage());
		vpanel.setBounds(10, 11, 350, 350);
		frmPengenalan.getContentPane().add(vpanel);
		
		imgPanel = new ImagePanel(new ImageIcon("figs/320x240.gif").getImage());
		imgPanel.setBounds(405, 25, 160, 160);
		frmPengenalan.getContentPane().add(imgPanel);
		
		JLabel label = new JLabel("Image :");
		label.setBounds(405, 11, 46, 14);
		frmPengenalan.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Result :");
		label_1.setBounds(405, 196, 46, 14);
		frmPengenalan.getContentPane().add(label_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(405, 210, 160, 160);
		frmPengenalan.getContentPane().add(panel_2);
		
		lblA = new JLabel("A");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 72));
		lblA.setBounds(55, 39, 50, 75);
		panel_2.add(lblA);
		
		JButton button = new JButton("CAPTURE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rect rect = new Rect(0, 0, 350, 350);
				Mat crop = new Mat(frameaux, rect);
				Imgcodecs.imwrite("asset/result.jpg", crop);
				
				Mat resizeimage = new Mat();
				Size sz = new Size(160,160);
				Imgproc.resize( crop, resizeimage, sz );
				Imgcodecs.imwrite("asset/thumb.jpg", resizeimage);
				BufferedImage bufImg = null;
				try {
					bufImg = ImageIO.read(new File("asset/thumb.jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				imgPanel.updateImage(new ImageIcon(bufImg).getImage());
				
				Mat mat = crop;
				Mat matFrame = new Mat();
				Imgproc.cvtColor(mat, matFrame, Imgproc.COLOR_BGR2GRAY);
	        	Imgproc.threshold(matFrame, matFrame, 100, 255, Imgproc.THRESH_BINARY_INV);
	        	Imgproc.Canny(matFrame, matFrame, 100, 255);
	        	
	        	Imgcodecs.imwrite("asset/testingA.jpg", matFrame);
	        	
	        	List<MatOfPoint> contours1 = new ArrayList<MatOfPoint>();
	        	Imgproc.findContours(matFrame, contours1, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
	        	System.out.println("Contour 1 : "+contours1.size());
	        	double[] minMatchValues = new double[25];
	        	
				for(int i = 0; i < 24; i++) {
					Mat sample = Imgcodecs.imread("asset/sample/"+(i+1)+".jpg");
					Mat frameSample = new Mat();
					Imgproc.cvtColor(sample, frameSample, Imgproc.COLOR_BGR2GRAY);
		        	Imgproc.threshold(frameSample, frameSample, 100, 255, Imgproc.THRESH_BINARY_INV);
		        	Imgproc.Canny(frameSample, frameSample, 100, 255);
		        	
		        	Imgcodecs.imwrite("asset/testing"+(i+1)+".jpg", frameSample);
		        	
		        	List<MatOfPoint> contours2 = new ArrayList<MatOfPoint>();
		        	Imgproc.findContours(frameSample, contours2, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		        	
		        	System.out.println("\nBandingkan Image "+character.get(i));
		        	System.out.println("Contour2 : "+contours2.size());
		        	int length = 0;
		        	if(contours1.size() > contours2.size())
		        		length = contours2.size();
		        	else
		        		length = contours1.size();
		        	
		        	double minValue = 100;
		        	for(int j=0; j < length; j++) {
		        		double match = Imgproc.matchShapes(contours1.get(j), contours2.get(j), Imgproc.CV_CONTOURS_MATCH_I2, 0);
		        		System.out.printf("Match: %f\n", match);
		        		if(match < minValue && match != 0) {
		        			minValue = match;
		        		}
		        	}
		        	
		        	if(minValue == 100)
		        		minMatchValues[i] = 0;
		        	else
		        		minMatchValues[i] = minValue;
				}
				
				int indexMinValue = 0;
				double minValue = minMatchValues[0];
				for(int i = 1; i < 24; i++) {
					if(minMatchValues[i] < minValue) {
						minValue = minMatchValues[i];
						indexMinValue = i;
					}
				}
				
				lblA.setText(character.get(indexMinValue));
				System.out.println(character.get(indexMinValue));
			}
		});
		button.setBounds(10, 372, 350, 44);
		frmPengenalan.getContentPane().add(button);
		
		JLabel label_3 = new JLabel("FETTY AULIA SABATINI (2012081061)");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(10, 435, 555, 15);
		frmPengenalan.getContentPane().add(label_3);
		
		video = new VideoCapture(0);

	    if(video.isOpened())
	    {
	    	new CaptureThread().start();
	    	begin = true;
	    }
	}
	
	public void show() {
		frmPengenalan.setVisible(true);
	}
	
	class CaptureThread extends Thread {
	    @Override
	    public void run()
	    {
	    	if(video.isOpened())
	    	{
	    		while(begin == true)
	    		{
	    			Scalar min = new Scalar(0, 133, 77);
	    			Scalar max = new Scalar(255, 173, 127);
	    			Mat frame = new Mat();
	    			List<MatOfPoint> contours = new ArrayList<>();
	    			
		        	video.read(frameaux);
		        	Imgproc.cvtColor(frameaux, frame, Imgproc.COLOR_BGR2YCrCb);
		        	Core.inRange(frame, min, max, frame);

		        	Imgproc.findContours(frame, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		        	
		        	int length = contours.size();
		        	for(int i = 0;i < length; i++) {
		        		double area = Imgproc.contourArea(contours.get(i));
		        		if(area > 1000) {
		        			Imgproc.drawContours(frame, contours, i, new Scalar(0, 255, 0), 3);
		        		}
		        	}
		        	
		        	Mat mask = new Mat(frame.size(), CvType.CV_8U);
		        	Imgproc.threshold(frame, mask, 70, 255, Imgproc.THRESH_BINARY_INV);
		            Mat dn = new Mat(frame.size(), CvType.CV_8UC3);
		            Photo.inpaint(frame, mask, dn, 20, Photo.INPAINT_TELEA);
		        	
		        	Imgcodecs.imencode(".jpg", dn, matOfByte);
		        	byte[] byteArray = matOfByte.toArray();
	
		        	try
		        	{
		        		in = new ByteArrayInputStream(byteArray);
		        		bufImage = ImageIO.read(in);
		        	}
		        	catch(Exception ex)
		        	{
		        		ex.printStackTrace();
		        	}
	
		        	vpanel.updateImage(bufImage);
		          
		        	try{ Thread.sleep(5); } catch(Exception ex){}
	    		}
	    	}
	    }
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		begin = false;
		video.release();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
