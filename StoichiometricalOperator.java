package com.sigma.metrica;

import java.util.*;
import com.sigma.metrica.*;

public class StoichiometricalOperator extends Metrica {

	public ArrayList<String> findRatios(ArrayList<String> reactants, ArrayList<String> products) {
		ArrayList<String> ratios = new ArrayList<String>();
		ArrayList<Integer> coefficients = new ArrayList<Integer>();
		ArrayList<String> compounds = new ArrayList<String>();
		EquationBalancer balancer = new EquationBalancer();
		double holder1 = 0.0;
		double holder2 = 0.0;
		for (int ir = 0; ir < reactants.size(); ir++) {
			compounds.add(reactants.get(ir));
		}
		for (int ip = 0; ip < products.size(); ip++) {
			compounds.add(products.get(ip));
		}
		coefficients = balancer.balance(reactants, products, 20);
		coefficients.remove(coefficients.indexOf(0));
		for (int i1 = 0; i1 < coefficients.size(); i1++) {
			for (int i2 = 0; i2 < coefficients.size(); i2++) {
				if (i1 != i2) {
					holder1 = coefficients.get(i1);
					holder2 = coefficients.get(i2);
					ratios.add(compounds.get(i1) + "-" + String.valueOf(holder1) + "," + compounds.get(i2) + "-"
							+ String.valueOf(holder2));
				}
			}
		}
		return ratios;
	}

	public double useRatio(double moles, double ratioIn, double ratioOut, boolean molesOnBottom) {
		double nextMoles = 0.0;
		if (molesOnBottom) {
			nextMoles = (moles * ratioIn) / ratioOut;
		} else {
			nextMoles = (moles * ratioOut) / ratioIn;
		}
		return nextMoles;
	}

	public double convertGasToSTP(double temp, double vol, double pressure, boolean volumeOnBottom) {
		double moles = 0.0;
		if (volumeOnBottom) {
			moles = ((temp * 101.325) / (vol * pressure * 273.15)) * 22.4;
		} else {
			moles = ((vol * pressure * 273.15) / (temp * 101.325)) / 22.4;
		}
		return moles;
	}

	public double convertLiquidToMoles(double molarity, double vol, boolean volumeOnBottom) {
		double moles = 0.0;
		if (volumeOnBottom) {
			moles = vol / molarity;
		} else {
			moles = vol * molarity;
		}
		return moles;
	}

	public double convertMolesToGrams(double moles, double molarMass, boolean molesOnBottom) {
		double grams = 0.0;
		if (molesOnBottom) {
			grams = moles / molarMass;
		} else {
			grams = moles * molarMass;
		}
		return grams;
	}

	public double convertGramsToMoles(double grams, double molarMass, boolean gramsOnBottom) {
		double moles = 0.0;
		if (gramsOnBottom) {
			moles = grams * molarMass;
		} else {
			moles = grams / molarMass;
		}
		return moles;
	}

	public double convertMolesToLiquid(double molarity, double moles, boolean molesOnBottom) {
		double vol = 0.0;
		if (molesOnBottom) {
			vol = moles * molarity;
		} else {
			vol = moles / molarity;
		}
		return vol;
	}

	public double convertGasfromSTP(double temp, double moles, double pressure, boolean volumeOnBottom) {
		double nextVol = 0.0;
		double vol = moles * 22.4;
		if (volumeOnBottom) {
			nextVol = (273.15 * pressure) / (vol * 101.325 * temp);
		} else {
			nextVol = (vol * 101.325 * temp) / (273.15 * pressure);
		}
		return nextVol;
	}
}
