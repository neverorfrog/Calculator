import LinkedList.*;
import java.util.StringTokenizer;
import java.util.Comparator;
/**
*Data una stringa rappresentante un'espressione aritmetica ne calcola il valore rispettando le precedenze e le parentesi.
*/
public class Valutatore{
  /**
  *@param st Lo StringTokenizer che scandisce la stringa
  *@return Il risultato dell'espressione
  */
  public static Integer valutaOperando(StringTokenizer st) {
    //Due stack che contengono rispettivamente operandi e operatori
    LinkedList<Integer> operandi = new LinkedList<Integer>();
    LinkedList<Character> operatori = new LinkedList<Character>();
    //Oggetto comparatore
    Precedenza pr = new Precedenza();
    //Operatore corrente
    char opc;
    //Scandisco una sola volta la string, prendendo un carattere a ogni "passo"
    while(st.hasMoreTokens() == true)
    {
      String nt = st.nextToken();

      //Se il carattere preso � un operatore lo confronto con quello affiorante se ce ne sono nello stack operatori
      if(nt.equals("+") || nt.equals("-") || nt.equals("*") || nt.equals("/") || nt.equals("%") || nt.equals("^"))
      {
        opc = nt.charAt(0);
        //Se il primo carattere processato � un segno, inserisco 0 negli operandi per evitare un'eccezione di espr malformata
        if(operandi.size() == 0) operandi.addFirst(0);
        //Se operatori � vuoto lo inserisco a prescindere
        if(operatori.size() == 0) operatori.addFirst(opc);
        //Altrimenti faccio il confronto
        else
        {
          //Caso in cui quello attuale � pi� priotario del precedente, quindi devo inserirlo in cima allo stack
          if(pr.compare(opc,operatori.getFirst()) > 0) operatori.addFirst(opc);
          //Caso in cui posso processare la attuale operazione
          else
          {
            Integer opn2 = operandi.removeFirst(); Integer opn1 = operandi.removeFirst();
            char opr = operatori.removeFirst();
            //Processo l'espressione
            operandi.addFirst(valutaEspressione(opn1,opn2,opr));
            //Inserisco il risultato in cima allo stack
            operatori.addFirst(opc);
          }
        }
      }

      //L'espressione dentro la parentesi ha priorit� assoluta rispetto al resto, quindi isolo il contenuto facendo continuare
      //st sull'altro metodo valutaEspressione che poi richiama valutaOperando sulla stringa generata dentro valutaEspressione
      else if(nt.equals("(")) operandi.addFirst(Valutatore.valutaOperandoPar(st));
      else if(nt.equals(")")) ;

      //Caso in cui il carattere � un intero
      else
      {
    	  Integer opn = null;
    	  opn = Integer.valueOf(nt);
          operandi.addFirst(opn);
      }
    }//while(prima scansione)

    //Svuoto gli stack eseguendo tutte le operazioni restanti finch� operatori non � vuoto
    while(operatori.size() > 0){
      Integer opn2 = operandi.removeFirst(); Integer opn1 = operandi.removeFirst();
      char opr = operatori.removeFirst();
      operandi.addFirst(valutaEspressione(opn1,opn2,opr));
    }//while(scansione stack)
    return operandi.getFirst();
  }//valutaOperando

  /**
   *@param st Lo StringTokenizer che scandisce la stringa
   *@return Il risultato dell'espressione (contenuta dentro una coppia di parentesi)
   */
  private static Integer valutaOperandoPar(StringTokenizer st){
    String espr = "";
    String nt = "";
    //Costruisco una stringa che contiene tutta l'espressione contenuta nelle parentesi, escluse le parentesi stesse
    while(!nt.equals(")") && st.hasMoreTokens()){
      nt = st.nextToken();
      espr += nt;
    }
    //Istanzio uno StringTokenizer sull'espressione appena letta
    StringTokenizer st2 = new StringTokenizer(espr,"+-*/%^()",true);
    //Richiamo valutaOperando
    return Valutatore.valutaOperando(st2);
  }

  /**
   *@param opn1 � il primo operatore
   *@param opn2 � il secondo operatore
   *@param op � l'operatore in questione
   *@return Calcola l'espressione matematica attraverso un'operazione aritmetica comandata dall'operatore.
   *Torna il risultato a valutaOperando.
   */
  private static Integer valutaEspressione(Integer opn1, Integer opn2, char op){
    Integer ris = 0;
    switch(op){
      case '+': ris = opn1 + opn2; break;
      case '-': ris = opn1 - opn2; break;
      case '*': ris = opn1 * opn2; break;
      case '/': ris = opn1 / opn2; break;
      case '%': ris = opn1 % opn2; break;
      case '^': ris = (int)Math.pow(opn1,opn2); break;
    }//NON DIMENTICARE I BREAK!
    return ris;
  }//valutaEspressione

  /**
   *Classe che implementa l'interfaccia Comparator. Serve a gestire le precedenze degli operatori aritmetici all'interno dell'espressione.
   */
  static class Precedenza implements Comparator<Character>{
    public int compare(Character c1, Character c2){
      switch(c1){
        case '^':
          if(c2 == '^') return 0;
          else return 1;
        case '*': case '/': case '%':
          if(c2 == '^') return -1;
          else if(c2 == '*' || c2 == '/' || c2 == '%') return 0;
          else return 1;
        default:
          if(c2 == '+' || c2 == '-') return 0;
          else return -1;
      }
    }//compare
  }//Precedenza
}
