import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import sp.project2.interfacepack.ResourceManager;

public class Manager implements ResourceManager {

	public byte[] memory;
	private String buffer;
	private int[] register;
	private int num = 0;

	public Manager() {
		// TODO Auto-generated constructor stub
		initializeMemory();
		initializeRegister();
		buffer = "";
	}

	@Override
	public void affectVisualSimulator() {
		// TODO Auto-generated method stub
		int A = Control.mana.getRegister(0);
		int X = Control.mana.getRegister(1);
		int L = Control.mana.getRegister(2);
		int PC = Control.mana.getRegister(8);
		int SW = Control.mana.getRegister(9);
		int B = Control.mana.getRegister(3);
		int S = Control.mana.getRegister(4);
		int T = Control.mana.getRegister(5);
		int F = Control.mana.getRegister(6);
		Control.vs.dec_a.setText(String.format("%d", A));
		Control.vs.hex_a.setText(String.format("%06X", A));
		Control.vs.dec_x.setText(String.format("%d", X));
		Control.vs.hex_x.setText(String.format("%06X", X));
		Control.vs.dec_l.setText(String.format("%d", L));
		Control.vs.hex_l.setText(String.format("%06X", L));
		Control.vs.dec_pc.setText(String.format("%d", PC));
		Control.vs.hex_pc.setText(String.format("%06X", PC));
		Control.vs.status.setText(String.format("%d", SW));
		Control.vs.dec_b.setText(String.format("%d", B));
		Control.vs.hex_b.setText(String.format("%06X", B));
		Control.vs.dec_s.setText(String.format("%d", S));
		Control.vs.hex_s.setText(String.format("%06X", S));
		Control.vs.dec_t.setText(String.format("%d", T));
		Control.vs.hex_t.setText(String.format("%06X", T));
		Control.vs.fpp.setText(String.format("%d", F));
	}

	@Override
	public byte[] getMemory(int locate, int size) {
		// TODO Auto-generated method stub
		byte[] tmp = new byte[size];
		for (int i = 0; i < size; i++) {
			tmp[i] = memory[locate + i];
		}
		return tmp;
	}

	@Override
	public int getRegister(int regNum) {
		// TODO Auto-generated method stub
		return register[regNum];
	}

	@Override
	public void initialDevice(String devName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initializeMemory() {
		// TODO Auto-generated method stub
		memory = new byte[10000];
	}

	@Override
	public void initializeRegister() {
		// TODO Auto-generated method stub
		register = new int[10];
	}

	@Override
	public byte[] readDevice(String devName, int size) {
		// TODO Auto-generated method stub
		File f = new File(devName);
		FileReader fr = null;
		BufferedReader br = null;
		byte[] b = new byte[1];
		try {
			if (f.length() <= num) {
				b[0] = -1;
				return b;
			}
			fr = new FileReader(devName);
			br = new BufferedReader(fr);
			br.skip(size);
			b[0] = (byte) br.read();
			num++;
		} catch (Exception e) {
			e.setStackTrace(null);
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
			}
		}
		return b;
	}

	@Override
	public void setMemory(int location, byte[] data, int size) {
		// TODO Auto-generated method stub
		for (int i = 0; i < size; i++) {
			data[i] -= '0';
			if (data[i] > 9) {
				data[i] -= 7;
			}
			memory[location + i] = data[i];
		}
	}

	@Override
	public void setRegister(int regNum, int value) {
		// TODO Auto-generated method stub
		register[regNum] = value;
	}

	@Override
	public void writeDevice(String devName, byte[] data, int size) {
		// TODO Auto-generated method stub
		FileWriter fw = null;
		BufferedWriter bw = null;
		String s = "";
		try {
			fw = new FileWriter(devName, true);
			bw = new BufferedWriter(fw);
			for (int i = 0; i < 2; i++) {
				s += String.format("%c", data[i]);
			}
			int tmp = Integer.parseInt(s, 16);
			bw.write(String.format("%c", tmp));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {
				}
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
				}
		}
	}
}
