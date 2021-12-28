//Flavio Maiorana - Matricola 182611
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

import javax.swing.*;
//.Interfaccia.
public class Interfaccia extends JFrame {

	//.Campi
	private JButton uno, due, tre, quattro, cinque, sei, sette, otto, nove, zero, piu, meno, per, diviso, percento,
			elevato, uguale, paraperta, parchiusa, cancella;

	private JPanel tasti,testo;

	private JTextField espr,ris;
	//Campi.

	//.Costruttore
	public Interfaccia() {
		//Aggiustamenti Frame esterno
		setSize(500, 700);
		setResizable(true);
		setMinimumSize(new Dimension(455,400));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		//Creazione e aggiunta al Frame delle due caselle di testo per l'espressione e il risultato riunite in un unico JPanel
		testo = new JPanel();

		espr = new JTextField("",20);
		espr.setFont(new Font("Bold",10,18));
		espr.addKeyListener(new invioListener());;

		ris = new JTextField("",10);
		ris.setFont(new Font("Bold",10,18));
		ris.setEditable(false);

		testo.add(espr);
		testo.add(ris);

		add(testo, BorderLayout.BEFORE_FIRST_LINE);

		//Creazione e aggiunta di un JPanel contenente i bottoni di numeri e operatori
		tasti = new JPanel();
		tasti.setSize(500,500);
		add(tasti,BorderLayout.CENTER);
		tasti.setLayout(new GridLayout(5,4,0,0));
		//Delego la creazione dei singoli bottoni ad un metodo
		bottoni();

		tasti.add(uno); 	  tasti.add(due);      tasti.add(tre);       tasti.add(piu);
		tasti.add(quattro);   tasti.add(cinque);   tasti.add(sei);       tasti.add(meno);
		tasti.add(sette); 	  tasti.add(otto);     tasti.add(nove);      tasti.add(per);
		tasti.add(paraperta); tasti.add(zero);     tasti.add(parchiusa); tasti.add(diviso);
		tasti.add(elevato);   tasti.add(percento); tasti.add(uguale);    tasti.add(cancella);
	}

	//Vengono sistemati tutti i bottoni in un panel e aggiunti tutti gli ActionListener
	private void bottoni() {
		uno = new JButton("1");     	uno.setFont(new Font("BOLD",Font.BOLD,50));			uno.addActionListener(new numOpListener());  		uno.setBackground(new Color(230,230,230));
		due = new JButton("2");			due.setFont(new Font("BOLD",Font.BOLD,50));			due.addActionListener(new numOpListener());			due.setBackground(new Color(230,230,230));
		tre = new JButton("3");			tre.setFont(new Font("BOLD",Font.BOLD,50));			tre.addActionListener(new numOpListener());			tre.setBackground(new Color(230,230,230));
		quattro = new JButton("4"); 	quattro.setFont(new Font("BOLD",Font.BOLD,50));		quattro.addActionListener(new numOpListener());		quattro.setBackground(new Color(230,230,230));
		cinque = new JButton("5");  	cinque.setFont(new Font("BOLD",Font.BOLD,50));		cinque.addActionListener(new numOpListener());		cinque.setBackground(new Color(230,230,230));
		sei = new JButton("6");			sei.setFont(new Font("BOLD",Font.BOLD,50));			sei.addActionListener(new numOpListener());			sei.setBackground(new Color(230,230,230));
		sette = new JButton("7");		sette.setFont(new Font("BOLD",Font.BOLD,50));		sette.addActionListener(new numOpListener());		sette.setBackground(new Color(230,230,230));
		otto = new JButton("8");		otto.setFont(new Font("BOLD",Font.BOLD,50));		otto.addActionListener(new numOpListener());		otto.setBackground(new Color(230,230,230));
		nove = new JButton("9");		nove.setFont(new Font("BOLD",Font.BOLD,50));		nove.addActionListener(new numOpListener());		nove.setBackground(new Color(230,230,230));
		zero = new JButton("0");		zero.setFont(new Font("BOLD",Font.BOLD,50));		zero.addActionListener(new numOpListener());		zero.setBackground(new Color(230,230,230));
		paraperta = new JButton("(");	paraperta.setFont(new Font("BOLD",Font.BOLD,50));	paraperta.addActionListener(new numOpListener());	paraperta.setBackground(new Color(200,200,200));
		parchiusa = new JButton(")");	parchiusa.setFont(new Font("BOLD",Font.BOLD,50));	parchiusa.addActionListener(new numOpListener());	parchiusa.setBackground(new Color(200,200,200));
		piu = new JButton("+");			piu.setFont(new Font("BOLD",Font.BOLD,50));			piu.addActionListener(new numOpListener());			piu.setBackground(new Color(200,200,200));
		meno = new JButton("-");		meno.setFont(new Font("BOLD",Font.BOLD,50));		meno.addActionListener(new numOpListener());		meno.setBackground(new Color(200,200,200));
		per = new JButton("*");			per.setFont(new Font("BOLD",Font.BOLD,50));			per.addActionListener(new numOpListener());			per.setBackground(new Color(200,200,200));
		diviso = new JButton("/");		diviso.setFont(new Font("BOLD",Font.BOLD,50));		diviso.addActionListener(new numOpListener());		diviso.setBackground(new Color(200,200,200));
		elevato = new JButton("^");		elevato.setFont(new Font("BOLD",Font.BOLD,50));		elevato.addActionListener(new numOpListener());		elevato.setBackground(new Color(200,200,200));
		percento = new JButton("%");	percento.setFont(new Font("BOLD",Font.BOLD,50));	percento.addActionListener(new numOpListener());	percento.setBackground(new Color(200,200,200));
		uguale = new JButton("=");		uguale.setFont(new Font("BOLD",Font.BOLD,50));		uguale.addActionListener(new ugListener());			uguale.setBackground(new Color(200,200,200));
		cancella = new JButton("c");	cancella.setFont(new Font("BOLD",Font.BOLD,50));	cancella.addActionListener(new cListener());		cancella.setBackground(new Color(200,200,200));
	}
	//Costruttore.


	//.Ascoltatori

	//Ascoltatore per i bottoni numerici. Una volta azionati scrivo sul TextField che contiene l'espressione.
	class numOpListener implements ActionListener { //Non dimenticarti degli import!!!! E non dimenticarti di aggiungere gli ascoltatori
		public void actionPerformed(ActionEvent e) {
			if(!ris.getText().equals("")) espr.setText("");
			ris.setText("");
			JButton j = (JButton)e.getSource(); //Sono sicuro che sia un JButton, ma per il compilatore � un Object, quindi devo castizzare
			espr.setText(espr.getText() + j.getText());
		}
	}

	// Ascoltatore per il bottone = . Una volta premuto torna il risultato.
	class ugListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String espressione = null;
			Integer risultato = null;
			for(;;)
			{
				String inizio = "(\\(?\\d+)";
				String operatore = "([\\/\\*\\+\\^\\-\\%]\\(?)";
				String operando = "(\\d+\\)?)";
				
				String regex = inizio + "(" + operatore + operando + ")*";
				espressione = espr.getText();
				if(espressione.matches(regex)) break;
				else System.out.println("Immetti un'espressione corretta");
			}
			StringTokenizer st = new StringTokenizer(espressione,"*^/-+%()",true);
			try{
		        risultato = Valutatore.valutaOperando(st);
		      }catch(NumberFormatException | NoSuchElementException ex) {
		        ris.setText("Espr. Malformata");
		    }
			if(risultato != null) ris.setText(risultato.toString());
		}
	}

	//Ascoltatore per il bottone c, che cancella l'ultimo carattere dell'espressione
	class cListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String text = espr.getText();
			espr.setText(text.substring(0, text.length()-1));
		}
	}

	//Ascoltatore per la tastiera. Viene azionato quando si preme il tasto invio, per tornare il risultato dell'espressione.
	class invioListener extends KeyAdapter {
		public void keyPressed(KeyEvent k) 
		{
			ugListener u = new ugListener();
			if(k.getKeyCode() == k.VK_ENTER) u.actionPerformed(null);
		}
	}
	//Ascoltatori.

	public static void main(String[] args) {
		Interfaccia f = new Interfaccia();
		f.setVisible(true);
  }

}//Interfaccia.
