package com.sigma.metrica;

import java.util.*;

public class UnitConverter extends Metrica {
	
	public double fixTemp(String rawTemp) {
		double temp = 0.0;
		if (rawTemp.endsWith("K")) {
			rawTemp = rawTemp.substring(0, rawTemp.length() - 1);
			temp = Double.parseDouble(rawTemp);
			temp = round(temp, 4);
		} else if (rawTemp.endsWith("C")) {
			rawTemp = rawTemp.substring(0, rawTemp.length() - 1);
			temp = Double.parseDouble(rawTemp);
			temp += 273.15;
			temp = round (temp, 4);
		} else if (rawTemp.endsWith("F")) {
			rawTemp = rawTemp.substring(0, rawTemp.length() - 1);
			temp = Double.parseDouble(rawTemp);
			temp -= 32;
			temp = temp * 5 / 9;
			temp += 273.15;
			temp = round (temp, 4);
		} else if (rawTemp.endsWith("R")) {
			rawTemp = rawTemp.substring(0, rawTemp.length() - 1);
			temp = Double.parseDouble(rawTemp);
			temp -= 459.67;
			temp -= 32;
			temp = temp * 5 / 9;
			temp += 273.15;
			temp = round (temp, 4);
		} else {
			temp = Double.parseDouble(rawTemp);
			temp = round (temp, 4);
		}
		return temp;
	}
	
	public double fixPressure (String rawPressure) {
		double pressure = 0.0;
		if (rawPressure.endsWith("kPa")) {
			rawPressure = rawPressure.substring(0, rawPressure.length() - 3);
			pressure = Double.parseDouble(rawPressure);
			pressure = round (pressure, 4);
		} else if (rawPressure.endsWith("atm")) {
			rawPressure = rawPressure.substring(0, rawPressure.length() - 3);
			pressure = Double.parseDouble(rawPressure);
			pressure = pressure * 101.325;
			pressure = round (pressure, 4);
		} else if (rawPressure.endsWith("mmHg")) {
			rawPressure = rawPressure.substring(0, rawPressure.length() - 4);
			pressure = Double.parseDouble(rawPressure);
			pressure = pressure * 101.325 / 760;
			pressure = round (pressure, 4);
		} else if (rawPressure.endsWith("bar")) {
			rawPressure = rawPressure.substring(0, rawPressure.length() - 3);
			pressure = Double.parseDouble(rawPressure);
			pressure = pressure * 100;
			pressure = round (pressure, 4);
		} else {
			pressure = Double.parseDouble(rawPressure);
			pressure = round (pressure, 4);
		}
		return pressure;
	}
	
	public double fixMass (String rawMass) {
		double mass = 0.0;
		if (rawMass.endsWith("g")) {
			rawMass = rawMass.substring(0, rawMass.length() - 1);
			mass = Double.parseDouble(rawMass);
			mass = round (mass, 4);
		} else if (rawMass.endsWith("kg")) {
			rawMass = rawMass.substring(0, rawMass.length() - 2);
			mass = Double.parseDouble(rawMass);
			mass = mass * 1000;
			mass = round (mass, 4);
		} else if (rawMass.endsWith("Mg")) {
			rawMass = rawMass.substring(0, rawMass.length() - 2);
			mass = Double.parseDouble(rawMass);
			mass = mass * 1000 * 1000;
			mass = round (mass, 4);
		} else if (rawMass.endsWith("mg")) {
			rawMass = rawMass.substring(0, rawMass.length() - 2);
			mass = Double.parseDouble(rawMass);
			mass = mass / 1000;
			mass = round (mass, 4);
		} else {
			mass = Double.parseDouble(rawMass);
			mass = round (mass, 4);
		}
		return mass;
	}
	
	public double fixVolume (String rawVol) {
		double vol = 0.0;
		if (rawVol.endsWith("L")) {
			rawVol = rawVol.substring(0, rawVol.length() - 1);
			vol = Double.parseDouble(rawVol);
			vol = round (vol, 4);
		} else if (rawVol.endsWith("mL")) {
			rawVol = rawVol.substring(0, rawVol.length() - 2);
			vol = Double.parseDouble(rawVol);
			vol = vol / 1000;
			vol = round (vol, 4);
		} else if (rawVol.endsWith("m3")) {
			rawVol = rawVol.substring(0, rawVol.length() - 2);
			vol = Double.parseDouble(rawVol);
			vol = vol * 1000;
			vol = round (vol, 4);
		} else {
			vol = Double.parseDouble(rawVol);
			vol = round (vol, 4);
		}
		return vol;
	}
}
