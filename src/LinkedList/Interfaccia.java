package LinkedList;
//Flavio Maiorana - Matricola 182611
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.regex.*;
import javax.swing.*;

public class Interfaccia extends JFrame {

	private JButton add = new JButton("Add"),					remove = new JButton("Remove"),					size = new JButton("Size"),
					contains = new JButton("Contains"),			addFirst = new JButton("AddFirst"),				addLast = new JButton("AddLast"),
					removeFirst  = new JButton("RemoveFirst"),	removeLast  = new JButton("RemoveLast"),		getFirst = new JButton("GetFirst"),
					getLast = new JButton("GetLast"),			next = new JButton("Next"),						addli = new JButton("Add"),
					set = new JButton("Set"),					sort = new JButton("Sort"),						clear = new JButton("Clear"),
					hashCode = new JButton("Hashcode"),			previous = new JButton("Previous"),				removeli = new JButton("Remove"),
					creaLista = new JButton("Crea Lista"),		vistaIterator = new JButton("Vista Iterator"),	vistaNormale = new JButton("Vista Normale");

	private JTextField lista, stato;
	private static Interfaccia interfaccia;
	private JMenu menuM,menuI,menuS,menuL;

	//Mi servono come variabili private perch� la stessa istanza necessita di essere usata da diversi metodi
	private LinkedList ll = null;
	private ListIterator li = null;
	//tipo � true se la lista � di Stringhe e false se � di Integer e itAcceso � true se, appunto, l'iteratore � stato acceso
	private boolean tipo, itAcceso;

	//.Costruttore
	public Interfaccia() {
		setSize(1220,150);
		setMinimumSize(new Dimension(1220,200));

		JMenuBar menubar = menubar();
		add(menubar,BorderLayout.NORTH);

		JPanel testo1 = new JPanel();
		JPanel testo2 = new JPanel();

		//TextField che contiene la lista
		lista = new JTextField("",70);
		lista.setFont(new Font("Bold",Font.BOLD,20));
		lista.setEditable(false);

		//TextField che mostra l'elemento corrente o eventualmente un'eccezione
		stato = new JTextField("",50);
		stato.setFont(new Font("Bold",Font.BOLD,20));
		stato.setEditable(false);

		add(testo1,BorderLayout.WEST);
		add(testo2,BorderLayout.SOUTH);

		testo1.add(lista,BorderLayout.CENTER);
		testo2.add(stato, BorderLayout.CENTER);

		//Uso un optionDialog per far scegliere se la LinkedList sar� di stringhe o interi
		Object[] objects = {"Stringhe","Interi"};
		int i = JOptionPane.showOptionDialog(this,"Scegli se sar� una LinkedList di Stringhe o di Interi","Tipo LinkedList",
		JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,objects,null);
		//Richiedo finch� l'utente non ha scelto
		for(;;)
		{
			if(i == JOptionPane.YES_OPTION) {
				ll = new LinkedList<String>();
				tipo = true;
				setTitle("LinkedList di Stringhe");
				break;
			}
			else if(i == JOptionPane.NO_OPTION){
				ll = new LinkedList<Integer>();
				tipo = false;
				setTitle("LinkedList di Interi");
				break;
			}
			else i = JOptionPane.showOptionDialog(this,"Scegli se sar� una LinkedList di Stringhe o di Interi","Tipo LinkedList",
					JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,objects,null);
		}
		ascoltatori();
		lista.setText(ll.toString());

	}

	//Setto la barra dei menu, inserendo quattro diversi menu, uno per ogni tipologia di operazioni
	private JMenuBar menubar() {
		JMenuBar menubar = new JMenuBar();
		menuM = new JMenu("ModificaLL");
		menuM.add(sort); menuM.add(add); menuM.add(remove); menuM.add(clear);
		menuI = new JMenu("InfoLL");
		menuI.add(hashCode); menuI.add(size); menuI.add(contains);
		menuS = new JMenu("StackLL");
		menuS.add(addFirst); menuS.add(addLast); menuS.add(removeFirst); menuS.add(removeLast); menuS.add(getFirst); menuS.add(getLast);
		menuL = new JMenu("ListIterator");
		menuL.add(next); menuL.add(previous); menuL.add(set); menuL.add(removeli); menuL.add(addli);
		menubar.add(creaLista); menubar.add(menuM); menubar.add(menuI); menubar.add(menuL); menubar.add(menuS); menubar.add(vistaIterator); menubar.add(vistaNormale);
		menuL.setVisible(false); vistaNormale.setVisible(false);
		return menubar;
	}
	//Costruttore.

	//Ascoltatori.
	class AscoltatoreMod implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(tipo && e.getSource().equals(sort)) ll.sort(new ComparatoreStringhe());
			else if(!tipo && e.getSource().equals(sort)) ll.sort(new ComparatoreInteri());
			else if(e.getSource().equals(clear)) ll.clear();
			else if(e.getSource().equals(removeFirst)) {
				try{
					ll.removeFirst();
				}catch(IllegalStateException ex) {
					stato.setText("IllegalStateException: Chiamare prima una next() o una previous()");
				}
			}
			else if(e.getSource().equals(removeLast)) {
				try{
					ll.removeLast();
				}catch(IllegalStateException ex) {
					stato.setText("IllegalStateException: Chiamare prima una next() o una previous()");
				}
			}
			else if(e.getSource().equals(getFirst)) ll.getFirst();
			else if(e.getSource().equals(getLast)) ll.getLast();
			//Se l'iteratore � acceso, allora devo usare toStringI, che posiziona il cursore dentro la lista
			if(!itAcceso) lista.setText(ll.toString());
			else lista.setText(toStringI(ll.toString()));
		}
	}


	class AscoltatoreElem implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object ris = null;
			for(;;) {
				String a = JOptionPane.showInputDialog("Immetti l'elemento da inserire nella lista");
				if(!tipo) {
					try {
						ris = Integer.valueOf(a); break;
					}catch(NumberFormatException nf) { //Controllo se la stringa presa � effettivamente un intero
						JOptionPane.showMessageDialog(interfaccia, "Immettere un intero, nient'altro");
					}
				}
				else {
					//Verifico che la stringa non contenga virgole o spazi all'interno
					if(a.indexOf(' ') > 0 || a.indexOf(',') > 0) JOptionPane.showMessageDialog(interfaccia, "Immettere una stringa senza virgole o spazi all'interno");
					else{ris = a; break;}
				}
			}
			if(e.getSource().equals(add)) ll.add(ris);
			else if(e.getSource().equals(remove)) {
				ll.remove(ris);
			}
			else if(e.getSource().equals(contains)) {
				boolean b = ll.contains(ris);
				if(b) JOptionPane.showMessageDialog(interfaccia,"TRUE, l'elemento � contenuto nella lista");
				else JOptionPane.showMessageDialog(interfaccia,"FALSE, l'elemento non � contenuto nella lista");
			}
			else if(e.getSource().equals(addFirst)) ll.addFirst(ris);
			else if(e.getSource().equals(addLast)) ll.addLast(ris);
			else if(e.getSource().equals(addli)) li.add(ris);
			else if(e.getSource().equals(set)) li.set(ris);
			if(!itAcceso) lista.setText(ll.toString());
			else lista.setText(toStringI(ll.toString()));
		}
	}


	class AscoltatoreInfo implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(hashCode)) {
				int h = ll.hashCode();
				JOptionPane.showMessageDialog(interfaccia,"L'hashCode � il seguente: " + h);
			}
			else if(e.getSource().equals(size)) {
				int size = ll.size();
				JOptionPane.showMessageDialog(interfaccia,"La size � la seguente: " + size);
			}
			else if(e.getSource().equals(getFirst))
				JOptionPane.showMessageDialog(interfaccia,"La testa della lista � la seguente: " + ll.getFirst());
			else if(e.getSource().equals(getLast))
				JOptionPane.showMessageDialog(interfaccia,"La coda della lista � la seguente: " + ll.getLast());
		}
	}


	class AscoltatoreLit implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object processato = null;
			if(e.getSource().equals(next)) {
				processato = li.next();
				removeli.setVisible(true);
				set.setVisible(true);
			}
			else if(e.getSource().equals(previous)) {
				processato = li.previous();
				removeli.setVisible(true);
				set.setVisible(true);
			}
			else if(e.getSource().equals(removeli)) {
				try{
					li.remove();
				}catch(IllegalStateException ex) {
					stato.setText("IllegalStateException: Chiamare prima una next() o una previous()"); return;
				}
				set.setVisible(false);
				removeli.setVisible(false);
			}
			else if(e.getSource().equals(addli)) {
				AscoltatoreElem a2 = new AscoltatoreElem();
				a2.actionPerformed(e);
				set.setVisible(false);
				removeli.setVisible(false);
				processato = null;
			}
			else if(e.getSource().equals(set)) {
				AscoltatoreElem a2 = new AscoltatoreElem();
				try{
					a2.actionPerformed(e);
				}catch(IllegalStateException ex) {
					stato.setText("IllegalStateException: Chiamare prima una next() o una previous()"); return;
				}
				removeli.setVisible(false);
				set.setVisible(false);
				processato = null;
			}
			if(processato != null) stato.setText("Elemento Corrente: " + processato.toString());
			else stato.setText("Elemento Corrente: ?");
			lista.setText(toStringI(ll.toString()));
		}
	}

	//Crea un'altra lista
	class AscoltatoreCrea implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Interfaccia i = new Interfaccia();
			i.setVisible(true);
		}
	}

	class AscoltatoreVistaI implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			vistaIterator.setVisible(false);
			add.setVisible(false);
			sort.setVisible(false);
			remove.setVisible(false);
			removeFirst.setVisible(false);
			removeLast.setVisible(false);
			addFirst.setVisible(false);
			addLast.setVisible(false);
			menuL.setVisible(true);
			vistaNormale.setVisible(true);

			Object[] opzioni = {"Default","Posizione a scelta","Annulla"};
			//Inizializzazione iteratore
			int i = JOptionPane.showOptionDialog(interfaccia, "Scegli se inizializzare il ListIterator di default o da una posizione a tua scelta", "Costruzione listIterator",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, null);
			//Default
			if(i == JOptionPane.YES_OPTION) li = ll.listIterator();
			//Da pos
			else if(i == JOptionPane.NO_OPTION) {
				Integer ris = null;
				for(;;) {
					String a = JOptionPane.showInputDialog("Immetti un indice da 0 a " + (ll.size()) + " da cui far partire il listIterator");
					try {
						ris = Integer.valueOf(a); break;
					}catch(NumberFormatException nf) {
						JOptionPane.showMessageDialog(interfaccia, "Immettere un intero, nient'altro");
					}
				}
				li = ll.listIterator(ris);
			}
			else ;
			if(li != null) {
				itAcceso = true;
				lista.setText(toStringI(ll.toString()));
			}
			//Se l'utente ha annullato richiedo cosa vuole
			else {
				AscoltatoreVistaN  n = new AscoltatoreVistaN();
				n.actionPerformed(null);
			}
		}
	}

	class AscoltatoreVistaN implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//Attuo tutti gli aggiustamenti di visibilit� dei bottoni
			vistaIterator.setVisible(true);
			add.setVisible(true);
			sort.setVisible(true);
			remove.setVisible(true);
			removeFirst.setVisible(true);
			removeLast.setVisible(true);
			addFirst.setVisible(true);
			addLast.setVisible(true);
			menuL.setVisible(false);
			vistaNormale.setVisible(false);
			itAcceso = false;
			lista.setText(ll.toString());
		}
	}
	//Ascoltatori.

	//.Metodi di supporto
	//Stampa della lista inserendo ^ nella posizione in cui � il cursore
	private String toStringI(String list) {
		StringBuilder ris = new StringBuilder(list);
		StringTokenizer st = new StringTokenizer(list," ,",false);
		int indice = 1;	//Punta al primo carattere di ogni token. Parto da 1 per la presenza della parentesi, per cui il primo carattere ha indice 1
		int i = 0;
		while(st.hasMoreTokens() && i < li.nextIndex()) {
			int gap = st.nextToken().length();
			indice = indice + gap + 3; //Incremento di 3 + gap perch� nella stampa, fra un carattere e l'altro c'� una virgola e due spazi
			i++;
		}
		if(indice > 1 && indice - 2 < ris.length()) { //Il cursore va posto all'interno della lista
			ris.setCharAt(indice - 2, '^');
		}
		else if(indice > 1 && indice - 2 >= ris.length()) {//Il cursore va posto alla fine della lista
			ris.append("^");
		}
		else if(indice <= 1){//Il cursore va posto all'inizio della lista
			String riss = "^" + ris.toString();
			return riss;
		}
		return ris.toString();
	}


	//Delego la distribuzione degli ascoltatori ad un metodo
	private void ascoltatori() {
		sort.addActionListener(new AscoltatoreMod()); clear.addActionListener(new AscoltatoreMod()); removeFirst.addActionListener(new AscoltatoreMod());
		removeLast.addActionListener(new AscoltatoreMod());

		add.addActionListener(new AscoltatoreElem()); remove.addActionListener(new AscoltatoreElem()); contains.addActionListener(new AscoltatoreElem());
		addFirst.addActionListener(new AscoltatoreElem()); addLast.addActionListener(new AscoltatoreElem());


		size.addActionListener(new AscoltatoreInfo()); hashCode.addActionListener(new AscoltatoreInfo()); getLast.addActionListener(new AscoltatoreInfo());
		getFirst.addActionListener(new AscoltatoreInfo());

		next.addActionListener(new AscoltatoreLit()); addli.addActionListener(new AscoltatoreLit()); previous.addActionListener(new AscoltatoreLit());
		set.addActionListener(new AscoltatoreLit()); removeli.addActionListener(new AscoltatoreLit());

		creaLista.addActionListener(new AscoltatoreCrea()); vistaIterator.addActionListener(new AscoltatoreVistaI());
		vistaNormale.addActionListener(new AscoltatoreVistaN());

	}
	//Metodi di supporto.


	public static void main(String[] args) {
		interfaccia = new Interfaccia();
		interfaccia.setVisible(true);
	}

}


//MOLTO IMPORTANTE: INDICI
//MOLTO IMPORTANTE: CONTROLLI NULL E INIZIALIZZAZIONE
