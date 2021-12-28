package LinkedList;
//Flavio Maiorana - Matricola 182611

import java.util.Comparator;

public class ComparatoreInteri implements Comparator<Integer> {
	public int compare(Integer uno, Integer due) {
		if(uno > due) return 1;
		else if( due > uno) return -1;
		else return 0;
	}

}
