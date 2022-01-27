package org.generation.italy.utilities;

public class Strings {

	private Strings() {
		
	}
	public static Boolean isBlank8(String s) {
		if(s!=null) {
			if(s.trim().equals("")) {
				return true;
			}			
		}
		return true;
	}
}
