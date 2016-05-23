package main;

import java.util.ArrayList;
import java.util.Collection;

public class UniqueArrayList<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8791473256074191095L;

	public UniqueArrayList() {
		super();
	}

	@Override
	public boolean add(E item) {

		boolean ret = false;
		if (!contains(item))
			ret = super.add(item);

		return ret;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {

		boolean ret = super.addAll(c);
		return ret;
	}

	@Override
	public E get(int index) {

		E ret = super.get(index);

		return ret;
	}
}
