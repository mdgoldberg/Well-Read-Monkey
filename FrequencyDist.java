public interface FrequencyDist
{
     //This class calculates the frequency of characters,
     // and then generates random characters using the distribution
     // of characters that were supplied.
     // There are two phases: the adding characters phase, and generation phase
     // The class automatically starts out in the adding character phase
     // Once the method lockDist() is called, adding letter will no longer
     // affect the distribution, and random letter can be generated
     //  (no letters can be generated before lockDist() is called
     //
     // characters can include punctuation, spaces, digits, etc.
     // 
  
     //add a characer to the Frequency Count
     void addLetter(char c);
     void lockDist();
     
     //depending on your implementation, you will need one of these;
     // either you generate a random characer, or
     // you geneerate a random character given a character.
     char getRandomChar();
     char getRandomChar(char c);
     
}