import java.util.*;

public class FrequencyMap implements FrequencyDist {
  
  private HashMap map;
  private HashMap<Character,Integer> totalMap;
  private boolean locked;
  private char[] lastChars;
  
  public FrequencyMap(int n) {
    totalMap = new HashMap<Character,Integer>();
    if(n == 0) {
      map = new HashMap<Character,Integer>();
    } else {
      map = new HashMap<Character,FrequencyMap>();
    }
    lastChars = new char[n];
    Arrays.fill(lastChars, ' ');
    locked = false;
  }
  
  public void addLetter(char c) {
    if(locked) {
      System.out.println("Error: FrequencyMap is locked.");
      return;
    }
    Character character = new Character(c);
    
    //for totalMap
    Integer maybe = totalMap.get(character);
    if(maybe == null)
      totalMap.put(character, new Integer(1));
    else {
      Integer alreadyExisted = new Integer(maybe.intValue() + 1);
      totalMap.put(character, alreadyExisted);
    }
    
    // for mapception
    FrequencyMap currMap = this;
    int index = 0;
    while(currMap.depth() > 0) {
      Character prevChar = getPrevChar(index);
      FrequencyMap potentialCurrMap = (FrequencyMap) (currMap.map.get(prevChar));
      if(potentialCurrMap == null) {
        currMap.map.put(prevChar, new FrequencyMap(currMap.depth()-1));
        currMap = (FrequencyMap) currMap.map.get(prevChar);
      } else {
        currMap = (FrequencyMap) currMap.map.get(prevChar);
      }
      index++;
    }
    Integer potVal = (Integer) currMap.map.get(character);
    if(potVal == null)
      currMap.map.put(character, new Integer(1));
    else {
      Integer currVal = new Integer(potVal.intValue()+1);
      currMap.map.put(character, currVal);
    }
    // character has now been added to the map stuff, now needs to be added to lastChars
    if(lastChars.length > 0) {
      for(int i = 0; i < lastChars.length-1; i++) {
        lastChars[i] = lastChars[i+1];
      }
      lastChars[lastChars.length-1] = c;
    }
  }
  
  public void lockDist() {
    //System.out.println("FrequencyMap is now locked");
    locked = true;
  }
  
  // picks a random character just based on totalMap
  public char getRandomChar() {
    if(!locked) {
      System.out.println("Please lock map before generating letters.");
      return '=';
    }
    Iterator iter = totalMap.values().iterator();
    int total = 0;
    while(iter.hasNext()) {
      total += ((Integer)(iter.next())).intValue();
    }
    int random = ((int)(Math.random()*total))+1;
    Set<Character> keys = totalMap.keySet();
    iter = keys.iterator();
    while(iter.hasNext()) {
      Character key = (Character) iter.next();
      int cum = cumulative(totalMap, key);
      if((random > (cum-totalMap.get(key))) && (random <= cum)) {
        return key.charValue();
      }
    }
    return '=';
  }
  
  public char getRandomChar(char c) {
    char[] a = new char[1];
    a[0] = c;
    return getRandomChar(a);
  }
  
  public char getRandomChar(char[] a) {
    if(a.length != depth()) {
      System.out.println("Error: incorrect array length.");
      return '=';
    }
    if(!locked) {
      System.out.println("Please lock map before generating letters.");
      return '=';
    }
    int index = 0;
    FrequencyMap currMap = this;
    while(currMap.depth() > 0) {
      Character historyChar = new Character(a[index]);
      currMap = (FrequencyMap) currMap.map.get(historyChar);
      if(currMap == null) {
        //System.out.println("Error: these characters never appear consecutively. revert to using totalMap.");
        return getRandomChar();
      }
      index++;
    }
    Iterator iter = currMap.map.values().iterator();
    int total = 0;
    while(iter.hasNext()) {
      total += ((Integer)(iter.next())).intValue();
    }
    int random = ((int)(Math.random()*total))+1;
    Set<Character> keys = currMap.map.keySet();
    iter = keys.iterator();
    while(iter.hasNext()) {
      Character key = (Character) iter.next();
      int cum = cumulative(currMap.map, key);
      if(random > (cum-((Integer)currMap.map.get(key)).intValue()) && (random <= cum)) {
        return key.charValue();
      }
    }
    return '=';
  }
  
  public String toString() {
    String s = "Mapception: " + map.entrySet().toString();
    String t = "Total map: " + totalMap.entrySet().toString();
    return s + "\n" + t;
  }
  
  private int cumulative(HashMap<Character,Integer> m, Character c) {
    if(!m.containsKey(c)) {
      System.out.println("Error: character not found");
      return -1;
    }
    int sum = 0;
    Set<Character> keys = m.keySet();
    Iterator<Character> iter = keys.iterator();
    while(iter.hasNext()) {
      Character next = iter.next();
      int value = m.get(next).intValue();
      sum += value;
      if(c.equals(next)) {
        return sum;
      }
    }
    return -1;
  }
  
  private int depth() {
    return lastChars.length;
  }
  
  private Character getPrevChar(int i) {
    return new Character(lastChars[i]);
  }
  
  private String printArray(char[] a) {
    String s = "{ ";
    for(char c : a) {
      s += (c + " ");
    }
    s += "}";
    return s;
  }
  
}