import os
import re
def reducePepXML(fdir, foutdir):
	for f in os.listdir(fdir):
		fin = open(fdir + "/" + f, "r")
		fout = open(foutdir + "/" + f, "w")
		for line in fin.read().split("\n"):
			if line.startswith("index"):
				fout.write(line + "\n")
			arr = line.split("\t")
			if len(arr) < 11: continue
			if "K[325.13]" in arr[10]:
				fout.write(line + "\n")
		fout.close()
		fin.close()
def mergePepXML(fdir, out):
	fout = open(out, "w")
	title = False	
	for f in os.listdir(fdir):
		fin = open(fdir + "/" + f, "r")
		for line in fin.read().split("\n"):
			if line.startswith("index") and title: continue
			if line.startswith("index") :title = True			
			fout.write(line + "\n")
		fin.close()
	fout.close()
def mergeReact(fdir, out):
	fout = open(out, "w")
	fout.write("File Name\tScan Number\tPrecursor Mass\tPrecursor Error\tPrecursor Charge\tPeptide Mass 1\tPeptide Charge 1\tPeptide Mass 2\tPeptide Charge 2\n")
	for f in os.listdir(fdir):
		fin = open(fdir + "/" + f, "r")
		lines = fin.read().split("\n")
		i = 0
		while i + 2 < len(lines):
			arr = re.split(" +", lines[i + 1])
			fout.write(f + "\t")
			if len(arr) == 15:
				fout.write(arr[3][0: -7] + "\t" + arr[8] + "\t" + arr[14] + "\tN/A\t")
			elif len(arr) > 15:
				fout.write(arr[3][0: -7] + "\t" + arr[8] + "\t" + arr[14] + "\t" + arr[17][0 : -7] + "\t")
			else : 
				print(f + "\tWrong Format")
			arr = re.split(" +", lines[i + 2])
			fout.write(arr[2] + "\t" + arr[6][0 : -7] + "\t" + arr[4] + "\t" + arr[8][0 : -7] + "\n")
			i = i + 3
		fin.close()
	fout.close()

def separateFR(fin, fout_f, fout_r) :
	f = open(fin, "r")
	fout1 = open(fout_f, "w")
	fout2 = open(fout_r, "w")
	for line in f.read().split("\n"):
		arr = line.split("\t")
		if len(arr) < 21: continue
		if arr[10].startswith("rev_") or arr[16].startswith("rev_"):
			fout2.write(arr[0] + "\t" + arr[1] + "\t" + arr[3] + "\t" + arr[11] + "\t" + arr[17] + "\n")
		else :
			fout1.write(arr[0] + "\t" + arr[1] + "\t" + arr[3] + "\t" + arr[11] + "\t" + arr[17] + "\n")

def correctPPM(fin, out):
	f = open(fin, "r")
	fout = open(out, "w")
	for line in f.read().split("\n"):
		if line.startswith("index"): 
			fout.write(line)
			continue
		arr = line.split("\t")
		if len(arr) < 19 : continue
		massdiff = float(arr[16])
		if massdiff > 0 : 
			sign = 1
		else: sign = -1
		massdiff = abs(massdiff)
		if massdiff <= 0.5: 
			fout.write(line + "\n")
			continue
		mass = float(arr[15])
		ppm = float(arr[17])
		if massdiff > 0.5 and massdiff <= 1.5:
			ppm = (massdiff - 1.008) / mass * 1000000
		elif massdiff > 1.5 and massdiff <= 2.5:
			ppm = (massdiff - 1.008 * 2) / mass * 1000000
		elif massdiff > 2.5 and massdiff <= 3.5:
			ppm = (massdiff - 1.008 * 3) / mass * 1000000
		elif massdiff > 3.5 and massdiff <= 4.5:
			ppm = (massdiff - 1.008 * 4) / mass * 1000000
		arr[17] = str(ppm * sign)
		for i in range(0, len(arr) - 1):
			fout.write(arr[i] + "\t")
		fout.write(arr[-1] + "\n") 
		
	fout.close()
	f.close()


