/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csu2017sp314.DTR14.tripco.Presenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class XMLReader {

    public String[] readSelectFile(String filename, StringBuilder csvName) throws FileNotFoundException{
    	if(!new File(filename).exists()) return new String[0];
		Scanner scan = new Scanner(new File(filename));
		ArrayList<String> subs = new ArrayList<String>();
		String temp;
		while((temp = scan.nextLine().trim()) != null){
			if(!checkValid(temp, "<?xml")) break;
			if(!checkValid(temp, "<selection")) break;
			if(!checkValid(temp, "<title")) break;
			if(checkValid(temp, "<filename")){
				temp = temp.substring(temp.indexOf('>')+1);
				temp = temp.substring(0, temp.indexOf('<'));
				csvName.append(temp);
			}
			if(!checkValid(temp, "<destinations")) break;
			while(checkValid(temp, "<id")){
				temp = temp.substring(temp.indexOf('>')+1);
				temp = temp.substring(0, temp.indexOf('<'));
				subs.add(temp.trim());
			} 
			if(!checkValid(temp, "</destinations")) break;
			if(checkValid(temp, "</selection")){
				scan.close();
				return subs.toArray(new String[0]);
			}
		}
		return new String[0];
    }

	private boolean checkValid(String checks, String compareTo){
		if (checks.length() > compareTo.length() &&
				checks.substring(0, compareTo.length()).equalsIgnoreCase(compareTo)){
			return true;
		}
		else return false;
	}
}