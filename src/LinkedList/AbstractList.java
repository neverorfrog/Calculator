package LinkedList;
//Flavio Maiorana - Matricola 182611

import java.util.Iterator;
/**
 *
 * @author 97fla
 *
 * @param <T>
 *
 * Classe Astratta
 */
public abstract class AbstractList<T> implements List<T>{

/**
 * Converte la lista in stringa
 */
	  public String toString() {
	    StringBuilder ris = new StringBuilder("[");
	    for(T t : this) {
	      ris.append(t.toString() + ",  ");
	    }
	    if(ris.length() > 3) ris.delete(ris.length() - 3, ris.length());
	    return (ris + "]");
	  }//toString

/**
 * Ritorna l'hashCode
 */
	  public int hashCode() {
		  int h = 0; int m = 43;
		  for(T t : this) {
			  h = h * t.hashCode() + m;
		  }
		  return h;
	  }//hashCode

/**
 * Confronta due liste
 */
	  public boolean equals(Object o) {
		  if(!(o instanceof List)) return false; //Se o non � istanza di List, non posso castizzare
		  List<T> l = (List)o;
		  if(l.size() != this.size()) return false; //Controllo le size, che � una prerogativa fondamentale
		  Iterator<T> it1 = this.iterator(); Iterator<T> it2 = this.iterator();
		  //Controllo se ogni singolo elemento coincide nelle due liste. Se uno non coincide il ciclo si interrompee viene ritornato false
		  while(it1.hasNext()) {
			  T t1 = it1.next(); T t2 = it2.next();
			  if(!(t1.equals(t2))) return false;
		  }
		  return true;
	  }//equals

	}//AbstractList
