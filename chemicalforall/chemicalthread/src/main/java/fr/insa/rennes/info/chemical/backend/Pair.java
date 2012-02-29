package fr.insa.rennes.info.chemical.backend;

class Pair<A, B> {
	private A _first;
	private B _second;
	public A get_first() {
		return _first;
	}
	public void set_first(A _first) {
		this._first = _first;
	}
	public B get_second() {
		return _second;
	}
	public void set_second(B _second) {
		this._second = _second;
	}
	public Pair(A _first, B _second) {
		super();
		this._first = _first;
		this._second = _second;
	}	
}


