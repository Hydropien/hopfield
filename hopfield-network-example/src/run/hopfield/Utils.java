package run.hopfield;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.informatics4kids.Picture;

public class Utils {

	public static void plotMatrix(double[][] matrix) {
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				System.out.printf("%f ", matrix[row][col]);
			}
			System.out.println();
		}
	}

	public static void plotVector(int[] vector) {
		for (int i = 0; i < vector.length; i++) {
			System.out.print(vector[i] + " ");
		}
		System.out.println();
	}

	public static String getContentFromFile(String filename) {
		String out = "";
		try {
			FileReader reader = new FileReader(filename);
			int data = reader.read();
			while (data != -1) {
				out += (char) data;
				data = reader.read();
			}
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	public static int[] convertFileToInt(String content) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < content.length(); i++) {
			char curr = content.charAt(i);
			if (curr == '1') {
				list.add(1);
				i += 1;
			} else if (curr == '-') {
				list.add(-1);
				i += 2;
			} else {
			}
		}
		return list.stream().mapToInt(Integer::intValue).toArray();
	}

	public static int[] extractVectorFromImage(Picture input) {
		int[] out = new int[input.heightY() * input.widthX()];
		for (int j = 0; j < input.heightY(); j++) {
			for (int i = 0; i < input.widthX(); i++) {
				out[(j * input.widthX()) + i] = binaryFromColor(input.getColor(i, j));
			}
		}
		return out;
	}

	public static int binaryFromColor(Color inputColor) {
		double avg = Math.round((inputColor.getBlue() + inputColor.getGreen() + inputColor.getRed()) / 3);
		return (avg < 128) ? 1 : -1;
	}

	public static Picture extractPictureFromVector(int[] img, int height) {
		int width = img.length / height;
		Picture pic = new Picture(width, height);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int val = img[(j * width) + i];
				if (val == 1) {
					pic.setColor(i, j, new Color(0, 0, 0));
				} else {
					pic.setColor(i, j, new Color(255, 255, 255));
				}
			}
		}
		return pic;
	}

	public static Picture corruptPicture(Picture input, double probability) {
		Random rand = new Random();
		Picture out = new Picture(input.widthX(), input.heightY());
		for (int i = 0; i < out.widthX(); i++) {
			for (int j = 0; j < out.heightY(); j++) {
				boolean corrupt = (rand.nextDouble() <= probability) ? true : false;
				if (corrupt) {
					Color tmp = input.getColor(i, j);
					if (tmp.equals(Color.black)) {
						out.setColor(i, j, Color.white);
					} else {
						out.setColor(i, j, Color.black);
					}

				} else {
					out.setColor(i, j, input.getColor(i, j));
				}
			}
		}
		return out;
	}

}
