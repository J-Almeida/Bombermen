package ui.cli;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import logic.Architect;
import logic.Dragon;
import logic.Maze;
import logic.MazeGenerator;
import logic.RandomMazeGenerator;

public class Statistics {
	public static void main(String[] args) throws IOException {
		BufferedWriter f = null;
		try {
			f = new BufferedWriter(new FileWriter("values" + System.currentTimeMillis() + ".csv"));
			//int[] mazeSizes = { 10, 20, 30, 50, 100, 200, 400, 800, 1000, 2000 };
			int[] maxes = { 50 };
			for (int mazeSize  = 6; mazeSize <= 1000; mazeSize++) {
				for (int max : maxes) {
					ArrayList<Double> props = new ArrayList<Double>();
					double average = 0.0;
					double timeavg;


					Architect architect = new Architect();
					MazeGenerator mg = new RandomMazeGenerator();
					architect.SetMazeGenerator(mg);
					Maze maze;

					long start = System.currentTimeMillis();
					for (int i = 0; i < max; i++) {
						architect.ConstructMaze(mazeSize, 2,
								Dragon.Behaviour.Sleepy);
						maze = architect.GetMaze();
						double m = (maze.GetNumberOfPathCells());
						props.add(m);
						average += m;
					}
					timeavg = (System.currentTimeMillis() - start);

					average /= max;
					timeavg /= max;
					double stddev = 0.0;

					for (double prop : props) {
						stddev += (prop - average) * (prop - average);
					}

					stddev /= max;

					f.write(mazeSize + ";" + Double.toString(average).replace('.', ',') + ";" + Double.toString(stddev).replace('.', ',')
							+ ";" + Double.toString(Math.sqrt(stddev)).replace('.', ',') + ";" + Double.toString(timeavg).replace('.', ',') + "\n");
					//System.out.println(mazeSize + ";" + average + ";" + stddev
						//	+ ";" + Math.sqrt(stddev));
					// System.out.println("Average of path cells in " + max +
					// " mazes of " + mazeSize + "x" + mazeSize + " is " +
					// average);
					f.flush();
				}
			}
		} finally {
			if (f != null) f.close();
		}

	}
}
