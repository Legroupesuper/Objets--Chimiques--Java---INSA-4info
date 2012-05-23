package foxesnrabbits;


public class Cell {
	private int _x;
	private int _y;
	
	private Field _field;
	private Animal _animal;
	
	public Cell(Field field, int i, int j, Animal animal) {
		_field = field;
		_x = i;
		_y = j;
		_animal = animal;
	}
	
	public Cell(Field field, int i, int j) {
		this(field, i, j, null);
	}
	
	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

	public Animal getAnimal() {
		return _animal;
	}
	
	public void setAnimal(Animal animal) {
		_animal = animal;
		if (_field.isViewReady())
			_field.update();
	}
	
	public Field getField() {
		return _field;
	}
	
	public boolean nextTo(Cell c) {
		return Math.abs(this._x-c._x) <= 1 && Math.abs(this._y-c._y) <= 1 && this._x != c._x && this._y != c._y;
	}
	
	public boolean isFree() {
		return _animal == null;	
	}
	
	public Cell findFreeAdjacentCell() {
		return _field.findFreeAdjacentCell(_x, _y);
	}
	
	public String toString(){
		return "Case ["+_x+","+_y+"]";
	}
}
