package run.hopfield;

public class MatrixMath {

	public static double[][] matrixMultiplication(double[][] matrix1, double[][] matrix2) {
		boolean possibleMultiplication = false;
		possibleMultiplication = (matrix1[0].length == matrix2.length) ? true : false;
		if (possibleMultiplication) {
			double[][] resultMatrix = new double[matrix1.length][matrix2[0].length];
			for (int i = 0; i < matrix1.length; i++) {
				for (int j = 0; j < matrix2[0].length; j++) {
					resultMatrix[i][j] = 0;
					for (int k = 0; k < matrix1[0].length; k++) {
						resultMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
					}
				}
			}

			return resultMatrix;
		} else {
			return null;
		}
	}

	public static double[][] matrixAddition(double[][] matrix1, double[][] matrix2) {
		boolean possibleAddition = false;
		possibleAddition = ((matrix1.length == matrix2.length) && (matrix1[0].length == matrix2[0].length)) ? true
				: false;
		if (possibleAddition) {
			double[][] resultMatrix = new double[matrix1.length][matrix1[0].length];
			for (int i = 0; i < matrix1.length; i++) {
				for (int j = 0; j < matrix1[0].length; j++) {
					resultMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
				}
			}
			return resultMatrix;
		} else {
			return null;
		}
	}

	public static double[][] matrixSubtraction(double[][] matrix1, double[][] matrix2) {
		boolean possibleSubtraction = false;
		possibleSubtraction = ((matrix1.length == matrix2.length) && (matrix1[0].length == matrix2[0].length)) ? true
				: false;
		if (possibleSubtraction) {
			double[][] resultMatrix = new double[matrix1.length][matrix1[0].length];
			for (int i = 0; i < matrix1.length; i++) {
				for (int j = 0; j < matrix1[0].length; j++) {
					resultMatrix[i][j] = matrix1[i][j] - matrix2[i][j];
				}
			}
			return resultMatrix;
		} else {
			return null;
		}
	}

	public static double[][] columnVectorFromVector(int[] rowVector) {
		double[][] columnVector = new double[rowVector.length][1];
		for (int i = 0; i < rowVector.length; i++) {
			columnVector[i][0] = rowVector[i];
		}
		return columnVector;
	}

	public static int[] rowVectorFromMatrix(double[][] matrix) {
		if (matrix[0].length == 1) {
			int[] vector = new int[matrix.length];
			for (int i = 0; i < matrix.length; i++) {
				vector[i] = (int) matrix[i][0];
			}
			return vector;
		} else {
			return null;
		}
	}

}
