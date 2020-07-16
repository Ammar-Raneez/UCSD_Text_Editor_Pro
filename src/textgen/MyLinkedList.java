package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) {
		LLNode<E> nodeBeingAdded = new LLNode<E>(element);
		
		//null values ain't allowed
		if(element == null) {
			throw new NullPointerException("Null Values aren't allowed");
		}

		LLNode<E> current = head;	
		nodeBeingAdded.next = tail;	//added node will go in between head and tail
		tail.prev = nodeBeingAdded;
			
		//to traverse the list
		while(current.next.data != null) current = current.next;
		
		//update for traversal
		current.next = nodeBeingAdded;
		nodeBeingAdded.prev = current;
		
		//size of list increases upon each addition
		size++;
		return false;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) {
		//idea behind get is simple - we just loop thru all the elements keeping a counter for each index
		//when we reach the same value as the specified index, that particular node is returned
		
		LLNode<E> current = head;
		int listIndexCounter = 0;
		
		if (index < 0 || index>=this.size()) {
			throw new IndexOutOfBoundsException("Accessed index out of bounds");
		}
		
		//this loops till the end, excluding the last node (since it has data of null)
		while(current.next.data != null) {
			current = current.next;
			if(listIndexCounter == index) return current.data;
			listIndexCounter++;
		}
		
		//if we reached the last node
		if(current.next.data == null) return current.data;
		
		return null;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) {
		//the idea behind add is that
		//upon adding at a particular index, we shift everything which was already
		//in that index to the right, as such moving the linkedlist elements
		
		LLNode<E> nodeBeingAdded = new LLNode<>(element);
		
		if (element == null) {
			 throw new NullPointerException("Null values aren't allowed.");
		}
		
		if(index <0 || index>=this.size() && index!=0) {
			throw new IndexOutOfBoundsException("Accessed index out of bounds");
		}
		 
		LLNode<E> current = head;
		int linkedListIndex = 0;
		
		//if we wanna add a node to the beginning 
		if(index == 0) {
			current.next.prev=nodeBeingAdded; //element after the head is our new node
			nodeBeingAdded.prev=current; 	  //the node b4 that is therefore the head, and the node after it is the actual node at index 0	
			nodeBeingAdded.next=current.next;
			current.next=nodeBeingAdded;	  //the node at index 0 becomes the next node and the rest move one index to the right	
		}
		
		//traverse thru the enire list
		while(current.next.data != null) {
			 current = current.next;
			 linkedListIndex++;
			 
			 if(linkedListIndex == index) {
				 current.next.prev = nodeBeingAdded;
				 nodeBeingAdded.prev = current;
				 nodeBeingAdded.next = current.next;
				 current.next = nodeBeingAdded;
			 }
		}
		
		size++;
	}


	/** Return the size of the list */
	public int size() {
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		//the idea of remove is that we change the next and prev data fields
		//to point at the nodes before and after the removed node
		//therefore ignoring the removed node
		
		if(this.size == 0) throw new IndexOutOfBoundsException("No elements to remove");
		
		if(index <0 || index >= this.size()) {
			throw new IndexOutOfBoundsException("Accessed index out of bounds");
		}
		
		LLNode<E> current = head;
		int linkedListIndex = 0;
		
		while(current.next.data != null) {
			current = current.next;			
			linkedListIndex++;

			if(linkedListIndex == index+1) {
				current.next.prev = current.prev;
				current.prev.next = current.next;
				size--;
				return current.data;
			}
			
		}
		
		return null;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) {
		//the idea behind set is similar to adding, but much easier
		//all we have to do is change the data field of the particular node at the specified index
		//to the new data passed, there's no need for unnecessary "arrow" changes
		
		if(element == null) throw new NullPointerException("Null elements aren't allowed");
		if(index < 0 || index>=this.size()) throw new IndexOutOfBoundsException("Accessed inedx out of bounds");
		
		LLNode<E> current = head;
		int linkedListIndex = 0;
		
		if(index == 0) {
			E oldValue = current.next.data;
			current.next.data = element;
			return oldValue;
		}
		
		while(current.next.data != null) {
			current = current.next;
			E oldValue = current.data;
			linkedListIndex++;
			
			if(linkedListIndex == index+1) {
				current.data = element;
				return oldValue;
			}
		}
		return null;
	}   
	
	
	public String toString() {
        LLNode<E> current = head;  
        if(head == null) {  
            System.out.println("List is empty");  
        }  
        while(current != null) {
        	//check to ignore the sentinel nodes
        	if(current.data != null)
        		System.out.print(current.data + " ");  
            current = current.next;  
        }
        return "";
    }
	
	public static void main(String[] args) {
		/*MyLinkedList<String> sl = new MyLinkedList<>();
		sl.add("a"); sl.add("b");
		System.out.println(sl);
		
		MyLinkedList<Integer> s2 = new MyLinkedList<>();
		for(int i=0; i<10; i++) s2.add(i);
		System.out.println(s2);
	
		System.out.println("S1 Size: " + sl.size());
		System.out.println("S2 Size: " + s2.size());
		
		System.out.println("Index at 0 " + sl.get(0));
		System.out.println("Index at 1 " + sl.get(1));
		System.out.println("Index at 2 " + sl.get(2));
		
		MyLinkedList<Integer> s3 = new MyLinkedList<>();
		for(int i=0; i<5;i++) {
			s3.add(i);
		}
		s3.add(1,10);
		System.out.println(s3);
		
		MyLinkedList<Integer> s3 = new MyLinkedList<>();
		for(int i=0; i<5;i++) {
			s3.add(i);
		}
		System.out.println(s3);
		int data = s3.remove(2);
		System.out.println(data);
		System.out.println(s3);*/
	}
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// E.g. you might want to add another constructor

	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}
}
