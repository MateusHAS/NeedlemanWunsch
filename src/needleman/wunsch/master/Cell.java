package needleman.wunsch.master;

import java.util.concurrent.Semaphore;

public class Cell {

	private Integer value;
	private Semaphore semaphore = new Semaphore(0);


	public void await(){

		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void release(){
		semaphore.release();
		synchronized (this){

			this.notifyAll();
		}
	}

	public Cell() {

	}

	public Cell(Integer value) {

		this.value = value;
	}

	public Integer getValue() {return value;}

	public void setValue(Integer value) {this.value = value;}
}
