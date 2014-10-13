import java.util.Arrays;
import java.util.Scanner;
import java.util.LinkedList;
import java.io.*;
public class Monkey {

	FrequencyMap distro;
	char[] firstn;

	public Monkey(int n) {
		distro = new FrequencyMap(n);
		firstn = new char[n];
	}

	public void processFile (String filename)
		throws FileNotFoundException, IOException {

		FileReader fileReader = new FileReader (filename);
		BufferedReader in = new BufferedReader (fileReader);

		boolean filled = false;
		while (true) {
			String s = in.readLine();
			if (s == null) break;
			if (!filled && s.length() >= firstn.length) {
				firstn = s.substring(0, firstn.length).toCharArray();
				filled = true;
			}
			processLine (s);
		}
		distro.lockDist();
	}

	public void processLine (String s) {
		//s = s.toLowerCase();
		for(int i = 0; i < s.length(); i++) {
			distro.addLetter(s.charAt(i));
		}
	}

	public char getLetter() { return distro.getRandomChar(); }

	public char getLetter(char c) { return distro.getRandomChar(c); }

	public char getLetter(char[] a) { return distro.getRandomChar(a); }

	private static char[] toCharArray(Character[] arr) {
		char[] newArr = new char[arr.length];
		for(int i = 0, n = arr.length; i < n; i++)
			newArr[i] = arr[i].charValue();
		return newArr;
	}

	public static void main (String[] args)
		throws FileNotFoundException, IOException {

		if (args.length < 3) {
			System.out.println ("Usage: java Monkey filename numLookback numPrinted.");
			System.exit (0);
		}

		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(args[1]);
		Monkey monkey = new Monkey(n);
		monkey.processFile(args[0]);

		// now generating letters
		
		int numLetters = Integer.parseInt(args[2]);

		// start with first n characters
		char[] array = monkey.firstn;
		for(int i = 0; i < Math.min(n, numLetters); i++)
			System.out.print(array[i]);
		
		
		int count = 0;

		// finite case
		if(numLetters >= 0) {
			for(int i = 0; i < numLetters - n; i++) {
				char c = monkey.getLetter(array);
				System.out.print(c);
				count++;
				if(count >= 100 && (c == '.' || c == '!' || c == '"' || c == '?')) {
					count = 0; System.out.println();
				}
				for(int j = 0; j < array.length-1; j++)
					array[j] = array[j+1];
				array[array.length-1] = c;
			}
			System.out.println();
		}
		// infinite case
		else {
			while(true) {
				char c = monkey.getLetter(array);
				System.out.print(c);
				count++;
				if(count >= 100 && (c == '.' || c == '!' || c == '"' || c == '?')) {
					count = 0; System.out.println();
				}
				for(int j = 0; j < array.length-1; j++)
					array[j] = array[j+1];
				array[array.length-1] = c;
			}
		}
	}
}
