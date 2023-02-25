package run.hopfield;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageViewer {
	private List<BufferedImage> images;
	private JFrame frame;
	private JSlider slider;
	private int imgIndex;
	private JLabel iterations;
	private JLabel imageLabel;
  
	public ImageViewer(List<BufferedImage> inImages) {
		this.images = inImages;
		imgIndex = 0;

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Image Viewer");

		imageLabel = new JLabel();
		imageLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(images.get(imgIndex)).getImage()
				.getScaledInstance(256, 256, Image.SCALE_SMOOTH)));

		slider = new JSlider(JSlider.HORIZONTAL, 0, images.size() - 1, 0);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				imgIndex = slider.getValue();
				imageLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(images.get(imgIndex)).getImage()
						.getScaledInstance(256, 256, Image.SCALE_SMOOTH)));
			}
		});

		iterations = new JLabel("Iterations: " + (images.size() - 1));
		iterations.setFont(iterations.getFont().deriveFont(20f));

		frame.add(imageLabel, BorderLayout.CENTER);
		frame.add(slider, BorderLayout.SOUTH);
		frame.add(iterations, BorderLayout.NORTH);

		frame.pack();

	}

	public void show(boolean show) {
		frame.setVisible(true);

	}
}