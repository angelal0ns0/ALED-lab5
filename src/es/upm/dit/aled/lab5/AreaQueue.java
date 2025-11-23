package es.upm.dit.aled.lab5;

import java.util.LinkedList;
import java.util.Queue;

import es.upm.dit.aled.lab5.gui.Position2D;

/**
 * Extension of Area that maintains a strict queue of the Patients waiting to
 * enter in it. After a Patient exits, the first one in the queue will be
 * allowed to enter.
 * 
 * @author rgarciacarmona
 */
public class AreaQueue extends Area {

	//nuevo atributo que tiene
	private Queue<Patient> waitQueue;
	//Constructor, hay que invocar al super!
	//(el this.atributo hecho en el cosntructor de la clase padre)
	public AreaQueue(String name, int time, int capacity, Position2D position) {
		super(name, time, capacity, position);
		this.waitQueue = new LinkedList<Patient>(); //creamos la waitQueue como LinkedList
	}
	
	//METODO ENTER
	@Override //para avisar que lo reprogramamos (ya existía en la clase padre)
	public synchronized void enter(Patient p) {
		System.out.println("Patient " + p.getNumber() + " trying to enter " + this.name);
		this.waiting++;
		this.waitQueue.add(p); //le añado a la cola al que llega "preguntando" para entrar
		while(this.numPatients >= this.capacity || this.waitQueue.peek()!= p) { 
		//sigue perando si no hay sitio o si el p que quiere entrar 
		//no es el que toca por orden de waitQueue
			System.out.println("Patient " + p.getNumber() + " waiting for " + this.getName() + 
					"Front of queue?" + this.waitQueue.peek().equals(p));
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		//si sale del while (no le hacemos esperar) xq cabe y le toca entrar
		this.waitQueue.remove(); //le sacamos de la cola de espera
		this.waiting--;
		this.numPatients++;
		System.out.println("Patient " + p.getNumber() + " has entered " + this.name);

	}
}
