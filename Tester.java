import org.junit.*;
import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.reflect.Field;
/**
 * JUnit Tester
 */
public class Tester
{
  //DoubleLinkedList Tests
  @Test
  public void testEquals() throws IllegalArgumentException
  {
    DNA strand1 = new DNA();
    DNA strand2 = new DNA();
    
    //Test 1 - Tests zero 
    //Tests zero bases
    assertTrue("Test zero", strand1.equals(new DNA()));
    assertFalse("Test zero", strand1.equals(new DNA().string2DNA("C")));
    
    //Test 2 - Tests one
    //Tests DNAs with one Base
    strand1 = strand1.string2DNA("A");
    assertFalse("Test one", strand1.equals(new DNA().string2DNA("G")));
    assertTrue("Test one", strand1.equals(new DNA().string2DNA("A")));
    
    //-----------------------------------------------------------
    //Group 1 - Tests many - Tests lists with lengths of many
    //Test 1 - Tests first base
    strand1 = strand1.string2DNA("AGGTCAGT");
    strand2 = strand2.string2DNA("TTTTTTTT");
    assertFalse("Test first", strand1.equals(strand2));
    
    //Test 2 - Tests middle base
    strand2 = strand2.string2DNA("AGGTTTTT");
    assertFalse("Test middle", strand1.equals(strand2));
    
    //Test 3 - Tests last base
    strand2 = strand2.string2DNA("AGGTCAGA");
    assertFalse("Test last", strand1.equals(strand2));
    
    //Test 4 - Tests if correctly returns true for lists of many
    strand2 = strand2.string2DNA("AGGTCAGT");
    assertTrue(strand1.equals(strand2));
    
    //------------------------
    //Group 2 - Tests lists with different lengths
    //Test 1 - When the second list is longer
    strand2 = strand2.string2DNA("AGGTCAGTT");
    assertFalse("Tests when the second list is longer", strand1.equals(strand2));
    
    //Test 2 - The first list is longer
    assertFalse("Tests when the first list is longer", strand2.equals(strand1));
    
  }
  
  @Test
  public void testAppend() throws IllegalArgumentException
  {
    DNA strand1 = new DNA().string2DNA("AGGTCAGT");
    DNA strand2 = new DNA().string2DNA("AGTCTTGA");
    strand1.append(strand2);
    //Test 1 - Tests two full DNAS
    assertEquals("Tests two DNAS", "AGGTCAGTAGTCTTGA", strand1.toString());
    
    //Test 2 - Tests one empty DNA and a full DNA
    strand2 = new DNA();
    strand2.append(strand1);
    assertEquals("Tests one DNA and an empty DNA", strand1, strand2);
    //Test 3 - Tests two empty DNA
    strand1 = new DNA();
    strand2 = new DNA();
    strand1.append(strand2);
    assertEquals("Tests two empty DNA", new DNA(), strand1);
  }
  
  @Test
  public void testIterator()
  {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToFront(3);
    list.addToFront(2);
    list.addToFront(1);
    DoubleLLIterator it = list.iterator();
    
    //Test 1 - Tests if the DoubleLLIterator is correctly returned
    assertEquals(new Integer(1), it.next());
    assertEquals(new Integer(2), it.next());
    assertEquals(new Integer(3), it.next());
  }
  
  //------------------------------------------------------------------------------------
  //DoubleLLIterator tests
  @Test
  public void testDoubleLLIteratorConstructor() throws NoSuchFieldException, IllegalAccessException
  {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToFront(3);
    list.addToFront(2);
    list.addToFront(1);
    DoubleLLIterator<Integer> it = new DoubleLLIterator<Integer>(list);
    
    //Test 1 - Tests if variable nodeptr2 is correctly initialized by the constructor
    Field node = it.getClass().getDeclaredField("nodeptr2");
    node.setAccessible(true);
    assertEquals(list.getFront(), node.get(it));
    
    //Test 2 - Tests if the double linked list is correctly initialized in the constructor
    Field DLList = it.getClass().getDeclaredField("list");
    DLList.setAccessible(true);
    assertEquals(list, DLList.get(it));
  }
    
  @Test
  public void testAdd()
  {
    DoubleLinkedList<Integer> emptyList = new DoubleLinkedList<Integer>();
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToFront(72);
    
    //Tests empty - Tests when the given list is empty - last if branch
    DoubleLLIterator<Integer> it = emptyList.iterator();
    it.add(85);
    assertEquals(emptyList.getFront().getElement(), it.previous());
    
    //Test 1 - Tests when the nodeptr is null - first if branch
    it = list.iterator();
    it.add(43);
    assertEquals(list.getFront().getElement(), it.previous());
    
    //Test 2 - Tests when nodeptr2 is null - second if branch
    it.add(47);
    assertEquals(list.getFront().getElement(), it.previous());
  }
  
  @Test
  public void testHasNext_HasPrevious()
  {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToFront(4);
    DoubleLLIterator<Integer> it = list.iterator();
    
    //Test 1 - Tests if there are more elements in the forward direction
    assertTrue(it.hasNext());
    
    //Test 2 - Tests if there are no more elements in the forward direction
    it.next();
    assertFalse(it.hasNext());
    
    //Test 3 - Tests if there are more elements in the backwards direction
    assertTrue(it.hasPrevious());
    
    //Test 4 - Tests if there are no more elements in the backwards direction
    it.previous();
    assertFalse(it.hasPrevious());
  }
  
  @Test 
  public void testNext_Previous()
  {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToFront(4);
    DoubleLLIterator<Integer> it = list.iterator();
    
    //Test 1 - Tests if the next element is returned
    assertEquals(new Integer(4), it.next());
    
    //Test 2 - Tests if NoSuchElementException is correctly thrown on next()
    try
    {  
      it.next();
    }
    catch(NoSuchElementException e)
    {
    }
    catch(Exception t)
    {
      fail("Did not correctly throw exception");
    }
    
    //Test 3 - Tests if the previous element is returned
    assertEquals(new Integer(4), it.previous());
    
    //Test 4 - Tests if NoSuchElementException is correctly thrown on previous()
    try
    {  
      it.previous();
    }
    catch(NoSuchElementException e)
    {
    }
    catch(Exception t)
    {
      fail("Did not correctly throw exception");
    }
  }
  
  @Test
  public void testSet()
  {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToFront(5);
    DoubleLLIterator<Integer> it = list.iterator();
    
    //Test 1 - Tests if correctly sets when nothing has been called
    it.set(3);
    assertEquals(new Integer(5), it.next());
    
    //Test 2 - Tests if correctly sets on next()'s last call
    it.set(4);
    assertEquals(new Integer(4), it.previous());

    //Test 3 - Tests if correctly sets on previous()'s last call
    it.set(1);
    assertEquals(new Integer(1), it.next());
  }
  
  
  //-------------------------------------------------------------------------------------
  //DNA tests
  @Test
  public void testString2DNA() throws IllegalArgumentException
  {
    DNA dna = new DNA();
    
    //Test 1 - Tests DNAs with length of zero
    try
    {
      dna.string2DNA("");
    }
    catch(IllegalArgumentException e)
    {
    }
    catch(Exception f)
    {
      fail("Didn't correctly catch IllegalArgumentException");
    }
    
    //Test 2 - Tests DNAs with lengths of one
    DNA dna2 = new DNA();
    dna2.addToFront(DNA.Base.A);
    assertEquals("Tests DNAs with a length of one", dna2, dna.string2DNA("A"));
    
    //--------------------------------------------------
    //Test 3 - Tests DNAs with lengths of many
    //There is no need to test first, middle, last because it does not apply
    dna.addToBack(DNA.Base.C);
    dna.addToBack(DNA.Base.G);
    dna.addToBack(DNA.Base.T);
    dna.addToBack(DNA.Base.T);
    dna.addToBack(DNA.Base.A);
    assertEquals("Tests DNAs with a length of many", dna, dna.string2DNA("CGTTA"));
  }
  
  @Test
  public void testToString() throws IllegalArgumentException
  {
    //Test 1 - Tests zero Bases in the DNA
    assertEquals("", new DNA().toString());
    //Test 2 - Tests one Base in the DNA
    assertEquals("A", new DNA().string2DNA("A").toString());
    //Test 3 - Tests many Bases in the DNA
    assertEquals("AGGTA", new DNA().string2DNA("AGGTA").toString());
  }
  
  @Test
  public void testSplice() throws IllegalArgumentException
  {
    DNA empty = new DNA();
    DNA empty2 = new DNA();
    DNA oneBase = new DNA().string2DNA("G");
    DNA oneBase2 = new DNA().string2DNA("T");
    DNA manyBase = new DNA().string2DNA("CCCCT");
    DNA manyBase2 = new DNA().string2DNA("AAAAAAA");
    //Test 1 - Tests splicing DNAs with zero bases
    empty.splice(empty2, 3);
    assertEquals("Tests splicing an empty onto an empty", new DNA(), empty);
    oneBase.splice(empty, 2);
    assertEquals("Tests splicing an empty on a non-empty", "G", oneBase.toString());
    
    //Test 2 - Tests splicing DNAs with one base
    oneBase.splice(oneBase2, 0);
    assertEquals("Tests splicing an empty onto an empty", "GT", oneBase.toString());
    
    //----------------------------------------------------------------
    //Group 1 - Tests many bases
    //Tests splicing DNAs with multiple bases
    //Test 1 - Test First
    //Tests if first base is properly spliced
    manyBase.splice(manyBase2, 1);
    assertEquals("CCCCTAAAAAA", manyBase.toString());
    
    //Test 2 - Test Middle
    //Tests if middle bases are properly spliced
    manyBase = manyBase.string2DNA("CCCCTAAAAAA");
    manyBase2.splice(manyBase, 4);
    assertEquals("AAAAAATAAAAAA", manyBase2.toString());
    
    //Test 3 - Test Last
    //Tests if last base is properly spliced
    manyBase2 = manyBase2.string2DNA("AAAAAATAAAAAA");
    manyBase.splice(manyBase2, 12);
    assertEquals("TAAAAAAA", manyBase.toString());
    
    //Test 4 - Tests zero
    //Tests if properly spliced when removed bases are zero
    manyBase = new DNA().string2DNA("CCCC");
    manyBase.splice(new DNA().string2DNA("GTGT"), 0);
    assertEquals("CCCCGTGT", manyBase.toString());
    
    //Test 5 - Tests many
    //Tests when removed bases are greater than bases in the given DNA
    manyBase.splice(new DNA().string2DNA("CTAC"), 6);
    assertEquals("CCCCGTGT", manyBase.toString());
  }
  
  @Test
  public void testOverlaps()
  {
    DNA dna1 = new DNA().string2DNA("TTACCCGTGT");
    DNA dna2 = new DNA().string2DNA("TAAAAAAA");
    
    //Group 1 - Tests many
    //Tests DNAs with a length more than one
    //Test 1 - Tests First
    //Tests if the first bases overlap
    assertTrue(DNA.overlaps(dna1, dna2, 1));
    assertFalse(DNA.overlaps(dna1, dna2, 2));
    
    //Test 2 - Tests Middle
    //Tests if middle bases overlap
    dna2 = dna2.string2DNA("GTGTAAAA");
    assertTrue(DNA.overlaps(dna1, dna2, 4));
    assertFalse(DNA.overlaps(dna1, dna2, 5));
    
    //Test 3 - Tests Last
    //Tests if the last base can overlap
    dna2 = dna2.string2DNA("ACCCGTGT");
    assertTrue(DNA.overlaps(dna1, dna2, 8));
    assertFalse(DNA.overlaps(dna1, dna2, 9));
    
    //Test 4 - Tests many
    //Tests overlaps of lengths longer than the DNAs
    //If there aren't enough bases in the first or second DNA, return false
    assertFalse(DNA.overlaps(dna1, dna2, 9));
    assertFalse(DNA.overlaps(dna2, dna1, 9));
    assertFalse(DNA.overlaps(dna1, dna2, 15));
    
    //Test 5 - Tests one
    //Tests overlaps of one base
    assertTrue(DNA.overlaps(dna1.string2DNA("A"), dna2.string2DNA("A"), 1));
    
    //Test 6 - Tests zero
    //Tests overlaps of zero bases
    assertTrue(DNA.overlaps(dna1, dna2, 0));
    assertTrue(DNA.overlaps(dna1, new DNA(), 0));

    //----------------------------------------------------------------------------
    //Group 2 - Tests one
    //Tests DNAs of length one
    dna1 = new DNA().string2DNA("A");
    dna2 = new DNA().string2DNA("A");
    DNA dna3 = new DNA().string2DNA("G");
    assertTrue(DNA.overlaps(dna1, dna2, 1));
    assertFalse(DNA.overlaps(dna1, dna3, 1));
    
    //----------------------------------------------------------------------------
    //Group 3 - Tests zero
    //Tests DNAs of length zero
    assertFalse(DNA.overlaps(new DNA(), new DNA(), 1));
    assertFalse(DNA.overlaps(new DNA(), dna3, 1));
    
  }
  
  @Test
  public void compareTo()
  {
    DNA dna1 = new DNA().string2DNA("AGCCTCCA");
    DNA dna2 = new DNA().string2DNA("TTTTTTTT");
    
    //Group 1 - Tests many
    //Tests DNA with lengths more than one
    //Test 1 - Test first
    //Tests if the first base is checked alphabetically
    assertTrue(dna1.compareTo(dna2) < 0);
    assertTrue(dna2.compareTo(dna1) > 0);
                 
    //Test 2 - Test middle
    //Tests if the middle base is checked alphabetically
    dna2 = dna2.string2DNA("AGCTTTTT");
    assertTrue(dna1.compareTo(dna2) < 0);
    assertTrue(dna2.compareTo(dna1) > 0);
    
    //Test 3 - Test last
    //Tests if the last base is checked alphabetically
    dna2 = dna2.string2DNA("AGCCTCCT");
    assertTrue(dna1.compareTo(dna2) < 0);
    assertTrue(dna2.compareTo(dna1) > 0);
    
    //Test 4 - Test equivalent DNAs
    dna2 = dna2.string2DNA("AGCCTCCA");
    assertTrue(dna1.compareTo(dna2) == 0);
    assertTrue(dna2.compareTo(dna1) == 0);
    
    //-----------------------------------------------------------
    //Group 2 - Tests one
    //Tests DNA with lengths of one
    dna1 = dna1.string2DNA("A");
    dna2 = dna2.string2DNA("T");
    assertTrue(dna1.compareTo(dna2) < 0);
    assertTrue(dna2.compareTo(dna1) > 0);
    assertTrue(dna1.compareTo(new DNA().string2DNA("A")) == 0);
    
    //------------------------------------------------------------
    //Group 3 - Test zero
    //Tests DNA with lengths of zero
    dna1 = dna1.string2DNA("AGCCTCCA");
    assertTrue(new DNA().compareTo(dna1) < 0);
    assertTrue(dna1.compareTo(new DNA()) > 0);
    assertTrue(new DNA().compareTo(new DNA()) == 0);
  }
    
  @Test
  public void testMain()
  {
    //Tests 1 - Tests zero
    //Tests a string DNA of length zero
    System.out.println("Test 1A Expected: Bad data was entered. The String could not be converted to DNA.");
    System.out.print("Test 1A Result: ");
    DNA.main(new String[]{"", ""});
    
    System.out.println("Test 1A Expected: Bad data was entered. The String could not be converted to DNA.");
    System.out.print("Test 1B Result: ");
    DNA.main(new String[]{"", "G"});
    
    //Test 2 - Tests one
    //Tests a string DNA of length one
    System.out.println("Test 2A Expected: AG");
    System.out.print("Test 2A Result: ");
    DNA.main(new String[]{"A", "G"});
    
    System.out.println("Test 2B Expected: G");
    System.out.print("Test 2B Result: ");
    DNA.main(new String[]{"G", "G"});
    
    //Group 1 - Tests many
    //Tests a string DNA of length many
    //Test 1 - Tests zero
    //Test overlap of zero
    System.out.println("\n----------------------------------\nGroup 1");
    System.out.println("Test 1A Expected: GGTCCA");
    System.out.print("Test 1A Result: ");
    DNA.main(new String[]{"GGT", "CCA"});
    
    //Test 2 - Test one
    //Tests overlaps of one
    System.out.println("Test 2A Expected: CCAGG");
    System.out.print("Test 2A Result: ");
    DNA.main(new String[]{"AGG", "CCA"});
    
    System.out.println("Test 2B Expected: CCAGG");
    System.out.print("Test 2B Result: ");
    DNA.main(new String[]{"CCA", "AGG"});
    
    //Test 3 - Test many
    //Tests overlaps of many
    System.out.println("Test 3A Expected: GGGGATAACCCC");
    System.out.print("Test 3A Result: ");
    DNA.main(new String[]{"GGGGATAA", "ATAACCCC"});
    
    System.out.println("Test 3B Expected: GGGGATAACCCC");
    System.out.print("Test 3B Result: ");
    DNA.main(new String[]{"ATAACCCC", "GGGGATAA"});
    
    //Test 4 - Tests overlaps of differing lengths
    System.out.println("Test 4A Expected: CCGGATAACCCC");
    System.out.print("Test 4A Result: ");
    DNA.main(new String[]{"CCGGATAA", "ATAACCCC"});
    
    System.out.println("Test 4B Expected: GGCCATAAGGGG");
    System.out.print("Test 4B Result: ");
    DNA.main(new String[]{"ATAAGGGG", "GGCCATAA"});
    
    //Test 5 - Tests input
    //Tests if given string is corretly parsed
    System.out.println("Test 5A Expected: Bad data was entered. The String could not be converted to DNA.");
    System.out.print("Test 5A Result: ");
    DNA.main(new String[]{"Down", "Bad"});
    
    //Test 6 - Tests example given in assigment
    System.out.println("Test 6A Expected: ATAATCGCTCACCTAT");
    System.out.print("Test 6A Result: ");
    DNA.main(new String[]{"CGCTCACCTAT", "ATAATCGCTC"});
  } 
}
