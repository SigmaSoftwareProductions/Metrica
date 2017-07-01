package com.sigma.metrica;

import java.util.*;
import com.sigma.metrica.*;

public class PercentCompositionCalculator extends Metrica {
	
	public ArrayList<String> calculatePercent (String compound) {
		ArrayList<String> percents = new ArrayList<String>();
		Tuple<ArrayList<String>, ArrayList<Integer>> compoundData = parseCompound (compound);
		ArrayList<String> elements = compoundData.x;
		ArrayList<Integer> coefficients =  compoundData.y;
		MolarMassCalculator percentMasser = new MolarMassCalculator();
		double mass = percentMasser.calculateMass(compound);
		String element = "";
		String percent = "";
		double elementMass = 0;
		for (int i = 0; i < elements.size(); i++) {
			element = elements.get(i);
			elementMass = coefficients.get(i)*percentMasser.calculateMass(element);
			percent = String.valueOf(round(((elementMass*100)/mass), 4));
			percents.add(percent);
		}
		return percents;
	}
}
