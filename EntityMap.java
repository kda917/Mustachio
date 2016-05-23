package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EntityMap implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2157573093629117030L;
	private static final int sectionSize = 600;
	
	private ArrayList<LinkedList<Entity>> list;
	
	
	public EntityMap() {
		list = new ArrayList<LinkedList<Entity>>();
	}
	
	public boolean add(Entity e) {
		int index = e.getX() / EntityMap.sectionSize;

		LinkedList<Entity> l = null;
		try {
			l = list.get(index);
			l.add(e);
			list.add(index, l);
			
			return true;
		} catch (IndexOutOfBoundsException arg0) {
		}
		
		if (l == null) {
			l = new LinkedList<Entity>();
		}
		l.add(e);
		list.add(index, l);
		
		return true;
	}
	
	public boolean add(Entity[] arr) {
		boolean ret = true;
		for (Entity e : arr) {
			ret = ret && add(e);
		}
		return ret;
	}
	
	public List<Entity> getSection(int x) {
		int index = x / EntityMap.sectionSize;
		if (index < 0) index = 0;
		return list.get(index);
	}

}
