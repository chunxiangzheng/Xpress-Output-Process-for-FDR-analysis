/**
 * object of  peptide spectrum match
 * @author czheng
 *
 */
public class PSM {
	public String fileName;
	public int scanNum;
	private double probability, spscore, evalue, mass_calc, mass_exp, ppm, mzRatio;
	private String pep, pep_clean, pro;
	public PSM(String f, int sn){
		fileName = f;
		scanNum = sn;
	}
	public PSM(String line) {
		String[] arr = line.split("\t");
		if (arr.length < 19) return;
		fileName = arr[3].split("\\.")[0];
		scanNum = Integer.valueOf(arr[2]);
		probability = arr[1].equals("-") ? 0 : Double.valueOf(arr[1]);
		spscore = Double.valueOf(arr[7]);
		evalue = Double.valueOf(arr[8]);
		mass_calc = Double.valueOf(arr[14]);
		mass_exp = Double.valueOf(arr[15]);
		ppm = Double.valueOf(arr[17]);
		mzRatio = Double.valueOf(arr[18]);
		pep = arr[10];
		pep_clean = arr[11];
		pro = arr[12];
	}
	public void setPro(String pro) {
		this.pro = pro;
	}
	public void setPepClean(String p) {
		pep_clean = p;
	}
	public void setPepSeq(String pep) {
		this.pep = pep;
	}
	public void setMzRatio(double mz) {
		mzRatio = mz;
	}
	public void setPpm(double ppm) {
		this.ppm = ppm;
	}
	public void setMass_exp(double mass) {
		mass_exp = mass;
	}
	public void setMass_calc(double mass) {
		mass_calc = mass;
	}
	public void setEvalue(double eval) {
		evalue = eval;
	}
	public void setProbability(double p) {
		probability = p;
	}
	public void setSpScore(double sp) {
		spscore = sp;
	}
	public double getProbability() {
		return probability;
	}
	public double getSpscore() {
		return spscore;
	}
	public double getEvalue() {
		return evalue;
	}
	public double getMass_calc() {
		return mass_calc;
	}
	public double getMass_exp() {
		return mass_exp;
	}
	public double getPPM() {
		return ppm;
	}
	public double getMzRatio() {
		return mzRatio;
	}
	public String getPepSeq() {
		return pep;
	}
	public String getCleanPep() {
		return pep_clean;
	}
	public String getPro() {
		return pro;
	}
}
