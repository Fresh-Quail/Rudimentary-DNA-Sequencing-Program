/**
 * A class that can represent DNA sequences
 * @author <em>Ashraf Ibraheem</em>
 */
public class DNA extends DoubleLinkedList<DNA.Base> implements Comparable<DNA>
{
  /** Represents the possible values of DNA's bases */
  public enum Base {
    A, T, G, C;
  }
  
  /**
   * Returns a DNA that represents the DNA sequence given by a string 
   * @param s A string representing a DNA sequence
   * @return A DNA that represents the DNA sequence given by a string
   */
  public static DNA string2DNA(String s) {
    //The DNA representing the given string
    DNA dna = new DNA();
    if(s.length() == 0)
      throw new IllegalArgumentException();
    //The loop's goal is to create a DNA that matches the given string
    //Precondition: The string must not be empty
    //Each iteration checks and adds a base to the string
    for(int i = 0; i < s.length(); i++)
    {
      if('A' == s.charAt(i))
        dna.addToBack(Base.A);
      else if('T' == s.charAt(i))
        dna.addToBack(Base.T);
      else if('G' == s.charAt(i))
        dna.addToBack(Base.G);
      else if('C' == s.charAt(i))
        dna.addToBack(Base.C);
      else
        throw new IllegalArgumentException();
    }
    return dna;
  }
  
  /**
   * Returns a string representation of the DNA
   * @return a string representation of the DNA
   */
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    for(Base base : this)
    {
      builder.append(base.toString());
    }
    return builder.toString();
  }
  
  /**
   * Removes a given number of bases from the given DNA and appends it to this DNA
   * @param dna The given strand of DNA
   * @param numbases The number of bases to remove from the given dna
   */
  public void splice(DNA dna, int numbases)
  {
    for(int i = 0; i < numbases; i++)
    {
      if(dna.getFront() != null)
        dna.removeFromFront();
    }
    if(!dna.isEmpty())
      append(dna);
  }
  
  /**
   * Returns true if a number of bases at the end of one DNA matches the bases at the start of another DNA
   * @param dna1 The first given DNA
   * @param dna2 The second given DNA
   * @param n The number of bases that must match at the start of dna1 and end of dna2
   * @return True if the number of bases at the end of one DNA match the bases at the start of another
   */
  public static boolean overlaps(DNA dna1, DNA dna2, int n)
  {
    //node1 points to the current base in dna1
    DLNode<DNA.Base> node1 = dna1.getBack();
    //node2 points to the current base in dna2
    DLNode<DNA.Base> node2 = dna2.getFront();
    
    //This loop's goal is to get the point at which the two DNAs are supposed to overlap
    //Precondition: The given number of bases must be greater than one
    //Each iteration decrements the current base in the DNA by one
    for(int i = 1; i < n; i++)
    {
      //If the DNA ends, there are not 'n' amount of matching bases
      if(node1 == null)
        return false;
      node1 = node1.getPrevious(); 
    }
    //If the current node is null, then 'n' is greater than dna1's length
    if(node1 == null)
        return false;
    
    //This loop's goal is to check whether the bases overlap in the two DNAs
    //Precondition: The given number of bases must be greater than zero
    //Each iteration checks if the current base in each DNA is equal
    for(int i = 0; i < n; i++)
    {
      //If the DNA ends, there are not 'n' amount of matching bases
      if(node2 == null)
        return false;
      if(node1.getElement() != node 2.getElement())
        return false;
      node1 = node1.getNext();
      node2 = node2.getNext();
    }
    return true;
  }
  
  @Override
  /**
   * Returns a negative, positive, or zero number if this DNA is less than, greater than, or equal to this DNA
   * @param dna The given DNA
   * @return A negative, positive, or zero number if this DNA is less than, greater than, or equal to this DNA
   */
  public int compareTo(DNA dna)
  {
    //Represents whether this DNA precedes the given alphabetically
    int alphaOrder = 0;
    
    //Represents if the alphabetical order has been checked
    boolean checked = false;
    
    //Represents the first base in this DNA
    DLNode<DNA.Base> thisNode = this.getFront();
    
    //Represents the first base in the given DNA
    DLNode<DNA.Base> dnaNode = dna.getFront();
    
    while(thisNode != null)
    {
      //If this DNA is longer, dnaNode is null
      if(dnaNode == null)
        return 1;
      
      //If the bases aren't the same, take the difference for alphabetic order
      if(Character.toUpperCase(thisNode.getElement().toString().charAt(0)) - Character.toUpperCase(dnaNode.getElement().toString().charAt(0)) != 0 && !checked)
      {
        alphaOrder = Character.toUpperCase(thisNode.getElement().toString().charAt(0)) - Character.toUpperCase(dnaNode.getElement().toString().charAt(0));
        checked = true;
      }
      dnaNode = dnaNode.getNext();
      thisNode = thisNode.getNext();
    }
    
    //If the given DNA is longer, dnaNode is should still exist
    if(dnaNode != null)
      return -1;
    
    return alphaOrder;
  }
  
  public static void main(String[] args)
  {
    try
    {
      //Represents the first given DNA
      DNA strand1 = string2DNA(args[0]);
      //Represents the second given DNA
      DNA strand2 = string2DNA(args[1]);
      
      int i = args[0].length();
      //This loop finds the number of bases that overlap from the end of strand1 and start of strand2
      //Precondition: They must have at least one overlapping base and the given strand must exist
      //Each iteration checks how many bases overlapping, incrementing the number of checked bases each time
      while(!overlaps(strand1, strand2, i))
        i--;
      
      int k = args[1].length();
      //This loop finds the number of bases that overlap from the end of strand1 and start of strand2
      //Precondition: They must have at least one overlapping base and the given strand must exist
      //Each iteration checks how many bases overlapping, incrementing the number of checked bases each time
      while(!overlaps(strand2, strand1, k))
        k--;
      
      if(i >= k)
      {
        strand1.splice(strand2, i);
        System.out.println(strand1.toString());
      }
      else
      {
        strand2.splice(strand1, k);
        System.out.println(strand2.toString());
      }
    }
    catch(IllegalArgumentException e)
    {
      System.out.println("Bad data was entered. The String could not be converted to DNA.");
    }
  }
                                 
                                 
}