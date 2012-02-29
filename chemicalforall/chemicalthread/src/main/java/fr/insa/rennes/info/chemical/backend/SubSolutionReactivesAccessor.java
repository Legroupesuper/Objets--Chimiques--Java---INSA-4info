package fr.insa.rennes.info.chemical.backend;

import java.util.List;


interface SubSolutionReactivesAccessor {
	public List<Object> getElements();
	public List<Class<? extends Object>> getTypeList();
	public void setElements(List<Object> l);
	public void setTypeList(List<Class<? extends Object>> l);
}
