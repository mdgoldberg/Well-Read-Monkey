import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
public class Monkey {
  
  FrequencyMap distro;
  
  public Monkey(int n) {
    distro = new FrequencyMap(n);
  }
  
  public void processFile (String filename)
    throws FileNotFoundException, IOException {
    
    FileReader fileReader = new FileReader (filename);
    BufferedReader in = new BufferedReader (fileReader);
    
    while (true) {
      String s = in.readLine();
      if (s == null) break;
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
    char[] array = new char[n];
    Arrays.fill(array, ' ');
    int count = 0;
    if(numLetters >= 0) {
      for(int i = 0; i < numLetters; i++) {
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
