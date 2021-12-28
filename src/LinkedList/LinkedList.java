package LinkedList;
//Flavio Maiorana - Matricola 182611
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList<T> extends AbstractList<T>
{
	public enum Direzione {FORWARD, BACKWARD, UNKNOWN};
	private Nodo<T> inizio, fine;
	private int size;

	private static class Nodo<E>{
	    private Nodo<E> next,previous;
	    private E info;
	}

	@Override
	public void add(T e){
		addLast(e);
	}

	@Override
	public void addFirst(T e) {
		ListIterator<T> i = this.listIterator();
		i.add(e);
	}

	@Override
	public void addLast(T e) {
		ListIterator<T> i = this.listIterator(this.size() - 1);
		i.add(e);
	}

	@Override
	public ListIterator<T> listIterator() {
		return new Iteratore();
	}

	@Override
	public ListIterator<T> listIterator(int pos) 
	{
		Iteratore i = new Iteratore();
		//Faccio tante next fino ad arrivare a pos
		while(i.cursore < pos && i.hasNext()) i.next();
		return i;
	}

	@Override
	public Iterator<T> iterator() 
	{
		return new Iteratore();
	}

	@Override
	public void sort(Comparator<T> c)
	{
		boolean scambio = true; //true se sono avvenuti scambi
		T next1 = null; T next2 = null;
		int limite = size(); //indice fino al quale possono avvenire scambi
		while(scambio) 
		{
			ListIterator<T> li1 = listIterator();
			ListIterator<T> li2 = listIterator(0);
			scambio = false;
			while(li1.hasNext() && li2.hasNext() && li2.nextIndex() < limite) 
			{
				next1 = li1.next(); next2 = li2.next();
				if(c.compare(next1, next2) > 0) 
				{ //se il primo elemento � maggiore del secondo devono essere scambiati
					//SWAP
					li1.set(next2); li2.set(next1);
					if(li2.nextIndex() == limite) limite--;
					scambio = true;
				}
			}
		}
	}


	private class Iteratore implements ListIterator<T>{

		private Nodo<T> nexti,previousi,processato;
		private Direzione direzione;
		private int cursore; 
		//Inizia da -1. Indica l'indice dell'elemento appena processato. Aumenta con una next, diminuisce con una previous.


		public Iteratore() {
			nexti = inizio;cursore = -1; direzione = Direzione.UNKNOWN;
		}//costruttore

		@Override
		public T next() {
			Nodo temp = previousi;
			try{
				previousi = nexti; nexti = nexti.next;
			}catch(NullPointerException e) {
				previousi = temp;
				return null;
			}
			cursore++;
			direzione = Direzione.FORWARD;
			processato = previousi;
			return previousi.info;
		}

		@Override
		public T previous() {
			if(!(this instanceof ListIterator)) throw new UnsupportedOperationException();
			Nodo temp = nexti;
			try {
				nexti = previousi; previousi = previousi.previous;
			}catch(NullPointerException e) {
				nexti = temp;
				return null;
			}
			cursore--;
			direzione = Direzione.BACKWARD;
			processato = nexti;
			return nexti.info; //Ricorda: non devi restituire il nodo, ma il suo contenuto.
		}

		@Override
		public boolean hasNext() {
			return nexti != null;
		}

		@Override
		public boolean hasPrevious() {
			if(!(this instanceof ListIterator)) throw new UnsupportedOperationException();
			return previousi != null;
		}

		@Override
		public int nextIndex() {
			if(!(this instanceof ListIterator)) throw new UnsupportedOperationException();
			int ris = cursore;
			return ++ris;//STAI ATTENTO A NON MODIFICARE COSE SENZA ACCORGERTENE
		}

		@Override
		public int previousIndex() {
			if(!(this instanceof ListIterator)) throw new UnsupportedOperationException();
			int ris = cursore;
			if(cursore < 0) return ris;
			return --ris;
		}

		@Override
		public void add(T e) {
			Nodo<T> nuovo = new Nodo();
			nuovo.info = e;
			if(previousi == null && nexti == null) { //lista vuota
				inizio = nuovo; fine = nuovo; previousi = nuovo; cursore = 0;
			}
			else if(previousi == null && nexti != null) { //inserimento in testa
				nuovo.next = nexti; nuovo.previous = previousi; //Nuovo viene prima dell'elemento che verrebbe restituito con una previous
				previousi = nuovo;
				inizio = nexti.previous = previousi; 			//Faccio partire la lista da nuovo
				if(nexti.next == null) fine = nexti; //Caso in cui nella lista c'� un solo elemento
				cursore = 0;
			}
			else if(previousi != null && nexti == null) { //inserimento in coda
				nuovo.previous = fine; nuovo.next = nexti;
				fine = previousi.next = nuovo; //Faccio terminare la lista con nuovo
				previousi = fine;
				cursore = size - 1;
			}
			else {  //inserimento fra due nodi
				nuovo.next = nexti; nuovo.previous = previousi;
				previousi.next = nexti.previous = nuovo;
				previousi = nuovo;
				cursore++;
			}
			processato = null;
			size++;
		}

		@Override
		public void set(T e) {
			if(processato != null) {
				processato.info = e;
			}
			else throw new IllegalStateException("Chiamare prima una next() o una previous()");
			processato = null; //Processato � diverso da null solo immediatamente dopo una next o una previous
		}

		@Override
		public void remove() {
			if(processato != null) {
				switch(direzione) {
				case FORWARD:
					size--; cursore--;
					if(nexti != null) nexti.previous = previousi.previous; //Mentre prima nexti.previous era proprio processato, ora diventa il suo precedente
					/*aggiunto*/else fine = processato.previous;//Sto eliminando alla fine della lista
					if(previousi != null) {
						if(previousi.previous == null) inizio = nexti;
						else previousi.previous.next = nexti;
					}
					previousi = previousi.previous;
					break;
				case BACKWARD:
					size--; cursore--;
					if(previousi != null) previousi.next = nexti.next;  //Anche qui salto processato, che in questo caso � next
					/*aggiunto*/else inizio = processato.next;//Sto eliminando all'inizio della lista
					if(nexti != null) {
						if(nexti.next == null) fine = nexti;
						else nexti.next.previous = previousi;
					}
					nexti = nexti.next;
					break;
				default:
					processato = null; size--; cursore--;
					break;
				}
			}
			else throw new IllegalStateException("Chiamare prima una next() o una previous()");
			processato = null;
		}//remove

	}//Iteratore


	}
