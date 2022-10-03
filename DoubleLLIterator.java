import java.util.ListIterator;
import java.lang.reflect.Field;
import java.util.NoSuchElementException;
/**
 * An iterator for our linked list.  The iterator permits looping without using nodes directly
 * @author <em>Ashraf Ibraheem</em>
 */
public class DoubleLLIterator<T> implements ListIterator<T> 
{
  /** list represents the list that this class is iterating over */
  DoubleLinkedList<T> list;
  
  /** nodeptr marks node before the cursor's current position as we are iterating */
  private DLNode<T> nodeptr = null;
  
  /** nodeptr2 marks the node after the cursor's current position as we are iterating */
  private DLNode<T> nodeptr2;
  
  /** Represents whether next() has been called last relative to the last call to previous() */
  private boolean calledNext = false;
  
  /** Represents whether previous() has been called last relative to the last call to next() */
  private boolean calledPrevious = false;
  
  /**
   * Create an iterator for a linked list
   * @param list The list that is being iterated over
   */
  public DoubleLLIterator(DoubleLinkedList<T> list)
  {
    this.list = list;
    nodeptr2 = list.getFront();
  }
  
  @Override
  /**
   * Adds an element before the cursor's current position in the list
   * @param element The element to be added to the list
   */
  public void add(T element)
  {
    if(nodeptr != null)
    {
      nodeptr.setNext(new DLNode<T>(element, nodeptr, nodeptr2));
      nodeptr = nodeptr.getNext();
      //Moves the back to the new back
      if(nodeptr2 == null)
        list.setBack(nodeptr);
    }
    else if(nodeptr2 != null)
    {
      nodeptr2.setPrevious(new DLNode<T>(element, nodeptr, nodeptr2));
      nodeptr = nodeptr2.getPrevious();
      //Moves the front to the new front
      if(nodeptr.getPrevious() == null)
        list.setFront(nodeptr);
    }
    else
    {
      list.setFront(new DLNode<T>(element, null, null));
      nodeptr = list.getFront();
    }
  }
  
  @Override
  /**
   * Returns true if there are elements after the cursor's position
   * @return true if there are elements after the cursor's position
   */
  public boolean hasNext() 
  {
    return nodeptr2 != null;
  }
  
  @Override
  /**
   * Returns true if there are elements before the cursor's position
   * @return true if there are elements before the cursor's position
   */
  public boolean hasPrevious() 
  {
    return nodeptr != null;
  }
  
  @Override
  /**
   * Returns the next element of the list and moves the cursor forward
   * @return the next element of the list
   */
  public T next()
  {
    if(nodeptr2 == null)
      throw new NoSuchElementException();
    nodeptr = nodeptr2;
    nodeptr2 = nodeptr2.getNext();
    calledNext = true;
    calledPrevious = false;
    return nodeptr.getElement();
  }
  
  @Override
  /**
   * Returns the previous element of the list and moves the cursor back
   * @return the previous element of the list
   */
  public T previous()
  {
    if(nodeptr == null)
      throw new NoSuchElementException();
    nodeptr2 = nodeptr;
    nodeptr = nodeptr.getPrevious();
    calledPrevious = true;
    calledNext = false;
    return nodeptr2.getElement();
  }
  
  @Override
  /**
   * Replaces the last element returned by either method, next or previous, with the given element
   * @param element The element to replace the last element given by either next or previous
   */
  public void set(T element)
  {
    if(calledPrevious)
      nodeptr2.setElement(element);
    if(calledNext)
      nodeptr.setElement(element);
  }
  
  @Override
  /**
   * Unused - throws UnsupportedOperationException
   */
  public void remove()
  {
    throw new UnsupportedOperationException();
  }
 
  @Override
  /**
   * Unused - throws UnsupportedOperationException
   */
  public int previousIndex()
  {
    throw new UnsupportedOperationException();
  }
  
  @Override
  /**
   * Unused - throws UnsupportedOperationException
   */
  public int nextIndex()
  {
    throw new UnsupportedOperationException();
  }
  
}