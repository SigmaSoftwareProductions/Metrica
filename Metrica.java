package com.sigma.metrica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Metrica {
	public static void p(Object x) {
		System.out.println(x);
	}

	public static void newLine() {
		p("");
		p("");
		p("");
	}

	public static double round(double x, int places) {
		int placer = 1;
		for (int i = 0; i < places; i++) {
			placer *= 10;
		}
		x = Math.round(x * placer);
		x /= placer;
		return x;
	}
	
	public static boolean isInt (String x) {
		try { 
			Integer.parseInt (x); 
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static boolean isUpperCase (String thing) {
		return thing.toUpperCase().equals(thing);
	}

	public static boolean isLowerCase (String thing) {
		return thing.toLowerCase().equals(thing);
	}

	public static <Q> void replace (ArrayList<Q> list, Q x, int index) {
		list.add(index, x);
		list.remove(index + 1);
	}
	
	public static String ask (String prompt) {
		Scanner input = new Scanner(System.in);
		p(prompt);
		return input.nextLine();
	}	

	public static ArrayList<String> split (String x, String delim) {
		ArrayList<String> tokens = new ArrayList<String> ();
		String[] split = x.split(delim);
		for (int i = 0; i < split.length; i++) {
			tokens.add(split[i]);
		}
		return tokens;
	}
	
	public static ArrayList<String> parseElements (String compound) {
		ArrayList<String> elements = new ArrayList<String> ();
		if (compound.equals("")) {
			return elements;
		}
		String prevChar = "";
		String currentChar = "";
		String currentString = "";
		for (int i = 0; i < compound.length(); i++) {
			prevChar = currentChar;
			currentChar = compound.substring(i, i+1);
			if (isInt(currentChar) && !isInt(prevChar)) {
				elements.add(currentString);
				currentString = "";
			} else if (isInt(currentChar) && isInt(prevChar)) {
				currentString = "";
			} else if (prevChar.equals("")) {
				currentString += currentChar;
			} else if (isUpperCase(currentChar) && !isInt(prevChar)) {
				elements.add(currentString);
				currentString = currentChar;
			} else {
				currentString += currentChar;
			}
		}
		if (!isInt(currentString) && !currentString.equals("")) {
			elements.add(currentString);
		}
		return elements;
	}

	public static ArrayList<Integer> parseCoefficients (String compound) {
		ArrayList<Integer> coefficients = new ArrayList<Integer>();
		if (compound.equals("")) {
			return coefficients;
		}
		String prevChar = "";
		String currentChar = "";
		String currentString = "";
		for (int i = 0; i < compound.length(); i++) {
			prevChar = currentChar;
			currentChar = compound.substring(i, i+1);
			if (isInt(currentChar)) {
				currentString += currentChar;
			} else if (prevChar.equals("") || isLowerCase(currentChar)) {
				// xD
			} else if (isUpperCase(currentChar) && !isInt (prevChar)) {
				coefficients.add(1);
			} else {
				coefficients.add(Integer.parseInt(currentString));
				currentString = "";
			}
		}
		if (!isInt(currentChar)) {
			currentString += currentChar;
		}
		if (isInt(currentString)) {
			coefficients.add(Integer.parseInt(currentString));
		} else if (!currentString.equals("")) {
			coefficients.add(1);
		}
		return coefficients;
	}
	
	public static Tuple<ArrayList<String>, ArrayList<Integer>> parseCompound (String compound) {
		ArrayList<String> elements = new ArrayList<String>();
		ArrayList<Integer> coefficients = new ArrayList<Integer>();
		for (int i = 0; i < compound.length(); i++) {
			if (compound.substring(i, i+1).equals("(") || compound.substring(i, i+1).equals(")")) {
				compound = compound.substring(0, i) + " " + compound.substring(i+1);
			}
		}
		ArrayList<String> tokens = split(compound, " ");
		int length = tokens.size();
		for (int i = 0; i < length; i++) {
			if (!tokens.get(i).equals("") && isInt(tokens.get(i).substring(0, 1))) {
				tokens.add(tokens.get(i-1));
				replace(tokens, tokens.get(i).substring(1), i);
			}
		}
		for (int i = 0; i < tokens.size(); i++) {
			elements.addAll(parseElements(tokens.get(i)));
			coefficients.addAll(parseCoefficients(tokens.get(i)));
		}
		for (int i = 0; i < elements.size(); i++) {
			for (int j = i + 1; j < elements.size(); j++) {
				if (elements.get(j).equals(elements.get(i))) {
					elements.remove(j);
					replace(coefficients, coefficients.get(i) + coefficients.get(j), i);
					coefficients.remove(j);
				}
			}
		}
		Tuple<ArrayList<String>, ArrayList<Integer>> compoundData = new Tuple<ArrayList<String>, ArrayList<Integer>>(elements, coefficients);
		return compoundData;
	}
	
	public static void molarMass() {
		String compound = ask("Please enter your compound. Case sensitive.");
		MolarMassCalculator calculator = new MolarMassCalculator();
		newLine();
		p("The molar mass of " + compound + " is " + calculator.calculateMass(compound) + " g/mol.");
		newLine();
	}

	public static void equationCoefficients() {
		EquationBalancer equationBalancer = new EquationBalancer();
		MolarMassCalculator massHelper = new MolarMassCalculator();
		p("Balances equations. Supports up to three products and reactants each.");
		ArrayList<Integer> coefficients = new ArrayList<Integer>();
		ArrayList<String> reactants = new ArrayList<String>();
		ArrayList<String> products = new ArrayList<String>();
		String nextReactant = "";
		int numReactants = Integer.parseInt(ask("How many reactants?"));
		for (int ir = 0; ir < numReactants; ir++) {
			int in = ir + 1;
			nextReactant = ask("Enter reactant #" + in + ".");
			reactants.add(ir, nextReactant);
		}
		String nextProduct = "";
		int numProducts = Integer.parseInt(ask("How many products?"));
		for (int ip = 0; ip < numProducts; ip++) {
			int in = ip + 1;
			nextProduct = ask("Enter Product #" + in + ".");
			products.add(ip, nextProduct);
		}
		int limit = Integer.parseInt(ask("What is the coefficient limit?"));
		coefficients = equationBalancer.balance(reactants, products, limit);
		for (int ir = 0; ir < numReactants; ir++) {
			p("The coefficient of the reactant " + reactants.get(ir) + " is "
					+ coefficients.get(ir) + ".");
			p("The molar mass of this reactant is "
					+ massHelper.calculateMass(reactants.get(ir)) + " g/mol.");
			newLine();
		}
		for (int ip = numReactants + 1; ip < coefficients.size(); ip++) {
			p("The coefficient of the product " + products.get(ip - numReactants - 1) + " is " + coefficients.get(ip) + ".");
			p("The molar mass of this product is " + massHelper.calculateMass(products.get(ip - numReactants - 1)) + " g/mol.");
			newLine();
		}
		p("*Some nice shit for politeness' sake*");
		newLine();
	}

	public static void percentMasses() {
		ArrayList<String> percentages = new ArrayList<String>();
		PercentCompositionCalculator percentFinder = new PercentCompositionCalculator();
		p("This finds the percent masses of the constituent elements of a compound.");
		String compound = ask("What is the compound?");
		percentages = percentFinder.calculatePercent(compound);
		newLine();
		Tuple<ArrayList<String>, ArrayList<Integer>> compoundData = parseCompound (compound);
		ArrayList<String> elements = compoundData.x;
		for (int i1 = 0; i1 < percentages.size(); i1++) {
			p(compound + " is composed of " + percentages.get(i1) + "% " + elements.get(i1) + ".");
		}
	}

	public static void stoichiometricalCalculations() {
		MolarMassCalculator massCalculator = new MolarMassCalculator();
		StoichiometricalOperator operator = new StoichiometricalOperator();
		UnitConverter unitConverter = new UnitConverter();
		String choice1 = ask("This thing does random stuff. Type 'd' for density calculations, or 'o' for other calculations.");
		if (choice1.equals("d")) {

		} else if (choice1.equals("o")) {
			ArrayList<String> ratios = new ArrayList<String>();
			ArrayList<String> reactants = new ArrayList<String>();
			ArrayList<String> products = new ArrayList<String>();
			double result = 0;
			String nextReactant = "";
			int numReactants = Integer.parseInt(ask("How many reactants?"));
			for (int ir = 0; ir < numReactants; ir++) {
				int in = ir + 1;
				nextReactant = ask("Enter reactant #" + in + ".");
				reactants.add(ir, nextReactant);
			}
			String nextProduct = "";
			int numProducts = Integer.parseInt(ask("How many products?"));
			for (int ip = 0; ip < numProducts; ip++) {
				int in = ip + 1;
				nextProduct = ask("Enter Product #" + in + ".");
				products.add(ip, nextProduct);
			}
			String compound1 = ask("What is the input compound?");
			String compound2 = ask("What is the output compound?");
			double ratioIn = 0;
			double ratioOut = 0;
			ratios = operator.findRatios(reactants, products);
			for (String ratio : ratios) {
				String[] ratioSplit = ratio.split(",");
				String ratioSplit1 = ratioSplit[0];
				String ratioSplit2 = ratioSplit[1];
				String[] ratioSplit1Split = ratioSplit1.split("-");
				String[] ratioSplit2Split = ratioSplit2.split("-");
				if (ratioSplit1Split[0].equals(compound1)
						&& ratioSplit2Split[0].equals(compound2)) {
					ratioIn = Double.parseDouble(ratioSplit1Split[1]);
					ratioOut = Double.parseDouble(ratioSplit2Split[1]);
				}
			}
			String choice2 = ask("What form is the input in? Type 's' for solid, 'l' for liquid (ie solution), or 'g' for gas.");
			if (choice2.equals("s")) {
				String choice3 = ask("Is the quantity measured in moles or something else? Type 'm' for moles and 'x' for other units.");
				if (choice3.equals("m")) {
					double moles = Double
							.parseDouble(ask("How many moles of input?"));
					result = operator.useRatio(moles, ratioIn, ratioOut, false);
				} else {
					double grams = unitConverter.fixMass(ask("What is the mass?"));
					result = operator.convertGramsToMoles(grams, massCalculator.calculateMass(compound1), false);
				}
			} else if (choice2.equals("l")) {
				double vol = unitConverter.fixVolume(ask("What is the volume of the solution?"));
				double molarity = Double.parseDouble(ask("What is the molarity of the input solution?"));
				result = operator.convertLiquidToMoles(molarity, vol, false);
				result = operator.useRatio(result, ratioIn, ratioOut, false);
			} else if (choice2.equals("g")) {
				double temp = unitConverter.fixTemp(ask("What is the temperature?"));
				double pressure = unitConverter.fixPressure(ask("What is the pressure?"));
				double vol = unitConverter.fixVolume(ask("What is the volume?"));
				result = operator.convertGasToSTP(temp, vol, pressure, false);
				result = operator.useRatio(result, ratioIn, ratioOut, false);
			}
			String choice3 = ask("What form is the output in? Type 's' for solid, 'l' for liquid (ie solution), or 'g' for gas.");
			if (choice3.equals("s")) {
				String choice31 = ask("Is the quantity measured in moles or something else? Type 'm' for moles and 'x' for other units.");
				if (choice31.equals("m")) {
					p("The result is " + result + " mol " + compound2 + ".");
				} else {
					result = operator.convertMolesToGrams(result, massCalculator.calculateMass(compound2), false);
					result = round(result, 4);
					p("The result is " + result + " g " + compound2 + ".");
				}
			} else if (choice3.equals("l")) {
				double molarity = Double.parseDouble(ask("What is the molarity of the output solution?"));
				result = operator.convertMolesToLiquid(molarity, result, false);
				result = round(result, 4);
				p("The result is " + result + " L " + compound2 + ".");
			} else if (choice3.equals("g")) {
				double temp = unitConverter
						.fixTemp(ask("What is the temperature?"));
				double pressure = unitConverter.fixPressure(ask("What is the pressure?"));
				result = operator.convertGasfromSTP(temp, result, pressure, false);
				result = round(result, 4);
				p("The result is " + result + " L " + compound2 + ".");
			}
		} else {
			p ("Floating on the water like a BUOIII.");
		}
	}
	
	public static void parseRequest (String[] request) {
		if (request.length == 2 && request[0].equals("m")) {
			newLine();
			MolarMassCalculator devsCalculator = new MolarMassCalculator();
			p("The molar mass of " + request[1] + " is " + devsCalculator.calculateMass(request[1]) + " g/mol.");
		} else if (request[0].equals("e")) {
			newLine();
			EquationBalancer devsBalancer = new EquationBalancer();
			MolarMassCalculator devsCalculator = new MolarMassCalculator();
			ArrayList<String> reactants = new ArrayList<String>();
			ArrayList<String> products = new ArrayList<String>();
			ArrayList<Integer> coefficients = new ArrayList<Integer>();
			boolean recordR = false;
			boolean recordP = false;
			for (String arg : request) {
				if (recordR) {
					reactants.add(arg);
				}
				if (recordP) {
					products.add(arg);
				}
				if (arg.equals("-r")) {
					recordR = true;
					recordP = false;
				}
				if (arg.equals("-p")) {
					reactants.remove("-p");
					recordR = false;
					recordP = true;
				}
			}
			coefficients = devsBalancer.balance(reactants, products, 20);
			newLine();
			for (int ir = 0; ir < reactants.size(); ir++) {
				p("The coefficient of the reactant " + reactants.get(ir)
						+ " is " + coefficients.get(ir) + ".");
				p("The molar mass of this reactant is "
						+ devsCalculator.calculateMass(reactants.get(ir))
						+ " g/mol.");
				newLine();
			}
			for (int ip = reactants.size() + 1; ip < coefficients.size(); ip++) {
				p("The coefficient of the product " + products.get(ip - reactants.size() - 1) + " is " + coefficients.get(ip) + ".");
				p("The molar mass of this product is " + devsCalculator.calculateMass(products.get(ip - reactants.size() - 1)) + " g/mol.");
				newLine();
			}
		} else if (request.length == 2 && request[0].equals("p")) {
			newLine();
			PercentCompositionCalculator calculator = new PercentCompositionCalculator();
			ArrayList<String> percents = new ArrayList<String>();
			boolean num = false;
			String percent = "";
			String element = "";
			percents = calculator.calculatePercent(request[1]);
			for (int i1 = 0; i1 < percents.size(); i1++) {
				for (int i2 = 0; i2 < percents.get(i1).length(); i2++) {
					if (percents.get(i1).substring(i2, i2 + 1).equals(",")) {
						num = true;
					} else if (num == false) {
						percent += percents.get(i1).substring(i2, i2 + 1);
					} else {
						element += percents.get(i1).substring(i2, i2 + 1);
					}
				}
				p(request[1] + " is composed of " + percent + "% " + element
						+ ".");
				percent = "";
				element = "";
				num = false;
			}
			newLine();
		} else if (request[0].equals("o")) {
			newLine();
			UnitConverter devsConverter = new UnitConverter();
			StoichiometricalOperator devsOperator = new StoichiometricalOperator();
			MolarMassCalculator devsCalculator = new MolarMassCalculator();
			ArrayList<String> reactants = new ArrayList<String>();
			ArrayList<String> products = new ArrayList<String>();
			ArrayList<String> ratios = new ArrayList<String>();
			boolean recordR = false;
			boolean recordP = false;
			boolean recordI = false;
			boolean recordO = false;
			String input = "";
			String output = "";
			String inputType = "";
			String outputType = "";
			double iTemp = 0;
			double iPressure = 0;
			double iVol = 0;
			double iMolarity = 0;
			double iMoles = 0;
			double iMass = 0;
			double oTemp = 0;
			double oPressure = 0;
			double oMolarity = 0;
			double result = 0;
			int i = 0;
			for (String arg: request) {
				if (recordR) {
					reactants.add(arg);
				}
				if (recordP) {
					products.add(arg);
				}
				if (recordI) {
					input = arg;
				}
				if (recordO) {
					output = arg;
				}
				if (arg.equals("-r")) {
					recordR = true;
					recordP = false;
				}
				if (arg.equals("-p")) {
					reactants.remove("-p");
					recordR = false;
					recordP = true;
				}
				if (arg.equals("-i")) {
					recordR = false;
					recordP = false;
					recordI = true;
				}
				if (arg.equals("-o")) {
					recordI = false;
					recordO = true;
				}
				if (arg.equals("g") || arg.equals("l") || arg.equals("sm") || arg.equals("sg")) {
					if (i == 0) {
						inputType = arg;
					} else {
						outputType = arg;
					}
				}
				if (inputType.equals("g") && !arg.equals("g") && i == 0) {
					recordI = false;
					iTemp = devsConverter.fixTemp(arg);
				} else if (inputType.equals("g") && !arg.equals("g") && iTemp != 0 && i == 0) {
					iPressure = devsConverter.fixPressure(arg);
				} else if (inputType.equals("g") && !arg.equals("g") && iPressure != 0 && i == 0) {
					iVol = devsConverter.fixVolume(arg);
					i = 1;
				} else if (inputType.equals("l") && !arg.equals("l") && i == 0) {
					recordI = false;
					iVol = devsConverter.fixVolume(arg);
				} else if (inputType.equals("l") && !arg.equals("l") && iVol != 0 && i == 0) {
					iMolarity = Double.parseDouble(arg);
					i = 1;
				} else if (inputType.equals("sm") && !arg.equals("sm") && i == 0) {
					recordI = false;
					iMoles = Double.parseDouble(arg);
					i = 1;
				} else if (inputType.equals("sg") && !arg.equals("sg") && i == 0) {
					recordI = false;
					iMass = Double.parseDouble(arg);
					i = 1;
				}
				if (outputType.equals("g") && !arg.equals("g") && i == 1) {
					recordO = false;
					oTemp = devsConverter.fixTemp(arg);
				} else if (outputType.equals("g") && !arg.equals("g") && oTemp != 0 && i == 1) {
					oPressure = devsConverter.fixPressure(arg);
				} else if (outputType.equals("l") && !arg.equals("l") && i == 1) {
					recordO = false;
					oMolarity = Double.parseDouble(arg);
				}
			}
			ratios = devsOperator.findRatios(reactants, products);
			double ratioIn = 0;
			double ratioOut = 0;
			for (String ratio : ratios) {
				String[] ratioSplit = ratio.split(",");
				String ratioSplit1 = ratioSplit[0];
				String ratioSplit2 = ratioSplit[1];
				String[] ratioSplit1Split = ratioSplit1.split("-");
				String[] ratioSplit2Split = ratioSplit2.split("-");
				if (ratioSplit1Split[0].equals(input) && ratioSplit2Split[0].equals(output)) {
					ratioIn = Double.parseDouble(ratioSplit1Split[1]);
					ratioOut = Double.parseDouble(ratioSplit2Split[1]);
				}
			}
			if (inputType.substring(0, 1).equals(("s"))) {
				if (inputType.substring(1, 2).equals("m")) {
					result = devsOperator.useRatio(iMoles, ratioIn, ratioOut, false);
				} else {
					result = devsOperator.convertGramsToMoles(iMass, devsCalculator.calculateMass(input), false);
				}
			} else if (inputType.equals("l")) {
				result = devsOperator.convertLiquidToMoles(iMolarity, iVol, false);
				result = devsOperator.useRatio(result, ratioIn, ratioOut, false);
			} else if (inputType.equals("g")) {
				result = devsOperator.convertGasToSTP(iTemp, iVol, iPressure, false);
				result = devsOperator.useRatio(result, ratioIn, ratioOut, false);
			}
			if (outputType.startsWith("s")) {
				if (outputType.endsWith("m")) {
					p("The result is " + result + " mol " + output + ".");
				} else {
					result = devsOperator.convertMolesToGrams(result, devsCalculator.calculateMass(output), false);
					result = round(result, 4);
					p("The result is " + result + " g " + output + ".");
				}
			} else if (outputType.equals("l")) {
				result = devsOperator.convertMolesToLiquid(oMolarity, result, false);
				result = round(result, 4);
				p("The result is " + result + " L " + output + ".");
			} else if (outputType.equals("g")) {
				result = devsOperator.convertGasfromSTP(oTemp, result, oPressure, false);
				result = round(result, 4);
				p("The result is " + result + " L " + output + ".");
			}
		}
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			p("Welcome to Metrica, the Stoichiometry Software! Currently running dev version �. � Sigma Productions 2017.");
			newLine();
			boolean go = true;
			while (go) {
				String choice1 = ask("Please select your operation. Type 'm' for molar mass calculation, 'e' for equation balancing, 'p' to find percent composition, or 's' for stoichimetrical calculations of other natures. Type 'q' to quit Metrica.");
				if (choice1.equals("m")) {
					newLine();
					molarMass();
				} else if (choice1.equals("e")) {
					newLine();
					equationCoefficients();
				} else if (choice1.equals("s")) {
					newLine();
					stoichiometricalCalculations();
					newLine();
				} else if (choice1.equals("q")) {
					newLine();
					go = false;
					p("*Some nice shit for politeness' sake*");
					newLine();
				} else if (choice1.equals("p")) {
					newLine();
					percentMasses();
					newLine();
				} else {
					newLine();
					p("The sons of the prophet were valiant and bold, and quite unaccustomed to fear; but of all the most reckless, or so I am told, was Abdul Abulbul Amir.");
					newLine();
				}
			}
		} else {
			if (args.length == 2 && args[0].equals("f")) {
				TextFileReader fileReader = new TextFileReader ();
				ArrayList<String> requests = new ArrayList<String>();
				String[] request;
				try {
					requests = fileReader.readFile(args[1]);
					for (int i = 0; i < requests.size(); i++) {
						request = requests.get(i).split(" ");
						newLine();
						p("Request #" + (i + 1) + ":");
						parseRequest (request);
					}
				} catch (IOException badBoy) {
					p ("Second to the Almighty Maggio only.");
				}
				p ("Thank you for using Metrica.");
				newLine();
			} else if (args[0].equals("help")) {
				newLine();
				p("How to use the Command-Line functions of Metrica:");
				p("To be included in dev version � and up! Troll!");
				newLine();
			} else {
				newLine();
				parseRequest (args);
				newLine();
			}
		}
	}
}