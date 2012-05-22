package foxesnrabbits;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class Main {
	
	
	public static void main(String[] args) {
		Solution solution = new Solution();
		
		//Create the fields
		int N = 50;
		int M = 10;
		Field field = new Field(N, M);
		
		//Add all the cells
		List<Cell> cells = field.getCellList();
		solution.addAll(cells);
		
		//Ad the dynamics
		solution.add(new MoveRabbit());
		solution.add(new MoveFox());
		solution.add(new BreedRabbit());
		solution.add(new BreedFox());
		solution.add(new DeadFoxCollector());
		solution.add(new DeadRabbitCollector());
		
		//Add the foxes and rabbits
		Random rand = new Random();
		double RABBIT_PROBA = 0.08;
		double FOX_PROBA = 0.05;
		int r;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(rand.nextDouble() <= RABBIT_PROBA) {
					Rabbit rabbit = new Rabbit(-1, field.getCell(i, j));
					solution.add(rabbit);
				} else if(rand.nextDouble() <= FOX_PROBA) {
					Fox fox = new Fox(-1, field.getCell(i, j));
					solution.add(fox);
				}
			}
		}
		
		solution.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				System.out.println("\n\nRéaction terminée :");
				System.out.println(e.getSource());
				printNbRabbitsAndFoxes((Solution)e.getSource(), -1);
			}
			
		});
		
		System.out.println(solution);
		solution.react();
		
		int seconds = 0;
		while(!solution.is_inert()) {
			seconds++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			printNbRabbitsAndFoxes(solution, seconds);
		}
	}
	
	public static void printNbRabbitsAndFoxes(Solution solution, int seconds) {
		
		Iterator<Object> it = solution.iterator();
		int nbRabbits = 0;
		int nbFoxes = 0;
		Object next;
		while(it.hasNext()) {
			next = it.next();
			
			if(next instanceof Rabbit)
				nbRabbits++;
			else if(next instanceof Fox)
				nbFoxes++;
		}
		
		System.out.println((seconds == -1 ? "End" : seconds)+" => Rabbits "+nbRabbits+"/ Foxes : "+nbFoxes);
	}
}
