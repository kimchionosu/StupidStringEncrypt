package fileTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
	
	static Random rand = new Random();

	public static void main(String[] args) throws IOException {

		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("mode: ");
		String mode = reader.readLine();
		if(mode.equalsIgnoreCase("encrypt")) {
			String message = reader.readLine();
			System.out.println(encrypt(message));
		}else if(mode.equalsIgnoreCase("decrypt")) {
			String message = reader.readLine();
			System.out.println(decrypt(message));
		}
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public static String binaryToString(String binary) {
		String[] singleBinaryArray = binary.toString().split(" ");
		String finalResult = "";
		for (String string : singleBinaryArray) {
		Character c = (char) Integer.parseInt(string, 2);
		    finalResult += c.toString();
		}
		return finalResult;
	}
	
	private static String encrypt(String message) {
		//OUR MESSAGE BUT BREAKDOWN INTO ARRAY OF CHARACTERS
		char[] charData = message.toCharArray();
		StringBuilder out = new StringBuilder();
		//SIMPLE WAY TO ENCRYPT STRING AND CONVERT 
		//CHARACTER TO BINARY AND APPEND INTO A STRING
		for(char c : charData) {
			c += -1;
			//System.out.print(c);
			out.append(Integer.toBinaryString(c) + (rand.nextBoolean() ? ">" : "!"));
		}
		
		String realOut = out.toString().replaceAll("1", "A").replaceAll("0", "a");
		//System.out.println(realOut);
		//System.out.println(message);
		
		//Thanks god stackoverflow exists
		int count = 1;
		char last = realOut.charAt(0);

		StringBuilder output = new StringBuilder();

		for(int i = 1; i < realOut.length(); i++){
		    if(realOut.charAt(i) == last){
		    count++;
		    }else{
		        if(count > 1){
		            output.append(""+count+last);
		        }else{
		            output.append("1" + last);
		        }
		    count = 1;
		    last = realOut.charAt(i);
		    }
		}
		if(count > 1){
		    output.append(""+count+last);
		}else{
		    output.append(last);
		}
		return output.toString();
	}
	
	private static String decrypt(String encrypted) {
		String outA = "";
		for(int i = 0; i < encrypted.length() - 1; i++) {
			char c = encrypted.charAt(i);
			char c1 = encrypted.charAt(i + 1);
			if(isInteger(String.valueOf(c))) {
				int num = Integer.parseInt(String.valueOf(c));
				for(int a = 0; a < num; a++) {
					outA += c1;
				}
			}
		}
		String a = outA.replaceAll("!", " ").replaceAll(">", " ");
		System.out.println("Binary AAA: " + a);
		
		String[] ab = a.split(" ");
		String newA = "";
		for(int i = 0; i < ab.length; i++) {
			String asd = ab[i];
			newA += asd.replaceAll("A", "1").replaceAll("a", "0") + " ";
		}
		//System.out.println(newA);
		
		System.out.println("Decode binary: " + binaryToString(newA));
		
		char[] charData1 = binaryToString(newA).toCharArray();
		StringBuilder out1 = new StringBuilder();
		//yea im suck :(
		for(char c : charData1) {
			c += 1;
			//System.out.print(c);
			out1.append(c);
		}
		
		return out1.toString();
	}
}
