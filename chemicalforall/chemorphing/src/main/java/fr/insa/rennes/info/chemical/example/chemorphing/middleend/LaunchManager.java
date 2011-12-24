package fr.insa.rennes.info.chemical.example.chemorphing.middleend;

public class LaunchManager {

	private LaunchManager(){}	// Overriding/disabling default constructor in order to prevent from abusive accessibility

	public static void init(){
		initModel();
		WindowManager.initView();
	}
	
	private static void initModel() {
	}

	public static void main(String[] args){
		init();
	}
	
}
