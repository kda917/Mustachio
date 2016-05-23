package main;

import java.util.ArrayList;
import java.util.Collection;

public class ThreadSafeList<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8791473256074191095L;
	protected boolean locked;
	
	public ThreadSafeList() {
		super();
		locked = false;
	}
	
	@Override
	public boolean add(E item) {
		while(locked()){}
		lock();
		boolean ret = false;
		if (!contains(item))
			ret = super.add(item);
		unlock();
		return ret;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		while(locked()){}
		lock();
		boolean ret = super.addAll(c);
		unlock();
		return ret;
	}
	
	@Override
	public E get(int index) {
		while(locked()){}
		lock();
		E ret = super.get(index);
		unlock();
		return ret;
	}
	
	public boolean locked() {
		return locked;
	}
	
	public void lock() {
		locked = true;
	}
	
	public void unlock() {
		locked = false;
	}

}
