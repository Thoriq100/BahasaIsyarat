import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import javax.swing.JTextField;

public class RecognizeByImage {
	static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
	
	public static RecognizeByImage instance;
	private JFrame frameRecognize;
	private JLabel txtResult;
	private String filename;
	private List<String> character;
	private ImagePanel imgTraining, imagePanel, imgTesting, imgGrayscale, imgThreshold, imgFeature;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					instance = new RecognizeByImage();
					instance.frameRecognize.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RecognizeByImage() {
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
	
	public static RecognizeByImage getInstance() {
		return instance;
	}
	
	public void show() {
		frameRecognize.setVisible(true);
	}
	
	public String getExtension(File f) {
		String ext = "";
		String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
        	ext = s.substring(i+1).toLowerCase();
        }
        
        return ext;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameRecognize = new JFrame();
		frameRecognize.setTitle("PENGENALAN ABJAD JARI");
		frameRecognize.setResizable(false);
		frameRecognize.setBounds(100, 100, 750, 620);
		frameRecognize.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameRecognize.getContentPane().setLayout(null);
		
		imagePanel = new ImagePanel(new ImageIcon("").getImage());
		imagePanel.setBounds(10, 63, 350, 350);
		frameRecognize.getContentPane().add(imagePanel);
		
		JButton btnBrowse = new JButton("...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(System.getProperty("user.dir")+"/asset/guide");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fc.showOpenDialog(instance.frameRecognize);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					filename = file.getPath();
					
					textField.setText(filename);
				}
			}
		});
		btnBrowse.setBounds(326, 21, 34, 20);
		frameRecognize.getContentPane().add(btnBrowse);
		
		JButton btnRecognize = new JButton("CAPTURE");
		btnRecognize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(System.getProperty("user.dir")+"/asset/guide");
				fc.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
					        return true;
					    }
						
						String extension = instance.getExtension(f);
				        
					    if (extension != "") {
					        if (extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png")) {
					                return true;
					        } else {
					            return false;
					        }
					    }
					    
						return false;
					}
				});
				int result = fc.showOpenDialog(instance.frameRecognize);
				Mat frame = new Mat();
				
				if(result == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					filename = file.getPath();
					
					frame = Imgcodecs.imread(filename);
					Mat resizeimage = new Mat();
					Size sz = new Size(350,350);
					Imgproc.resize(frame, resizeimage, sz );
					Imgcodecs.imwrite("asset/main.jpg", resizeimage);
					BufferedImage bufImg = null;
					try {
						bufImg = ImageIO.read(new File("asset/main.jpg"));
					} catch (IOException ioe) {
						// TODO Auto-generated catch block
						ioe.printStackTrace();
					}
					
					imagePanel.updateImage(new ImageIcon(bufImg).getImage());
					
					Mat testing = new Mat();
					Size small = new Size(160,160);
					Imgproc.resize(frame, testing, small);
					
					MatOfByte matOfByte = new MatOfByte();
					BufferedImage bufImage = null;
					InputStream in;
					Imgcodecs.imencode(".jpg", testing, matOfByte);
	    			byte[] byteArray = matOfByte.toArray();
	    			
	    			try {
	    				in = new ByteArrayInputStream(byteArray);
	    				bufImage = ImageIO.read(in);
	    			} catch(Exception ex) {
	    				ex.printStackTrace();
	    			}
					imgTesting.updateImage(bufImage);
				}
				
				Size small = new Size(160,160);
				
				Mat mat = frame;
				Mat matFrame = new Mat();
				Imgproc.cvtColor(mat, matFrame, Imgproc.COLOR_BGR2GRAY);
				
				Mat grayscale = new Mat();
				Imgproc.resize(matFrame, grayscale, small);
				
				MatOfByte matOfByte = new MatOfByte();
				BufferedImage bufImage = null;
				InputStream in;
				Imgcodecs.imencode(".jpg", grayscale, matOfByte);
    			byte[] byteArray = matOfByte.toArray();
    			
    			try {
    				in = new ByteArrayInputStream(byteArray);
    				bufImage = ImageIO.read(in);
    			} catch(Exception ex) {
    				ex.printStackTrace();
    			}
    			imgGrayscale.updateImage(bufImage);
    			
	        	Imgproc.threshold(matFrame, matFrame, 100, 255, Imgproc.THRESH_BINARY_INV);
	        	
	        	Mat threshold = new Mat();
				Imgproc.resize(matFrame, threshold, small);
				
	        	Imgcodecs.imencode(".jpg", threshold, matOfByte);
    			byteArray = matOfByte.toArray();
    			
    			try {
    				in = new ByteArrayInputStream(byteArray);
    				bufImage = ImageIO.read(in);
    			} catch(Exception ex) {
    				ex.printStackTrace();
    			}
    			imgThreshold.updateImage(bufImage);
	        	
	        	Imgproc.Canny(matFrame, matFrame, 100, 255);
	        	
	        	Mat feature = new Mat();
				Imgproc.resize(matFrame, feature, small);
				
	        	Imgcodecs.imencode(".jpg", feature, matOfByte);
    			byteArray = matOfByte.toArray();
    			
    			try {
    				in = new ByteArrayInputStream(byteArray);
    				bufImage = ImageIO.read(in);
    			} catch(Exception ex) {
    				ex.printStackTrace();
    			}
    			imgFeature.updateImage(bufImage);
	        	
	        	Mat resizeimage = new Mat();
				Imgproc.resize(matFrame, resizeimage, small);
	        	Imgcodecs.imwrite("asset/thumb.jpg", resizeimage);
	        	BufferedImage bufImg = null;
				try {
					bufImg = ImageIO.read(new File("asset/thumb.jpg"));
				} catch (IOException ioe) {
					// TODO Auto-generated catch block
					ioe.printStackTrace();
				}
				
				imgTraining.updateImage(new ImageIcon(bufImg).getImage());
	        	
	        	List<MatOfPoint> contours1 = new ArrayList<MatOfPoint>();
	        	Imgproc.findContours(matFrame, contours1, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
	        	System.out.println("Contour 1 : "+contours1.size());
	        	double[] minMatchValues = new double[25];
	        	
				for(int i = 0; i < 24; i++) {
					Mat sample = Imgcodecs.imread("asset/guide/"+(i+1)+".jpg");
					
					Mat training = new Mat();
					Imgproc.resize(sample, training, small);
					Imgcodecs.imencode(".jpg", training, matOfByte);
	    			byteArray = matOfByte.toArray();
	    			
	    			try {
	    				in = new ByteArrayInputStream(byteArray);
	    				bufImage = ImageIO.read(in);
	    			} catch(Exception ex) {
	    				ex.printStackTrace();
	    			}
	    			imgTraining.updateImage(bufImage);
					
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
				
				txtResult.setText(character.get(indexMinValue));
				System.out.println(character.get(indexMinValue));
			}
		});
		btnRecognize.setBounds(105, 424, 150, 25);
		frameRecognize.getContentPane().add(btnRecognize);
		
		JLabel label = new JLabel("TORIQ ZIADI RACHMAN (2015.1.06533)");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(170, 565, 564, 15);
		frameRecognize.getContentPane().add(label);
		
		JLabel lblImageTraining = new JLabel("Image Training");
		lblImageTraining.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageTraining.setBounds(402, 11, 160, 14);
		frameRecognize.getContentPane().add(lblImageTraining);
		
		imgTraining = new ImagePanel(new ImageIcon("").getImage());
		imgTraining.setBounds(402, 25, 160, 160);
		frameRecognize.getContentPane().add(imgTraining);
		
		JLabel lblHuruf = new JLabel("Huruf");
		lblHuruf.setHorizontalAlignment(SwingConstants.CENTER);
		lblHuruf.setBounds(574, 386, 160, 14);
		frameRecognize.getContentPane().add(lblHuruf);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(574, 400, 160, 160);
		frameRecognize.getContentPane().add(panel);
		
		txtResult = new JLabel("-");
		txtResult.setHorizontalAlignment(SwingConstants.CENTER);
		txtResult.setFont(new Font("Tahoma", Font.PLAIN, 72));
		txtResult.setBounds(0, 0, 160, 160);
		panel.add(txtResult);
		
		JLabel lblImageTesting = new JLabel("Image Testing");
		lblImageTesting.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageTesting.setBounds(574, 11, 160, 14);
		frameRecognize.getContentPane().add(lblImageTesting);
		
		imgTesting = new ImagePanel(new ImageIcon("").getImage());
		imgTesting.setBounds(574, 25, 160, 160);
		frameRecognize.getContentPane().add(imgTesting);
		
		imgGrayscale = new ImagePanel(new ImageIcon("").getImage());
		imgGrayscale.setBounds(402, 210, 160, 160);
		frameRecognize.getContentPane().add(imgGrayscale);
		
		JLabel lblImageGrayscale = new JLabel("Image Grayscale");
		lblImageGrayscale.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageGrayscale.setBounds(402, 196, 160, 14);
		frameRecognize.getContentPane().add(lblImageGrayscale);
		
		imgThreshold = new ImagePanel(new ImageIcon("").getImage());
		imgThreshold.setBounds(574, 210, 160, 160);
		frameRecognize.getContentPane().add(imgThreshold);
		
		JLabel lblImageThreshold = new JLabel("Image Threshold");
		lblImageThreshold.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageThreshold.setBounds(574, 196, 160, 14);
		frameRecognize.getContentPane().add(lblImageThreshold);
		
		imgFeature = new ImagePanel(new ImageIcon("").getImage());
		imgFeature.setBounds(402, 400, 160, 160);
		frameRecognize.getContentPane().add(imgFeature);
		
		JLabel lblFeatureExtraction = new JLabel("Feature Extraction");
		lblFeatureExtraction.setHorizontalAlignment(SwingConstants.CENTER);
		lblFeatureExtraction.setBounds(402, 386, 160, 14);
		frameRecognize.getContentPane().add(lblFeatureExtraction);
		
		JLabel lblNewLabel = new JLabel("Database Path :");
		lblNewLabel.setBounds(10, 24, 100, 14);
		frameRecognize.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(95, 21, 225, 20);
		frameRecognize.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnKembali = new JButton("KEMBALI");
		btnKembali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home.show();
				instance.frameRecognize.dispose();
			}
		});
		btnKembali.setBounds(10, 555, 150, 25);
		frameRecognize.getContentPane().add(btnKembali);
	}
}
