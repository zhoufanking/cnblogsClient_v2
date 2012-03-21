package org.CnBlogsClient.Common;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class LinkList<TYPE> implements Iterable<TYPE> {
	
	private class Node<TYPE>{
		public Node<TYPE>		pre;
		public TYPE				data;
		public Node<TYPE>		next;
		
		public Node(TYPE d, Node<TYPE> p, Node<TYPE> n){
			pre = p;
			data = d;
			next = n;		
		}
		
		public  Node(){
			pre = null;
			data = null;
			next = null;	
		}
		
	}

	private int theSize;
	private int ModSize;
	
	Node<TYPE> Header;
	Node<TYPE> Tail;
	
	public LinkList(){
		theSize = 0;
		ModSize = 0;
		
		Header = new Node<TYPE>();		
		Tail   = new Node<TYPE>(null,Header,null);
		
		Header.next = Tail;
	}
	
	public int Size(){
		return theSize;
	}
	
	public void add(TYPE d){
		Node<TYPE> newNode = new Node<TYPE>(d,null,null);
		
		newNode.pre  = Tail.pre;
		newNode.next = Tail;
		
		Tail.pre.next = newNode;
		Tail.pre = newNode;
		
		theSize++;
		ModSize++;
	}
	
	@Override
	public Iterator<TYPE> iterator(){
		return new LListIterator();
	}
	
	
	private class LListIterator implements Iterator<TYPE>{

		Node<TYPE> current  = Header.next;
		int ExpectedModSize = ModSize;
		
		@Override
		public boolean hasNext() {
			return (current!=Tail);
		}

		@Override
		public TYPE next() {
			if(!hasNext())
				throw new IndexOutOfBoundsException();
			if(ExpectedModSize != ModSize)
				throw new ConcurrentModificationException();
			
			TYPE item = current.data;
			current = current.next;
			
			return item;
		}

		@Override
		public void remove() {
			if(ExpectedModSize != ModSize)
				throw new ConcurrentModificationException();
			
			current.pre = current.next;
			current.next.pre = current.pre;
			current = current.next;
			
			ModSize++;
			theSize--;
			ExpectedModSize++;
			
		}
	}	
}
