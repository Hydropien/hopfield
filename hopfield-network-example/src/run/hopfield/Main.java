package run.hopfield;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import de.informatics4kids.Picture;
import de.informatics4kids.PictureViewer;

public class Main {

	/**
	 * 
	 * @author ricus
	 */
 
	/*
	 * Verbesserungen: Korrekte Behandlung von fehlerhaften Inputs
	 * 
	 * Man könnte die Vektoren und Matrizen zu eigenen Objekten machen
	 * 
	 * Einen Schutz, dass man das Netzwerk nicht mehr Bilder antrainieren kann als
	 * es mathematisch möglich ist (~13-15%)
	 * 
	 * Viele weitere Dinge
	 * 
	 * Nur zur Erklärung, warum ich doch static bei meinen Helferfunktionen genommen
	 * habe: Dort handelt bei der Klasse nicht um ein Objekt mit Eigenschaften,
	 * sondern lediglich um einen Zusammenschluss von nützlichen Funktionen, deshalb
	 * glaube ich, dass man dort static benutzen kann
	 * 
	 * Man könnte eine bessere Dokumentation hinzufügen
	 * 
	 * Man könnte das Laden von Bildern besser machen (z.B. per Gui)
	 * 
	 * 
	 */

	public static void main(String[] args) {

		ArrayList<BufferedImage> images = new ArrayList<>();

		HopfieldNetwork hop = new HopfieldNetwork();
		int[] p1 = Utils.extractVectorFromImage(new Picture("images/one.png"));
		int[] p2 = Utils.extractVectorFromImage(new Picture("images/two.png"));
		int[] p3 = Utils.extractVectorFromImage(new Picture("images/peace.png"));
		int[] p4 = Utils.extractVectorFromImage(new Picture("images/smiley.png"));

//		int[] p1 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
//				1, 1, 1, 1, 1, 1, 1 };
//		int[] p2 = { -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1,
//				-1, 1, -1, 1, -1, 1, -1, 1 };
//		int[] p3 = { -1, -1, -1, 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, 1,
//				1, 1, -1, -1, -1, 1, 1, 1 };
//		int[] p4 = { 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1,
//				1, -1, -1, -1, -1, -1, -1 };

		hop.init(p1.length);
		int height = (int) Math.sqrt(p1.length);

		hop.train(p1);
		hop.train(p2);
		hop.train(p3);
		hop.train(p4);

		ArrayList<int[]> imgArrays = hop.solveAndReturn(
				Utils.extractVectorFromImage(Utils.corruptPicture(Utils.extractPictureFromVector(p2, height), 0.3)));
		for (int i = 0; i < imgArrays.size(); i++) {
			images.add(Utils.extractPictureFromVector(imgArrays.get(i), height).getPicture());
		}
		ImageViewer viewer = new ImageViewer(images);
		viewer.show(true);

		int[] solve = hop.getNeurons();
		Picture p = new Picture(height, height);
		p = Utils.extractPictureFromVector(solve, height);

		PictureViewer pv = new PictureViewer(p.getPicture());
		pv.show();
		
//		Utils.plotMatrix(hop.getWeights());

	}

}
