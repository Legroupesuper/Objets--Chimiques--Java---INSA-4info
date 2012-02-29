package fr.insa.rennes.info.chemical.backend;

class Triple<A, B, C> {
	private A _first;
	private B _second;
	private C _triple;
	
	public Triple() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Triple(A _first, B _second, C _triple) {
		super();
		this._first = _first;
		this._second = _second;
		this._triple = _triple;
	}
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
	public C get_triple() {
		return _triple;
	}
	public void set_triple(C _triple) {
		this._triple = _triple;
	}
	
	
}