package com.sigma.metrica;

import java.io.*;
import java.util.*;

public class TextFileReader extends Metrica {
	
	public ArrayList<String> readFile (String path) throws IOException {
		ArrayList<String> requests = new ArrayList<String>();
		BufferedReader br = new BufferedReader (new FileReader (path));
		boolean go = true;
		String request = null;
		while (go) {
			request = br.readLine();
			try {
				if (request.equals(null)) {
					go = false;
				} else {
					requests.add(request);
				}
			} catch (NullPointerException npe) {
				go = false;
				break;
			}
		}
		return requests;
	}
}