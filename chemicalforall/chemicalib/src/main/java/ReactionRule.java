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
