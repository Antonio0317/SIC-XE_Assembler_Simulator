import java.io.File;


public class Control {
	public static V_Simulator vs;
	public static Loader ld;
	public static Manager mana;
	public static SIC_Simulator ss;
	
	public Control() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		vs = new V_Simulator();
		ld = new Loader();
		mana = new Manager();
		ss = new SIC_Simulator();
		vs.initialize();
		ss.initialize(new File("inst.data"));
	}
}
