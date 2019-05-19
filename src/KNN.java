import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A kNN classification algorithm implementation.
 * 
 */

public class KNN {

	/**
	 * In this method, you should implement the kNN algorithm. You can add other
	 * methods in this class, or create a new class to facilitate your work. If you
	 * create other classes, DO NOT FORGET to include those java files when
	 * preparing your code for hand in.
	 *
	 * Also, Please DO NOT MODIFY the parameters or return values of this method, or
	 * any other provided code. Again, create your own methods or classes as you
	 * need them.
	 * 
	 * @param trainingData
	 *            An Item array of training data
	 * @param testData
	 *            An Item array of test data
	 * @param k
	 *            The number of neighbors to use for classification
	 * @return The object KNNResult contains classification accuracy, category
	 *         assignment, etc.
	 */
	public KNNResult classify(Item[] trainingData, Item[] testData, int k) {

		int correct = 0;
		int total = 0;
		KNNResult temp = new KNNResult();
		temp.categoryAssignment = new String[testData.length];
		temp.nearestNeighbors = new String[testData.length][k];

		/* ... YOUR CODE GOES HERE ... */
		if (k <= 0) {
			System.out.println("k should be greater than 0!");
		}

		// for each test item in testData
		// find kNN in trainingData
		for (int i = 0; i < testData.length; i++) {
			List<Item> list = new ArrayList<Item>();
			Item test = testData[i];
			for (Item j : trainingData) {
				list.add(j);
			}
			int M = 0, N = 0, F = 0;
			Collections.sort(list, new Comparator<Item>() {
				public int compare(Item i1, Item i2) {
					return getDistance(test, i1) > getDistance(test, i2) ? 1
							: getDistance(test, i1) < getDistance(test, i2) ? -1 : 0;
				}
			});

			// get predicted category, save in KNNResult.categoryAssignment
			// save kNN in KNNResult.nearestNeighbors
			for (int q = 0; q < k; q++) {
				String newStr = new String(list.get(q).name);
				temp.nearestNeighbors[i][q] = newStr;
				if (list.get(q).category.equals("machine")) {
					M++;
				} else if (list.get(q).category.equals("fruit")) {
					F++;
				} else if (list.get(q).category.equals("gpe")) {
					N++;
				}
			}

			if (N >= F && N >= M) {
				temp.categoryAssignment[i] = "gpe";
			} else if (M >= F && M > N) {
				temp.categoryAssignment[i] = "machine";
			} else if (F > M && F > N) {
				temp.categoryAssignment[i] = "fruit";
			}

			if (temp.categoryAssignment[i].equals(test.category)) {
				correct++;
			}
			total++;
		}

		temp.accuracy = (double) correct / (double) total;
		return temp;
	}

	public double getDistance(Item i1, Item i2) {

		double distance;
		double x1 = 0, y1 = 0, z1 = 0, x2 = 0, y2 = 0, z2 = 0;
		double term1, term2, term3;

		x1 = i1.features[0];
		y1 = i1.features[1];
		z1 = i1.features[2];

		x2 = i2.features[0];
		y2 = i2.features[1];
		z2 = i2.features[2];

		term1 = Math.pow((x2 - x1), 2);
		term2 = Math.pow((y2 - y1), 2);
		term3 = Math.pow((z2 - z1), 2);
		double sum = term1 + term2 + term3;
		distance = Math.abs(Math.sqrt(sum));
		return distance;
	}
}
