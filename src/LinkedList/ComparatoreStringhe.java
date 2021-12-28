package LinkedList;
//Flavio Maiorana - Matricola 182611

import java.util.Comparator;

public class ComparatoreStringhe implements Comparator<String> {
	public int compare(String uno, String due) {
		if(uno.compareTo(due) > 0) return 1;
		else if(uno.compareTo(due) < 0) return -1;
		else return 0;
	}

}
