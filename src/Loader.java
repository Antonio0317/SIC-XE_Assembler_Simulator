import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import sp.project2.interfacepack.SicLoader;

public class Loader implements SicLoader {
	private String[][] Define_table;
	private int Define_line;
	private String[][] Refer_table;
	private int Refer_line;
	private ArrayList<String> Modi_table;
	private int modi_line;
	public String[][] Text_table;
	public int text_line;
	private int start = 0;
	private int size = 0;
	private int cur = 0;
	private int flag = 0;
	
	public Loader() {
		// TODO Auto-generated constructor stub
		Define_table = new String[5000][2];
		Refer_table = new String[5000][2];
		Modi_table = new ArrayList<String>();
		Text_table = new String[5000][3];
		Define_line = 0;
		Refer_line = 0;
		modi_line = 0;
		text_line = 0;
		flag = 0;
	}

	@Override
	public void load(File f) {
		// TODO Auto-generated method stub
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(f.getName());
			br = new BufferedReader(fr);
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				if (!tmp.equals("")) {
					readLine(tmp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		if (Modi_table.size() != 0) {
			for (int i = 0; i < Modi_table.size(); i++) {
				String str = Modi_table.get(i);
				int addr = Integer.parseInt(str.substring(0, 6), 16);
				int len = Integer.parseInt(str.substring(6, 8), 16);
				for (int j = 0; j < text_line; j++) {
					int st = Integer.parseInt(Text_table[j][0], 16);
					int ed = Integer.parseInt(Text_table[j][2], 16);
					if (st <= addr && addr < ed) {
						int orig = Integer.parseInt(Text_table[j][1], 16); // 원래값
						for (int k = 0; k < Define_line; k++) {
							String name = str.substring(9, str.length());
							if (Define_table[k][0].trim().equals(name)) {
								int modi = Integer.parseInt(Define_table[k][1],
										16); // 더하거나 빼줄값
								if (str.charAt(8) == '+') {
									orig += modi;
								} else {
									orig -= modi;
								}
								if (len == 6) {
									Text_table[j][1] = String.format("%06X",
											orig);
									Control.mana.setMemory(st*2, Text_table[j][1].getBytes(), 6);
								}
								else if (len == 5) {
									Text_table[j][1] = String.format("%08X",
											orig);

									Control.mana.setMemory(st*2, Text_table[j][1].getBytes(), 8);
								}
								break;
							}
						}
						break;
					}
				}
			}
		}
		for(int i = 0; i < text_line; i++) {
			Control.vs.inst_model.addElement(Text_table[i][1]);
		}
		Control.vs.instructions.setModel(Control.vs.inst_model);
	}

	@Override
	public void readLine(String s) {
		// TODO Auto-generated method stub

		if (s.charAt(0) == 'H') {
			
			String name = s.substring(1, 7);
			int addr = Integer.parseInt(s.substring(7, 13), 16) + cur;
			Define_table[Define_line][0] = name;
			Define_table[Define_line++][1] = String.format("%06X", addr);
			size = Integer.parseInt(s.substring(13, 19), 16);
			if (flag == 0) {
				Control.vs.progname.setText(name);
				Control.vs.saop.setText(String.format("%06X", addr));
				Control.vs.proglen.setText(String.format("%06X", size));
				Control.vs.firstadd.setText(String.format("%06X", addr));
				flag++;
			}
		} else if (s.charAt(0) == 'D') {
			int cnt = (s.length() - 1) / 12;
			for (int i = 0; i < cnt; i++) {
				Define_table[Define_line][0] = s.substring(1 + 6 * 2 * i,
						(1 + 6 * 2 * i) + 6);
				Define_table[Define_line++][1] = s.substring(7 + 6 * 2 * i,
						(7 + 6 * 2 * i) + 6);
			}
		} else if (s.charAt(0) == 'R') {
			int cnt = (s.length() - 1) / 6;
			for (int i = 0; i < cnt; i++) {
				Refer_table[Refer_line++][0] = s.substring(1 + 6 * i,
						(1 + 6 * i) + 6);
			}
		} else if (s.charAt(0) == 'T') {
			start = cur + Integer.parseInt(s.substring(1, 7), 16);
			int cnt = (s.length() - 9) / 2;
			int begin = 9;
			int len = 0;
			byte[] b;
			while (begin < s.length()) {
				start += len;
				int tmp = Integer.parseInt(s.substring(begin, begin + 2), 16);
				tmp = tmp / 4;
				tmp = tmp * 4;
				String str = String.format("%02X", tmp);
				String form = Control.ss.search_form(str);
				if (str.equals("44") || str.equals("00")) {
						form = "3";
				} else if (str.equals("F0") || str.equals("04")) {
					form = "1";
				}
				if (form.charAt(0) == '2') {
					len = 2;
				}
				else if (form.equals("3/4")) {
					tmp = Integer.parseInt(s.substring(begin + 2, begin + 3),
							16);
					if ((tmp % 2) == 1) {
						len = 4;
					} else {
						len = 3;
					}
				} else if (form.charAt(0) == '1') {
					len = 1;
				} else {
					len = 3;
				}
				Text_table[text_line][1] = s.substring(begin, begin + len * 2);	//명령어 형식에 맞춰서 자른것!
				b = Text_table[text_line][1].getBytes();
				Control.mana.setMemory(start * 2, b, len * 2);
				Text_table[text_line][0] = String.format("%04X", start);
				Text_table[text_line++][2] = String.format("%04X", start + len
						- 1);
				cnt -= len;
				begin += len * 2;
			}
		} else if (s.charAt(0) == 'M') {
			int tmp = Integer.parseInt(s.substring(1, 7), 16);
			tmp += cur;
			Modi_table.add(String.format("%06X%s", tmp, s.substring(7, s.length())));
		} else if (s.charAt(0) == 'E') {
			cur += size;
		}
	}

}
