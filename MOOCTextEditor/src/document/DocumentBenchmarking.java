package document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * A class for timing the EfficientDocument and BasicDocument classes
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 */

public class DocumentBenchmarking
{

	public static void main(String[] args)
	{

		// Run each test more than once to get bigger numbers and less noise.
		// You can try playing around with this number.
		int trials = 100;

		// The text to test on
		String textfile = "data/warAndPeace.txt";

		// The amount of characters to increment each step
		// You can play around with this
		int increment = 20000;

		// The number of steps to run.
		// You can play around with this.
		int numSteps = 25;

		// THe number of characters to start with.
		// You can play around with this.
		int start = 50000;

		System.out.printf("%1$-15s\t%2$-10s\tEfficient Time%n", "Characters Read", "Naive Time");

		for (int numToCheck = start; numToCheck < numSteps * increment + start; numToCheck += increment)
		{
			String text = getStringFromFile(textfile, numToCheck);
			long start_time;
			double basic_time, efficient_time;

			start_time = System.nanoTime();
			for (int i = 0; i <= trials; i++)
			{
				BasicDocument test = new BasicDocument(text);
				test.getFleschScore();
			}

			basic_time = (System.nanoTime() - start_time) / 0x3B9ACA00;

			start_time = System.nanoTime();
			for (int i = 0; i <= trials; i++)
			{
				EfficientDocument test = new EfficientDocument(text);
				test.getFleschScore();
			}
			efficient_time = (System.nanoTime() - start_time) / 0x3B9ACA00;

			System.out.printf("%1$-15d\t%2$-10f\t%3$f%n", numToCheck, basic_time, efficient_time);
		}

	}

	/**
	 * Get a specified number of characters from a text file
	 * 
	 * @param filename
	 *            The file to read from
	 * @param numChars
	 *            The number of characters to read
	 * @return The text string from the file with the appropriate number of
	 *         characters
	 */
	public static String getStringFromFile(String filename, int numChars)
	{

		StringBuffer s = new StringBuffer();
		try
		{
			FileInputStream inputFile = new FileInputStream(filename);
			InputStreamReader inputStream = new InputStreamReader(inputFile);
			BufferedReader bis = new BufferedReader(inputStream);
			int val;
			int count = 0;
			while ((val = bis.read()) != -1 && count < numChars)
			{
				s.append((char) val);
				count++;
			}
			if (count < numChars)
			{
				System.out.println("Warning: End of file reached at " + count + " characters.");
			}
			bis.close();
		} catch (Exception e)
		{
			System.out.println(e);
			System.exit(0);
		}

		return s.toString();
	}

}
