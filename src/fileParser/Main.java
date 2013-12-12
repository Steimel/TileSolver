package fileParser;

import projectCore.*;
import solver.*;
import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		FileParser x = new FileParser();
		Puzzle p = x.parseFile("C:\\Users\\Student\\Desktop\\test4.txt");
		ArrayList<Solution> s = Solver.solve(p, null, true, true);
		System.out.println(s.get(0).getPieces());
	}

}
