package util.collection;

/**
 * The Stack class is an abstract class used to implement a last-in-first-out
 * (LIFO) stack of objects. This collection does not permit null elements.
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 */

public abstract class Stack
{

	public static final int NULL_POSITION = -1;
	
	
	/**
	 * Pushes an element onto the top of this stack. Doesn't work if the specified
	 * element is null.
	 * 
	 * @param obj the element to be pushed onto this stack.
	 * @return the object argument if pushed, null otherwise.
	 * 
	 */
	public abstract Object push(Object obj);

	/**
	 * Removes the element at the top of this stack. Doesn't work if this stack is
	 * empty --> return null
	 * 
	 * @return the element at the top of this stack, null otherwise.
	 */
	public abstract Object pop();

	/**
	 * Returns the number of elements in this stack.
	 */
	public abstract int size();

	/**
	 * Tests if this stack is empty.
	 * 
	 * @return true if and only if this stack contains no items; false
	 *         otherwise.
	 */
	public abstract boolean isEmpty();

	/**
	 * Removes all of the elements from this stack: the stack will be empty
	 * after.
	 */
	public abstract void clear();

	/**
	 * Looks at the element at the top of this stack without removing it from
	 * the stack.
	 * 
	 * @return the element at the top of this stack. if this stack is empty -->
	 *         return null
	 */
	public abstract Object peek();

	/**
	 * Returns the 1-based position where an object is on this stack. If the
	 * object <tt>obj</tt> occurs as an item in this stack, this method returns
	 * the distance from the top of the stack of the occurrence nearest the top
	 * of the stack; the topmost item on the stack is considered to be at
	 * distance <tt>1</tt>. The <tt>equals</tt> method is used to compare
	 * <tt>obj</tt> to the items in this stack.
	 * 
	 * @param obj the desired object.
	 * @return the 1-based position from the top of the stack where the object
	 *         is located; the return value <code>-1</code> indicates that the
	 *         object is not on the stack.
	 */
	public abstract int search(Object obj);

	/**
	 * Returns the element at the specified position in this stack without
	 * removing it from the stack. The topmost element on the stack is
	 * considered to be at position 1.
	 * 
	 * @param position
	 *            position in this stack of element to return. if this position
	 *            is invalid for this stack --> return null ( position < 1 ||
	 *            position > this.size() )
	 * @return the element at the specified position
	 */
	public abstract Object get(int position);

	/**
	 * 	Retourne une représentation textuelle de la pile.
	 *  @return  représentation textuelle de la pile
	 */
	public abstract String toString();

}
