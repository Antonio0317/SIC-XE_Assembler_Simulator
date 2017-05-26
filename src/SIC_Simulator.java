import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import javax.swing.JOptionPane;

import sp.project2.interfacepack.SicSimulator;

public class SIC_Simulator implements SicSimulator {
	private String[][] inst_table;
	private int inst_line;
	private int line;
	private int end_flag;

	public SIC_Simulator() {
		// TODO Auto-generated constructor stub
		inst_table = new String[256][4];
		inst_line = 0;
		line = 0;
		end_flag = 0;
	}

	@Override
	public void addLog() {
		// TODO Auto-generated method stub
		String str = Control.ld.Text_table[line][1];
		String operator = str.substring(0, 2);
		int op = Integer.parseInt(operator, 16);
		op /= 4;
		op *= 4;
		operator = Control.ss.search_inst(String.format("%02X", op));
		switch (operator) {
		case "STL":
			Control.vs.log_model.addElement("해당 주소에 L레지스터의 값을 저장!\n");
			break;
		case "JSUB":
			Control.vs.log_model.addElement("서브루틴 호출!\n");
			break;
		case "LDA":
			Control.vs.log_model.addElement("해당주소로부터 A레지스터에 값을 로드!\n");
			break;
		case "COMP":
			Control.vs.log_model.addElement("A레지스터와 해당 메모리 값을 비교!\n");
			break;
		case "JEQ":
			Control.vs.log_model
					.addElement("SW 레지스터가 같음을 의미하면 해당하는 주소값으로 PC값을 설정!\n");
			break;
		case "J":
			Control.vs.log_model.addElement("해당하는 주소값으로 PC값을 설정!\n");
			break;
		case "STA":
			Control.vs.log_model.addElement("A레지스터의 값을 해당 주소에 저장!\n");
			break;
		case "CLEAR":
			Control.vs.log_model.addElement("해당 레지스터를 0으로 만듬!\n");
			break;
		case "LDT":
			Control.vs.log_model.addElement("T레지스터에 해당하는 메모리값을 저장!\n");
			break;
		case "TD":
			Control.vs.log_model.addElement("디바이스를 테스트함!\n");
			break;
		case "RD":
			Control.vs.log_model.addElement("디바이스에서 A레지스터로 데이터를 가져옴!\n");
			break;
		case "COMPR":
			Control.vs.log_model.addElement("레지스터1과 레지스터2를 직접 비교!\n");
			break;
		case "STCH":
			Control.vs.log_model.addElement("A레지스터의 마지막 1byte값을 해당주소에 저장!\n");
			break;
		case "TIXR":
			Control.vs.log_model.addElement("X레지스터를 1증가시키고 레지스터1과 비교!\n");
			break;
		case "JLT":
			Control.vs.log_model
					.addElement("SW 레지스터가 적음을 의미하면 해당하는 주소값으로 PC값을 설정!\n");
			break;
		case "STX":
			Control.vs.log_model.addElement("해당 주소에 X레지스터의 값을 저장!\n");
			break;
		case "RSUB":
			Control.vs.log_model.addElement("리턴 서브루틴!\n");
			break;
		case "LDCH":
			Control.vs.log_model
					.addElement("해당 주소로부터 A레지스터 마지막 1byte에 값을 로드!\n");
			break;
		case "WD":
			Control.vs.log_model
					.addElement("A레지스터 마지막 1byte에 있는 값을 디바이스에 저장!\n");
			break;
		}
		Control.vs.logs.setModel(Control.vs.log_model);
	}

	@Override
	public void allStep() {
		// TODO Auto-generated method stub
		while (true) {
			if (end_flag >= 2) {
				JOptionPane.showMessageDialog(null, "끝났어용 다시 불러세용~");
				break;
			}
			oneStep();
		}
	}

	@Override
	public void initialize(File f) {
		// TODO Auto-generated method stub
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(f.getName());
			br = new BufferedReader(fr);
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				inst_table[inst_line++] = tmp.split("\\|");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"init_my_assembler: 프로그램 초기화에 실패 했습니다.\n");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
				}
				;
			}
		}
	}

	@Override
	public void oneStep() {
		// TODO Auto-generated method stub
		for (line = 0; line < Control.ld.text_line; line++) {
			String ss = String.format("%04X", Control.mana.getRegister(8));
			if (ss.equals(Control.ld.Text_table[line][0])) {
				Control.vs.instructions.setSelectedIndex(line);
				break;
			}
		}
		String str = Control.ld.Text_table[line][1];
		String operator = str.substring(0, 2);
		int info = Integer.parseInt(str.substring(1, 3), 16);
		int op = Integer.parseInt(operator, 16);
		op /= 4;
		op *= 4;
		operator = Control.ss.search_inst(String.format("%02X", op));
		int n = info & 0x20;
		int i = info & 0x10;
		int x = info & 0x08;
		int b = info & 0x04;
		int p = info & 0x02;
		int e = info & 0x01;
		int pc = 0;
		int base = 0;
		int disp = 0;
		int r1 = 0;
		int r2 = 0;
		if (search_form(String.format("%02X", op)).equals("2")) {
			disp = Integer.parseInt(str.substring(2, 2 + 2), 16);
		} else if (e == 1) {
			disp = Integer.parseInt(str.substring(3, 3 + 5), 16);
		} else if (e == 0) {
			disp = Integer.parseInt(str.substring(3, 3 + 3), 16);
		}
		if (end_flag >= 2) {
			JOptionPane.showMessageDialog(null, "끝났어용 다시 불러세용~");
		}
		switch (operator) {
		case "STL":
			end_flag++;
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			base = Control.mana.getRegister(3);
			String get_l = String.format("%06X", Control.mana.getRegister(2));
			if (n == 32 && i == 16) {
				if (x == 1) {
					disp += Control.mana.getRegister(1);
				}
				if (p == 1) {
					Control.mana
							.setMemory((disp + pc) * 2, get_l.getBytes(), 6);
				} else if (b == 1) {
					Control.mana.setMemory((disp + base) * 2, get_l.getBytes(),
							6);
				} else {
					Control.mana.setMemory((disp) * 2, get_l.getBytes(), 6);
				}
			}
			break;
		case "JSUB":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			if (n == 32 && i == 16) {
				Control.mana.setRegister(2, pc);
				Control.mana.setRegister(8, disp);
			}
			break;
		case "LDA":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			byte[] bt = Control.mana.getMemory((pc + disp) * 2, 6);
			int tmp = 0;
			if (n == 32 && i == 16) {
				for (int j = 0; j < 6; j++) {
					tmp *= 16;
					tmp += bt[j];
				}
				Control.mana.setRegister(0, tmp);
			}

			else if (n == 0 && i == 16) {
				Control.mana.setRegister(0, disp);
			}
			break;
		case "COMP":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);

			if (n == 0 && i == 16) {
				int A = Control.mana.getRegister(0);
				if (A < disp) {
					Control.mana.setRegister(9, -1);
				} else if (A > disp) {
					Control.mana.setRegister(9, 1);
				} else {
					Control.mana.setRegister(9, 0);
				}
			}
			break;
		case "JEQ":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			int sw = Control.mana.getRegister(9);
			if (sw == 0) {
				Control.mana.setRegister(8, pc + disp);
			}
			break;
		case "J":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			if (n == 32 && i == 16) {
				if (disp > 3840) {
					Control.mana.setRegister(8, pc + disp - 4096);
				} else {
					Control.mana.setRegister(8, pc + disp);
				}
			} else if (n == 32 && i == 0) {
				byte[] y = Control.mana.getMemory((pc + disp) * 2, 6);
				int var = 0;
				for (int j = 0; j < 6; j++) {
					var *= 16;
					var += y[j];
				}

				Control.mana.setRegister(8, var);
			}
			break;
		case "STA":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			int A = Control.mana.getRegister(0);
			if (n == 32 && i == 16) {
				String data = String.format("%06X", A);
				Control.mana.setMemory((pc + disp) * 2, data.getBytes(), 6);
			}
			break;
		case "CLEAR":
			Control.mana.setRegister(8, Control.mana.getRegister(8) + 2);
			pc = Control.mana.getRegister(8);
			Control.mana.setRegister(disp / 16, 0);
			break;
		case "LDT":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			byte[] be;
			if (e == 0) {
				be = Control.mana.getMemory((pc + disp) * 2, 6);
			} else {
				be = Control.mana.getMemory(disp * 2, 6);
			}
			int val = 0;
			for (int j = 0; j < 6; j++) {
				val *= 16;
				val += be[j];
			}
			Control.mana.setRegister(5, val);
			break;
		case "TD":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			Control.mana.setRegister(9, 1);
			break;
		case "RD":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			byte[] byt = Control.mana.getMemory((pc + disp) * 2, 2);
			String dvn = "";
			for (int j = 0; j < 2; j++) {
				dvn += String.format("%X", byt[j]);
			}
			Control.vs.device.setText(dvn);
			byte[] by = Control.mana.readDevice(dvn,
					Control.mana.getRegister(1));
			if (by[0] == -1) {
				Control.mana.setRegister(0, 0);
			} else {
				Control.mana.setRegister(0, by[0]);
			}
			break;
		case "COMPR":
			Control.mana.setRegister(8, Control.mana.getRegister(8) + 2);
			pc = Control.mana.getRegister(8);
			r1 = Control.mana.getRegister(0);
			r2 = Control.mana.getRegister(4);
			if (r1 < r2) {
				Control.mana.setRegister(9, -1);
			} else if (r1 > r2) {
				Control.mana.setRegister(9, 1);
			} else {
				Control.mana.setRegister(9, 0);
			}
			break;
		case "STCH":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			r1 = Control.mana.getRegister(1);
			int data = Control.mana.getRegister(0);
			String s = String.format("%02X", data);
			Control.mana.setMemory((disp + r1) * 2, s.getBytes(), 2);
			break;
		case "TIXR":
			Control.mana.setRegister(8, Control.mana.getRegister(8) + 2);
			pc = Control.mana.getRegister(8);
			Control.mana.setRegister(1, Control.mana.getRegister(1) + 1);
			r1 = Control.mana.getRegister(1);
			r2 = Control.mana.getRegister(5);
			if (r1 < r2) {
				Control.mana.setRegister(9, -1);
			} else if (r1 > r2) {
				Control.mana.setRegister(9, 1);
			} else {
				Control.mana.setRegister(9, 0);
			}
			break;
		case "JLT":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			if (Control.mana.getRegister(9) == -1) {
				if (disp > 3840) {
					Control.mana.setRegister(8, pc + disp - 4096);
				} else {
					Control.mana.setRegister(8, pc + disp);
				}
			}
			break;
		case "STX":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			r1 = Control.mana.getRegister(1);
			String tmp1 = String.format("%06X", r1);
			Control.mana.setMemory(disp * 2, tmp1.getBytes(), 6);
			break;
		case "RSUB":
			Control.mana.setRegister(8, Control.mana.getRegister(2));
			break;
		case "LDCH":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			r1 = Control.mana.getRegister(0);
			String buf = String.format("%06X", r1);
			byte[] bch = Control.mana.getMemory(
					(disp + Control.mana.getRegister(1)) * 2, 2);
			int c = 0;
			for (int j = 0; j < 2; j++) {
				c *= 16;
				c += bch[j];
			}
			Control.mana.setRegister(0, c);
			break;
		case "WD":
			if (e == 0) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 3);
			} else if (e == 1) {
				Control.mana.setRegister(8, Control.mana.getRegister(8) + 4);
			}
			pc = Control.mana.getRegister(8);
			byte[] bytt = Control.mana.getMemory((pc + disp) * 2, 2);
			String wdvn = "";
			for (int j = 0; j < 2; j++) {
				wdvn += Integer.toHexString(bytt[j]);
			}
			Control.vs.device.setText(wdvn);
			Control.mana.writeDevice(wdvn,
					String.format("%02X", Control.mana.getRegister(0))
							.getBytes(), 1);
			break;
		}
		addLog();
		if (search_form(String.format("%02X", op)).equals("2")) {
			Control.vs.targetadd.setText("");
		}
		else if (disp + pc > 3840*2) {
			Control.vs.targetadd.setText(String
					.format("%06X", disp + pc - 4096));
		} else {
			Control.vs.targetadd.setText(String.format("%06X", disp + pc));
		}
		Control.mana.affectVisualSimulator();
	}

	public String search_form(String s) {
		for (int i = 0; i < inst_line; i++) {
			if (inst_table[i][2].equals(s)) {
				return inst_table[i][1];
			}
		}
		return null;
	}

	public String search_inst(String s) {
		for (int i = 0; i < inst_line; i++) {
			if (inst_table[i][2].equals(s)) {
				return inst_table[i][0];
			}
		}
		return null;
	}
}
