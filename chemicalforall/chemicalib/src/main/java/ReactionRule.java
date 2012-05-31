/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
/**
 * Interface nécéssaire à l'implémentation d'une réaction rule.
 * @author cedric
 *
 */
public interface ReactionRule {
	/**
	 * Cette méthode est appellée par la librairie et effectue un traitement.
	 * A la fin du traitement, elle retourne un tableau d'objets qui seront réajoutés au multi-ensemble.
	 * L'appel de cette méthode a consomé les paramètres de la classe implémentée dans le multi-ensemble.
	 * @return Les objets à réinjecter dans le multi-ensemble après la réaction
	 */
	public Object[] computeResult();
	/**
	 * Permet d'éffectuer une sélection sur les attributs avant d'appeler computeResult.
	 * @return computeResult peut être appelé avec les paramètres choisis par la librairie
	 */
	public boolean computeSelect();
}