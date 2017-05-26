import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import sp.project2.interfacepack.VisualSimulator;


public class V_Simulator extends JFrame implements VisualSimulator, ActionListener{
	public V_Simulator() {
	}
	
	public JTextField filename;
	public JTextField progname;
	public JTextField saop;
	public JTextField proglen;
	public JTextField firstadd;
	public JTextField addmemory;
	public JTextField targetadd;
	public JTextField device;
	public JTextField dec_a;
	public JTextField hex_a;
	public JTextField dec_x;
	public JTextField hex_x;
	public JTextField dec_l;
	public JTextField hex_l;
	public JTextField dec_pc;
	public JTextField hex_pc;
	public JTextField status;
	public JTextField dec_b;
	public JTextField hex_b;
	public JTextField dec_s;
	public JTextField hex_s;
	public JTextField dec_t;
	public JTextField hex_t;
	public JTextField fpp;
	public JList<String> instructions;
	public DefaultListModel<String> inst_model;
	public JList<String> logs;
	public DefaultListModel<String> log_model;
	private JButton open;
	private JButton exe_1s;
	private JButton exe_all;
	private JButton quit;
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getActionCommand().equals("open")) {
			FileDialog fd = new FileDialog(this, "파일 열기", FileDialog.LOAD);
			fd.setVisible(true);
			filename.setText(fd.getFile());
			
			Control.ld.load(new File(fd.getFile()));
		}
		else if(ae.getActionCommand().equals("실행(1step)")) {
			oneStep();
		}
		else if(ae.getActionCommand().equals("실행(Alt)")) {
			allStep();
		}
		else {
			System.exit(0);
		}
	}
	@Override
	public void allStep() {
		// TODO Auto-generated method stub
		Control.ss.allStep();
	}
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		getContentPane().setLayout(null);
		setBounds(150, 150, 610, 843);
		JLabel lblFilename = new JLabel("FileName : ");
		lblFilename.setBounds(14, 27, 73, 18);
		getContentPane().add(lblFilename);
		
		filename = new JTextField();
		filename.setBounds(90, 24, 197, 24);
		getContentPane().add(filename);
		filename.setColumns(10);
		
		open = new JButton("open");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		open.setBounds(301, 23, 65, 27);
		getContentPane().add(open);
		
		JLabel lblNewLabel = new JLabel("H (Header Record)");
		lblNewLabel.setBounds(24, 57, 132, 18);
		getContentPane().add(lblNewLabel);
		
		JLabel lblProgramName = new JLabel("Program Name :");
		lblProgramName.setBounds(34, 87, 111, 18);
		getContentPane().add(lblProgramName);
		
		progname = new JTextField();
		progname.setBounds(148, 84, 116, 24);
		getContentPane().add(progname);
		progname.setColumns(10);
		
		JLabel lblStartAddressOf = new JLabel("Start Address of");
		lblStartAddressOf.setBounds(34, 118, 122, 18);
		getContentPane().add(lblStartAddressOf);
		
		JLabel lblObjectProgram = new JLabel("Object Program : ");
		lblObjectProgram.setBounds(34, 139, 122, 18);
		getContentPane().add(lblObjectProgram);
		
		saop = new JTextField();
		saop.setBounds(148, 125, 116, 24);
		getContentPane().add(saop);
		saop.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Length of Program :");
		lblNewLabel_1.setBounds(34, 169, 137, 18);
		getContentPane().add(lblNewLabel_1);
		
		proglen = new JTextField();
		proglen.setBounds(171, 166, 93, 24);
		getContentPane().add(proglen);
		proglen.setColumns(10);
		
		JLabel lblEendRecord = new JLabel("E (End Record)");
		lblEendRecord.setBounds(311, 57, 111, 18);
		getContentPane().add(lblEendRecord);
		
		JLabel lblNewLabel_2 = new JLabel("Address of First Instruction");
		lblNewLabel_2.setBounds(321, 84, 181, 18);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblInObjectProgram = new JLabel("in Object Program : ");
		lblInObjectProgram.setBounds(331, 106, 137, 18);
		getContentPane().add(lblInObjectProgram);
		
		firstadd = new JTextField();
		firstadd.setBounds(465, 103, 113, 24);
		getContentPane().add(firstadd);
		firstadd.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Start Address in Memory");
		lblNewLabel_3.setBounds(311, 152, 169, 18);
		getContentPane().add(lblNewLabel_3);
		
		addmemory = new JTextField();
		addmemory.setBounds(431, 177, 147, 24);
		getContentPane().add(addmemory);
		addmemory.setColumns(10);
		
		JLabel lblTargetAddress = new JLabel("Target Address :");
		lblTargetAddress.setBounds(311, 210, 116, 18);
		getContentPane().add(lblTargetAddress);
		
		targetadd = new JTextField();
		targetadd.setBounds(431, 207, 147, 24);
		getContentPane().add(targetadd);
		targetadd.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Instructions : ");
		lblNewLabel_4.setBounds(311, 240, 93, 18);
		getContentPane().add(lblNewLabel_4);
		
		JScrollPane scrollPane = new JScrollPane();
		inst_model = new DefaultListModel<>();
		instructions = new JList<>(inst_model);
		scrollPane.setViewportView(instructions);
		scrollPane.setBounds(301, 264, 155, 333);
		getContentPane().add(scrollPane);
		
		JLabel lblNewLabel_5 = new JLabel("\uC0AC\uC6A9\uC911\uC778 \uC7A5\uCE58");
		lblNewLabel_5.setBounds(470, 287, 108, 18);
		getContentPane().add(lblNewLabel_5);
		
		device = new JTextField();
		device.setBounds(480, 311, 98, 24);
		getContentPane().add(device);
		device.setColumns(10);
		
		exe_1s = new JButton("\uC2E4\uD589(1step)");
		exe_1s.setBounds(468, 492, 110, 27);
		getContentPane().add(exe_1s);
		
		exe_all = new JButton("\uC2E4\uD589(Alt)");
		exe_all.setBounds(468, 531, 110, 27);
		getContentPane().add(exe_all);
		
		quit = new JButton("\uC885\uB8CC");
		quit.setBounds(468, 570, 110, 27);
		getContentPane().add(quit);
		
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setBounds(24, 210, 62, 18);
		getContentPane().add(lblRegister);
		
		JLabel lblA = new JLabel("A (#0)");
		lblA.setBounds(34, 248, 62, 18);
		getContentPane().add(lblA);
		
		dec_a = new JTextField();
		dec_a.setBounds(84, 245, 73, 24);
		getContentPane().add(dec_a);
		dec_a.setColumns(10);
		
		JLabel lblDec = new JLabel("Dec");
		lblDec.setBounds(83, 226, 62, 18);
		getContentPane().add(lblDec);
		
		hex_a = new JTextField();
		hex_a.setColumns(10);
		hex_a.setBounds(171, 245, 73, 24);
		getContentPane().add(hex_a);
		
		JLabel lblHex = new JLabel("Hex");
		lblHex.setBounds(171, 226, 62, 18);
		getContentPane().add(lblHex);
		
		JLabel lblX = new JLabel("X (#1)");
		lblX.setBounds(34, 281, 62, 18);
		getContentPane().add(lblX);
		
		dec_x = new JTextField();
		dec_x.setColumns(10);
		dec_x.setBounds(84, 278, 73, 24);
		getContentPane().add(dec_x);
		
		hex_x = new JTextField();
		hex_x.setColumns(10);
		hex_x.setBounds(171, 278, 73, 24);
		getContentPane().add(hex_x);
		
		dec_l = new JTextField();
		dec_l.setColumns(10);
		dec_l.setBounds(84, 311, 73, 24);
		getContentPane().add(dec_l);
		
		hex_l = new JTextField();
		hex_l.setColumns(10);
		hex_l.setBounds(171, 311, 73, 24);
		getContentPane().add(hex_l);
		
		dec_pc = new JTextField();
		dec_pc.setColumns(10);
		dec_pc.setBounds(84, 347, 73, 24);
		getContentPane().add(dec_pc);
		
		hex_pc = new JTextField();
		hex_pc.setColumns(10);
		hex_pc.setBounds(171, 347, 73, 24);
		getContentPane().add(hex_pc);
		
		status = new JTextField();
		status.setColumns(10);
		status.setBounds(84, 383, 160, 24);
		getContentPane().add(status);
		
		JLabel lblL = new JLabel("L (#2)");
		lblL.setBounds(34, 314, 62, 18);
		getContentPane().add(lblL);
		
		JLabel lblPc = new JLabel("PC (#8)");
		lblPc.setBounds(25, 350, 62, 18);
		getContentPane().add(lblPc);
		
		JLabel lblSw = new JLabel("SW (#9)");
		lblSw.setBounds(25, 386, 62, 18);
		getContentPane().add(lblSw);
		
		JLabel lblRegisterforXe = new JLabel("Register(for XE)");
		lblRegisterforXe.setBounds(25, 430, 111, 18);
		getContentPane().add(lblRegisterforXe);
		
		JLabel label = new JLabel("Hex");
		label.setBounds(171, 446, 62, 18);
		getContentPane().add(label);
		
		dec_b = new JTextField();
		dec_b.setColumns(10);
		dec_b.setBounds(84, 465, 73, 24);
		getContentPane().add(dec_b);
		
		JLabel label_1 = new JLabel("Dec");
		label_1.setBounds(83, 446, 62, 18);
		getContentPane().add(label_1);
		
		hex_b = new JTextField();
		hex_b.setColumns(10);
		hex_b.setBounds(171, 465, 73, 24);
		getContentPane().add(hex_b);
		
		dec_s = new JTextField();
		dec_s.setColumns(10);
		dec_s.setBounds(84, 501, 73, 24);
		getContentPane().add(dec_s);
		
		hex_s = new JTextField();
		hex_s.setColumns(10);
		hex_s.setBounds(171, 501, 73, 24);
		getContentPane().add(hex_s);
		
		dec_t = new JTextField();
		dec_t.setColumns(10);
		dec_t.setBounds(84, 537, 73, 24);
		getContentPane().add(dec_t);
		
		hex_t = new JTextField();
		hex_t.setColumns(10);
		hex_t.setBounds(171, 537, 73, 24);
		getContentPane().add(hex_t);
		
		fpp = new JTextField();
		fpp.setColumns(10);
		fpp.setBounds(84, 573, 160, 24);
		getContentPane().add(fpp);
		
		JLabel lblB = new JLabel("B (#3)");
		lblB.setBounds(34, 468, 62, 18);
		getContentPane().add(lblB);
		
		JLabel lblS = new JLabel("S (#4)");
		lblS.setBounds(34, 504, 62, 18);
		getContentPane().add(lblS);
		
		JLabel lblT = new JLabel("T (#5)");
		lblT.setBounds(34, 540, 62, 18);
		getContentPane().add(lblT);
		
		JLabel lblF = new JLabel("F (#6)");
		lblF.setBounds(34, 576, 62, 18);
		getContentPane().add(lblF);
		
		JLabel lblLog = new JLabel("Log (\uBA85\uB839\uC5B4 \uC218\uD589 \uAD00\uB828) :");
		lblLog.setBounds(24, 619, 160, 18);
		getContentPane().add(lblLog);
		
		JScrollPane scrollPane1 = new JScrollPane();
		log_model = new DefaultListModel<>();
		logs = new JList<>(log_model);
		scrollPane1.setViewportView(logs);
		scrollPane1.setBounds(24, 643, 554, 141);
		getContentPane().add(scrollPane1);
		
		open.addActionListener(this);
		exe_1s.addActionListener(this);
		exe_all.addActionListener(this);
		quit.addActionListener(this);
		setVisible(true);
	}
	@Override
	public void oneStep() {
		// TODO Auto-generated method stub
		Control.ss.oneStep();
	}
}
