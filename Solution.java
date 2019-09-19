import java.io.*;
import java.util.*;

import java.awt.Point;

public class Solution {
	// The minimum distance squared
	static long dminsq = Long.MAX_VALUE;

	public static void main(String[] args) {
		// The list of points that will be sorted by X values
		List<Point> points = new ArrayList<>();

		// The list of points that will be sorted by Y values
		List<Point> qpoints = new ArrayList<>();

		Scanner scan = new Scanner(System.in);

		long n = scan.nextLong();

		// Adding the input points to Points and QPoints
		for (long i = 0; i < n; i++) {
			Point p = new Point();
			p.setLocation(scan.nextLong(), scan.nextLong());
			points.add(p);
			qpoints.add(p);

		}
		scan.close();
		// Sort points by Y first, and then by X
		sortByY(points);
		sortByX(points);
		// Sort the QPoints by X first and then by Y
		sortByX(qpoints);
		sortByY(qpoints);

		closestPair(points, qpoints);
		System.out.println(dminsq);

	}

	// Sorts the list of points by their X-Value
	private static void sortByX(List<Point> points) {
		Collections.sort(points, new Comparator<Point>() {
			public int compare(Point x1, Point x2) {
				if (x1.getX() < x2.getX())
					return -1;
				if (x1.getX() > x2.getX())
					return 1;
				return 0;
			}
		});
	}

	// Sorts the list of points by their Y-Value
	private static void sortByY(List<Point> points) {
		Collections.sort(points, new Comparator<Point>() {
			public int compare(Point y1, Point y2) {
				if (y1.getY() < y2.getY())
					return -1;
				if (y1.getY() > y2.getY())
					return 1;
				return 0;
			}
		});
	}
	
	//The Efficient Closest Pair Algorithm
	public static long closestPair(List<Point> p, List<Point> q) {

		// Base Case
		// if p.size() is less than or equal to 3, call the brute force
		// algorithm
		if (p.size() <= 3) {
			return bruteForce(p);
		} else {
			long w = 0;
			long n = p.size();
			long mid = (n / 2);
			List<Point> pl = new ArrayList<>();
			List<Point> ql = new ArrayList<>();
			List<Point> pr = new ArrayList<>();
			List<Point> qr = new ArrayList<>();

			// Add the left half of the points of P to pl and ql
			for (long i = 0; i < mid; i++) {

				pl.add((int) i, p.get((int) i));
				ql.add((int) i, p.get((int) i));
			}
			// Add the right half of the points of P to pl and ql
			for (long j = mid; j < p.size(); j++) {

				pr.add((int) w, p.get((int) j));
				qr.add((int) w, p.get((int) j));

				w++;
			}
			// Sort the ql and qr points by their Y coordinate
			sortByY(ql);
			sortByY(qr);
			List<Point> s = new ArrayList<>();
			long dl;
			long dr;
			long d;
			long m;

			dl = closestPair(pl, ql);

			dr = closestPair(pr, qr);

			d = Math.min(dl, dr);

			// m is the median between pl and pr
			m = p.get((int) ((n / 2) - 1)).x;

			// copy all of the points of q for which |x-m| < d into arrayList S
			long g = 0;
			for (long a = 0; a < q.size(); a++) {
				if ((Math.abs(q.get((int) a).x - m)) < (double) Math.sqrt(d)) {

					s.add((int) g, q.get((int) a));
					g = g + 1;

				}
			}

			dminsq = d;
			// Finding the minimum distance from the points in the S arraylist
			for (long u = 0; u < s.size(); u++) {
				for (long k = u + 1; k < s.size(); k++) {

					dminsq = (long) Math.min(dminsq, (Math.pow((s.get((int) k).x - s.get((int) u).x), 2)
							+ Math.pow((s.get((int) k).y - s.get((int) u).y), 2)));
					
					//System.out.println("Hello world");

					if ((Math.abs(s.get((int) k).y - Math.sqrt(d))) >= s.get((int) u).y) {
					//	System.out.println("Hello worldjr");
						break;
					}
				}

			}

		}
		return dminsq;
	}

	// The closest pair brute-force algorithm
	public static long bruteForce(List<Point> p) {
		long d = Long.MAX_VALUE;
		for (long i = 0; i < p.size(); i++) {
			for (long j = i + 1; j < p.size(); j++) {
				d = (long) Math.min(d, (Math.pow((p.get((int) i).x - p.get((int) j).x), 2)
						+ Math.pow((p.get((int) i).y - p.get((int) j).y), 2)));

			}
		}

		return d;

	}

}
