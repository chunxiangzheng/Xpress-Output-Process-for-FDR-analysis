/**
 * match react relationship with PSMs
 * @author czheng
 *
 */
import java.util.*;
import java.io.*;
public class MatchReachFileWithPSM {
	public static void main(String[] args) {
	}
	public static Map<String, PSM> getPSMLibrary(String in) {
		Map<String, PSM> m = new HashMap<String, PSM>();
		try {
			FileReader fr = new FileReader(in);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				if (line.startsWith("index")) {
					line = br.readLine();
					continue;
				}
				PSM psm = new  PSM(line);
				String key = psm.fileName + "\t" + psm.scanNum;
				m.put(key, psm);
				line = br.readLine();
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return m;
	}
	public static void match(String react, String out, Map<String, PSM> m) {
		try {
			FileReader fr = new FileReader(react);
			BufferedReader br = new BufferedReader(fr);
			FileOutputStream fout = new FileOutputStream(out);
			PrintStream ps = new PrintStream(fout);
			String line = br.readLine();
			while (line != null) {
				if (line.startsWith("File")) {
					line = br.readLine();
					continue;
				}
				String[] arr = line.split("\t");
				String fileName = arr[0].split("\\.")[0];
				int scanNum = Integer.valueOf(arr[1]);
				String tmp = fileName + "\t";
				String k1 = tmp + (scanNum + 2);
				String k2 = tmp + (scanNum + 3);
				String k3 = tmp + (scanNum + 4);
				String k4 = tmp + (scanNum + 5);
				PSM p1 = m.containsKey(k1) ? m.get(k1) : null;
				PSM p2 = m.containsKey(k2) ? m.get(k2) : null;
				PSM p3 = m.containsKey(k3) ? m.get(k3) : null;
				PSM p4 = m.containsKey(k4) ? m.get(k4) : null;
				if (p1 == null && p2 == null || p3 == null && p4 == null) {
					line = br.readLine();
					continue;
				}
				p1 = getBestPSM(p1, p2);
				p2 = getBestPSM(p3, p4);
				ps.print(line + "\t" + p1.getPepSeq() + "\t" + p1.getPro() + "\t" + p1.getPPM() + "\t" + p1.getEvalue() + "\t" + p1.getProbability() + "\t" + p1.getSpscore());
				ps.println("\t" + p2.getPepSeq() + "\t" + p2.getPro() + "\t" + p2.getPPM() + "\t" + p2.getEvalue() + "\t" + p2.getProbability() + "\t" + p2.getSpscore());
				line = br.readLine();
			}
			ps.close();
			fout.close();
			br.close();
			fr.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	public static PSM getBestPSM(PSM p1, PSM p2) {
		if (p1 == null) return p2;
		if (p2 == null) return p1;
		if (p1.getSpscore() > p2.getSpscore()) return p1;
		if (p1.getSpscore() < p2.getSpscore()) return p2;
		if (p1.getEvalue() < p2.getEvalue()) return p1;
		if (p1.getEvalue() > p2.getEvalue()) return p2;
		return p1;
	}
}
