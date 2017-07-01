package com.sigma.metrica;

import java.util.*;
import com.sigma.metrica.*;

public class EquationBalancer extends Metrica {

	public ArrayList<Integer> balance(ArrayList<String> reactants,
			ArrayList<String> products, int limit) {
		MolarMassCalculator balanceCalculator = new MolarMassCalculator();
		ArrayList<Integer> coefficients = new ArrayList<Integer>();
		ArrayList<Double> reactantMasses = new ArrayList<Double>();
		ArrayList<Double> productMasses = new ArrayList<Double>();
		double massHolderR = 0.0;
		double massHolderP = 0.0;
		for (String reactant : reactants) {
			massHolderR = balanceCalculator.calculateMass(reactant);
			reactantMasses.add(massHolderR);
		}
		for (String product : products) {
			massHolderP = balanceCalculator.calculateMass(product);
			productMasses.add(massHolderP);
		}
		massHolderR = 0.0;
		massHolderP = 0.0;
		for (int ix = 0; ix <= reactants.size() + products.size(); ix++) {
			coefficients.add(ix, 0);
		}
		int i1rX = 0;
		int i1pX = 0;
		int i2rX = 0;
		int i2pX = 0;
		for (int i1r = 1; i1r <= limit; i1r++) {
			i1rX = i1r;
			coefficients.set(0, i1r);
			massHolderR += reactantMasses.get(0);
			massHolderR = round(massHolderR, 4);
			if (Math.abs(massHolderR - massHolderP) <= 0.00001) {
				break;
			}
			for (int i1p = 1; i1p <= limit; i1p++) {
				i1pX = i1p;
				coefficients.set(reactants.size() + 1, i1p);
				massHolderP += productMasses.get(0);
				massHolderP = round(massHolderP, 4);
				if (reactants.size() >= 2) {
					for (int i2r = 1; i2r <= limit; i2r++) {
						i2rX = i2r;
						coefficients.set(1, i2r);
						massHolderR += reactantMasses.get(1);
						massHolderR = round(massHolderR, 4);
						if (Math.abs(massHolderR - massHolderP) <= 0.00001) {
							break;
						}
						if (products.size() >= 2) {
							for (int i2p = 1; i2p <= limit; i2p++) {
								i2pX = i2p;
								coefficients.set(reactants.size() + 2, i2p);
								massHolderP += productMasses.get(1);
								massHolderP = round(massHolderP, 4);
								if (Math.abs(massHolderR - massHolderP) <= 0.00001) {
									break;
								}
							}
							if (Math.abs(massHolderR - massHolderP) <= 0.00001) {
								break;
							}
							if (Math.abs(massHolderR - massHolderP) <= 0.00001) {
								break;
							}
							if (i2pX == limit) {
								massHolderP -= limit * productMasses.get(1);
								massHolderP = round(massHolderP, 4);
							}
						} else if (products.size() >= 2) {
							if (Math.abs(massHolderR - massHolderP) <= 0.00001) {
								break;
							}
							for (int i2p = 1; i2p <= limit; i2p++) {
								coefficients.set(reactants.size() + 2, i2p);
								massHolderP += productMasses.get(1);
								massHolderP = round(massHolderP, 4);
								if (Math.abs(massHolderR - massHolderP) <= 0.00001) {
									break;
								}
							}
						}
						if (Math.abs(massHolderR - massHolderP) <= 0.00001) {
							break;
						}
						if (i2rX == limit) {
							massHolderR -= limit * reactantMasses.get(1);
							massHolderR = round(massHolderR, 4);
						}
						if (i2pX == limit && reactants.size() < 2) {
							massHolderP -= limit * productMasses.get(1);
							massHolderP = round(massHolderP, 4);
						}
					}
					if (Math.abs(massHolderR - massHolderP) <= 0.00001) {
						break;
					}
					if (i1pX == limit) {
						massHolderP -= limit * productMasses.get(0);
						massHolderP = round(massHolderP, 4);
					}
				}
				if (Math.abs(massHolderR - massHolderP) <= 0.00001) {
					break;
				}
			}
			if (Math.abs(massHolderR - massHolderP) <= 0.00001) {
				break;
			}
		}
		return coefficients;
	}
}
