package it.polito.tdp.NoleggioAuto.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.PriorityQueue;

import it.polito.tdp.NoleggioAuto.model.Evento.EventType;

public class Simulatore {

	//Parametri in ingresso
	private int NC; //numeroAuto
	private Duration T_IN = Duration.ofMinutes(10); //tempo di interarrivo clienti
	private Duration T_TRAVEL = Duration.ofHours(1); //Potrebbe essere 1,2,3 volte tanto
	
	//Valori in uscita (che vedra lo utilizzatore)
	private int numClientiTotali;
	private int numClientiInsoddisfatti;
	
	//Stato
	private int autoDisponibili;
	
	//Coda di eventi
	private PriorityQueue<Evento> coda;
	
	//Costruttore
	public Simulatore(int NC) {
		this.NC=NC;
		this.coda= new PriorityQueue<Evento>();
		autoDisponibili = NC;
	}

	//Metodi di simulazione
	public void run() {
		//Carichiamo gli eventi
		caricaEventi();
		
		
		//Finche la coda non è vuota devo processare
		//il prossimo evento
		while(!coda.isEmpty()) {
			Evento e = coda.poll();//Ritorna il primo elemento 
			processEvent(e);
		}
		
	}
	
	private void caricaEventi() {
		//Simuliamo dalle 8 del mattino alle 16
		//degli arrivi ogni 10 min
		
		LocalTime ora = LocalTime.of(8, 0);
		
		while(ora.isBefore(LocalTime.of(16, 0))) {
			coda.add(new Evento(ora, EventType.NUOVO_CLIENTE));
			ora = ora.plus(T_IN);
		}
	}
	
	private void processEvent(Evento e) {
		switch(e.getType()) {
		case NUOVO_CLIENTE:
			numClientiTotali++;
			if(autoDisponibili>0) {
				autoDisponibili--;
				
				int ore = (int) (Math.random()*3)+1;
				LocalTime oraRientro = e.getTime()
						.plus(Duration.ofHours(ore*T_TRAVEL.toHours()));
			
				coda.add(new Evento(oraRientro, EventType.AUTO_RESTITUITA));
			}else {
				numClientiInsoddisfatti++;
			}
			break;
		case AUTO_RESTITUITA:
			autoDisponibili++;
			break;
		}
		
	}

	//getter e setter
	public int getNumClientiTotali() {
		return numClientiTotali;
	}

	public int getNumClientiInsoddisfatti() {
		return numClientiInsoddisfatti;
	}

	public void setNC(int nC) {
		NC = nC;
	}

	public void setT_IN(Duration t_IN) {
		T_IN = t_IN;
	}

	public void setT_TRAVEL(Duration t_TRAVEL) {
		T_TRAVEL = t_TRAVEL;
	}
	
	
	
}
