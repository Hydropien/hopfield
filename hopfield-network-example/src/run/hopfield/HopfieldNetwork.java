package run.hopfield;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class HopfieldNetwork {

	private int[] neurons;
	private double[][] weights;

	public int[] getNeurons() {
		return neurons;
	}

	public void setNeurons(int[] neurons) {
		this.neurons = neurons;
	}

	public double[][] getWeights() {
		return weights;
	}

	public void setWeights(double[][] weights) {
		this.weights = weights;
	}

	public int MAX_IMG;
	public int MIN_IMG;

	HopfieldNetwork() {
	}

	public void init(int neuronsNumber) {
		Random r = new Random();
		MAX_IMG = (int) Math.ceil(neuronsNumber * 0.15);
		MIN_IMG = (int) Math.floor(neuronsNumber * 0.13);
		neurons = new int[neuronsNumber];
		weights = new double[neuronsNumber][neuronsNumber];

		for (int i = 0; i < weights.length; i++) {
			neurons[i] = 0;
			for (int j = 0; j < weights[0].length; j++) {
				weights[i][j] = r.nextDouble();
			}
		}
	}

	public void init(int[] inputNeurons, double[][] inputWeights) {
		MAX_IMG = (int) Math.ceil(inputNeurons.length * 0.15);
		MIN_IMG = (int) Math.floor(inputNeurons.length * 0.13);
		this.neurons = inputNeurons;
		this.weights = inputWeights;
	}

	public void train(int[] newImage) {
		double[][] imageMatrix = new double[newImage.length][newImage.length];
		for (int i = 0; i < imageMatrix.length; i++) {
			imageMatrix[i][i] = 0;
			for (int j = 0; j < i; j++) {
				imageMatrix[i][j] = (double) (newImage[i] * newImage[j]);
				imageMatrix[j][i] = (double) (newImage[i] * newImage[j]);
			}
		}
		setWeights(MatrixMath.matrixAddition(getWeights(), imageMatrix));

	}

	public void removeIMG(int[] oldIamge) {
		double[][] imageMatrix = new double[oldIamge.length][oldIamge.length];
		for (int i = 0; i < imageMatrix.length; i++) {
			imageMatrix[i][i] = 0;
			for (int j = 0; j < imageMatrix.length; j++) {
				if (i == j) {
					imageMatrix[i][j] = 0;
				} else {
					imageMatrix[i][j] = oldIamge[i] * oldIamge[j];
				}
			}
		}
		weights = MatrixMath.matrixSubtraction(weights, imageMatrix);

	}

	public void resetWeights() {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[0].length; j++) {
				weights[i][j] = 0;
			}
		}
	}

	public void solve(int[] checkImage) {
		boolean finished = false;
		int iterations = 0;

		int[] prevImg = checkImage;
		int[] nextIMG = null;
		int[] osciIMG = null;
		neurons = prevImg;

		while (!finished) {
			updateNetwork();
			nextIMG = neurons;
			if (Arrays.equals(prevImg, nextIMG)) {
				finished = true;
			} else if (Arrays.equals(osciIMG, nextIMG)) {
				finished = true;
			} else {
				osciIMG = prevImg;
				prevImg = nextIMG;
				neurons = prevImg;
			}
			iterations++;
		}
		System.out.println("Iterations: " + (iterations - 1));
	}

	public void bruteSolve(int[] checkImage, int iterations) {
		neurons = checkImage;
		for (int i = 0; i < iterations; i++) {
			updateNetwork();
		}
	}

	public ArrayList<int[]> solveAndReturn(int[] checkImage) {
		ArrayList<int[]> steps = new ArrayList<int[]>();
		boolean finished = false;

		int[] prevImg = checkImage;
		int[] nextIMG = null;
		int[] osciIMG = null;
		neurons = prevImg;
		steps.add(this.getNeurons());

		while (!finished) {
			updateNetwork();
			nextIMG = neurons;
			if (Arrays.equals(prevImg, nextIMG)) {
				finished = true;
			} else if (Arrays.equals(osciIMG, nextIMG)) {
				finished = true;
			} else {
				osciIMG = prevImg;
				prevImg = nextIMG;
				neurons = prevImg;
			}
			steps.add(this.getNeurons());
		}
		return steps;
	}

	public ArrayList<int[]> bruteSolveAndReturn(int[] checkImage, int iterations) {
		ArrayList<int[]> steps = new ArrayList<int[]>();
		neurons = checkImage;

		for (int i = 0; i < iterations; i++) {
			steps.add(this.getNeurons());
			updateNetwork();
		}
		return steps;
	}

	public void updateNetwork() {
		/*
		 * Um das Netzwerk zu aktualisieren, muss man einfach nur eine
		 * Matrixmultiplikation zwischen der Gewichtsmatrix und dem Spaltenvektor der
		 * Neuronen machen. Dazu müssen wir aber ein paar Umformungen duchführen
		 * (Umwandlung von Zeilen in Spaltenvektoren und wieder zurück), damit das
		 * Ergebnis ein nutzbares Format hat bzw. überhauüt berechenbar ist
		 */
		int[] newNeurons = MatrixMath.rowVectorFromMatrix(
				MatrixMath.matrixMultiplication(weights, MatrixMath.columnVectorFromVector(neurons)));
		for (int i = 0; i < newNeurons.length; i++) {
			newNeurons[i] = activationFunction(newNeurons[i]);
		}
		neurons = newNeurons;
	}

	private int activationFunction(int input) {
		return (input >= 0) ? 1 : -1;
	}

	public void plot(boolean plotNeurons, boolean plotWeights) {
		if (plotNeurons) {
			Utils.plotVector(neurons);
		} else {
		}

		if (plotWeights) {
			Utils.plotMatrix(weights);
		} else {
		}
	}

}
