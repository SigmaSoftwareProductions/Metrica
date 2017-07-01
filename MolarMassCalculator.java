package com.sigma.metrica;
import java.util.*;

public class MolarMassCalculator extends Metrica {
	
	public static Hashtable<String, Double> periodicTable = new Hashtable<String, Double>();
	
	{
        periodicTable.put("H", 1.0079);
		periodicTable.put("He", 4.0026);
		periodicTable.put("Li",  6.9410);
		periodicTable.put("Be", 9.0121);
		periodicTable.put("B",  10.8110);
		periodicTable.put("C", 12.0107);
		periodicTable.put("N", 14.0067);
		periodicTable.put("O", 16.0000);
		periodicTable.put("F", 18.9984);
		periodicTable.put("Ne", 20.1797);
		periodicTable.put("Na",  22.9898);
		periodicTable.put("Mg", 24.3050);
		periodicTable.put("Al", 26.5815);
		periodicTable.put("Si", 28.0855);
		periodicTable.put("P", 30.9738);
		periodicTable.put("S", 32.0660);
		periodicTable.put("Cl", 35.4527);
		periodicTable.put("Ar", 39.9480);
		periodicTable.put("K",  39.0983);
		periodicTable.put("Ca", 40.0780);
		periodicTable.put("Sc", 44.9559);
		periodicTable.put("Ti", 47.8670);
		periodicTable.put("V", 50.9415);
		periodicTable.put("Cr", 51.9961);
		periodicTable.put("Mn", 54.9381);
		periodicTable.put("Fe", 55.8450);
		periodicTable.put("Co", 58.9332);
		periodicTable.put("Ni", 58.6534);
		periodicTable.put("Cu", 63.5450);
		periodicTable.put("Zn", 65.3900);
		periodicTable.put("Ga", 69.7230);
		periodicTable.put("Ge", 72.6200);
		periodicTable.put("As", 74.9216);
		periodicTable.put("Se", 78.9600);
		periodicTable.put("Br", 79.9040);
		periodicTable.put("Kr", 83.8100);
		periodicTable.put("Rb", 85.4678);
		periodicTable.put("Sr", 87.6200);
		periodicTable.put("Y", 88.9059);
		periodicTable.put("Zr", 91.2240);
		periodicTable.put("Nb", 92.9064);
		periodicTable.put("Mo", 95.9400);
		periodicTable.put("Tc", 98.0000);
		periodicTable.put("Ru", 101.0710);
		periodicTable.put("Rh", 102.9055);
		periodicTable.put("Pd", 106.4200);
		periodicTable.put("Ag", 107.8682);
		periodicTable.put("Cd", 112.4110);
		periodicTable.put("In", 114.8180);
		periodicTable.put("Sn", 118.7100);
		periodicTable.put("Sb", 121.7600);
		periodicTable.put("Te", 127.6000);
		periodicTable.put("I", 126.9044);
		periodicTable.put("Xe", 131.2900);
		periodicTable.put("Ba", 137.2370);
		periodicTable.put("W", 183.8400);
		periodicTable.put("Pt", 195.0840);
		periodicTable.put("Au", 196.9666);
		periodicTable.put("Hg", 200.5913);
		periodicTable.put("Pb", 207.2130);
	};
	
	public double calculateMass (String compound) {
		Tuple<ArrayList<String>, ArrayList<Integer>> compoundData = parseCompound (compound);
		ArrayList<String> elements = compoundData.x;
		ArrayList<Integer> coefficients =  compoundData.y;
		double mass = 0;
		for (int i = 0; i < elements.size(); i++) {
			mass += coefficients.get(i)*periodicTable.get(elements.get(i));
		}
		return round(mass, 4);
	}
}
