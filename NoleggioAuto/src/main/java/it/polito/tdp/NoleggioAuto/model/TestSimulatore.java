package it.polito.tdp.NoleggioAuto.model;

public class TestSimulatore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Simulatore sim = new Simulatore(17);
	
		sim.run();
		
		System.out.println("Numero clienti insoddisfatti: "+sim.getNumClientiInsoddisfatti());
		System.out.println("Numero clienti totali: "+sim.getNumClientiTotali());

	}

}
