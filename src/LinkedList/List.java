package LinkedList;
//Flavio Maiorana - Matricola 182611
import java.util.*;
/**
 * 
 * Tutti i metodi concretizzati in quest'interfaccia lo sono grazie all'iteratore, che pu� essere istanziato anche se non � stato ancora concretizzato, giacch� usa come support, gi� a partire dall'interfaccia la classe concreta.
 */
public interface List<T> extends Iterable<T>{

/**
* @return Numero di elementi contenuti nella lista
*/
  default int size(){
	int size = 0;
    for(T t : this) size++;
    return size;
  }//size

/**
 * Svuota la lista
 */
  default void clear(){
    for(T t : this) this.remove(t);
  }//clear

/**
 * Controlla se l'elemento e � presente nella lista
 * @param e elemento da controllare
 * @return Booleano: true se e si trova nella lista, false altrimenti
 */
  default boolean contains( T e ){
    for(T t : this)
      if (t.equals(e)) return true;
    return false;
  }//contains

/**
 * Controlla se la lista � vuota
 * @return Booleano: true se la lista � vuota, false altrimenti
 */
  default boolean isEmpty(){
    return this.size() != 0;
  }//isEmpty

/**
 * Aggiunge un elemento e alla fine della lista
 * @param e elemento da aggiungere
 */
  void add( T e );

/**
 * Rimuove l'elemento e dalla lista
 * @param e elemento da rimuovere
 */
  default void remove( T e ){
    Iterator<T> it = this.iterator();
    while(it.hasNext()){
      T t = it.next();
      if(t.equals(e)) it.remove(); //La remove del listIterator non ha argomenti
    }
  }//remove

/**
 * Istanzia un listIterator relativo alla lista
 * @return ListIterator
 */
  ListIterator<T> listIterator();

  /**
   * Istanzia un listIterator, relativo alla lista, il cui indice parte da pos
   * @param pos Indice da cui partire
   * @return ListIterator
   */
  ListIterator<T> listIterator( int pos );

/**
 * Aggiunge un elemento in testa alla lista
 * @param e elemento da aggiungere
 */
  void addFirst( T e );

/**
 * Aggiunge un elemento in coda alla lsita
 * @param e elemento da aggiungere
 */
  void addLast( T e );

/**
 * Rimuove l'elemento in testa alla lista
 * @return elemento rimosso
 */
  default T removeFirst(){
    Iterator<T> it = this.iterator();
    T t = it.next();
    it.remove();
    return t;
  }

/**
 * Rimuove l'elemento in coda alla lista
 * @return elemento rimosso
 */
  default T removeLast(){
    ListIterator<T> lit = this.listIterator(this.size());
    T t = lit.previous();
    lit.remove();
    return t;
  }

  /**
   * Restituisce l'elemento in testa alla lista
   * @return T elemento
   */
  default T getFirst(){
    Iterator<T> it = this.iterator();
    return it.next();
  }//getFirst

  /**
   * Restituisce l'elemento in coda alla lista
   * @return T elemento
   */
  default T getLast(){
    ListIterator<T> lit = this.listIterator(this.size());
    return lit.previous();
  }//getLast

  void sort(Comparator<T> c);

}//List
