/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of libchloe.

    libchloe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    libchloe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with libchloe.  If not, see <http://www.gnu.org/licenses/>
*/
package foxesnrabbits;

import java.util.List;
import java.util.Random;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class Main {
	public static long time = 0;
	public static View view;
	
	public static void main(String[] args) {
		Solution solution = new Solution();
		
		//Create the fields
		int N = 12;
		int M = 12;
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
		double RABBIT_PROBA = 0.15;
		double FOX_PROBA = 0.45;
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
		
		view = new View(field,solution);
		field.setView(view);
		
		solution.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {				
				time = System.currentTimeMillis()-time;
				System.out.println("\n\nRéaction terminée :");
				System.out.println(e.getSource());
				printNbRabbitsAndFoxes((Solution)e.getSource(), -1);
				System.out.println("Time : "+time+"ms");
				view.update();
				view.finalUpdate();			
			}
			
		});
		
		System.out.println(solution);
		time = System.currentTimeMillis();
		solution.react();
		/*int seconds = 0;
		while(!solution.is_inert()) {
			seconds++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			printNbRabbitsAndFoxes(solution, seconds);
		}*/
	}
	
	public static void printNbRabbitsAndFoxes(Solution solution, int seconds) {
		System.out.println((seconds == -1 ? "End" : seconds)+
				" => Rabbits "+solution.getReactivesFromType(Rabbit.class).size()+
				"/ Foxes : "+solution.getReactivesFromType(Fox.class).size());
	}
	
}