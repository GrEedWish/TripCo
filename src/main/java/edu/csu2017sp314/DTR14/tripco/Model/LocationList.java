 package edu.csu2017sp314.DTR14.tripco.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class LocationList{

	// args: locList 		
	// # an array list store a list of locations
	private ArrayList <Location> locList;
	
	// args: cvs splitchar 	
	// # a char that used for spliting information in cvs format
	private final String cvsSplitRegex = ",";

	// Constructor 
	// # build a new arraylist reference
	protected LocationList(){
		locList = new ArrayList <Location>();
	}

	// get - External interface function
	// # return args:(class)location
	protected Location get(int i){
		return locList.get(i);
	}

	// getsize - External interface function
	// # return args:list.size()
	protected int getsize(){
		return locList.size();
	}

	// showLocList - Output interface function
	// # show a list of locations information in specific format
	protected void showLocList(){
		for (int i = 0; i < 75; i++)
			System.out.print("-");
		System.out.printf("\n%25s%25s%25s%25s%25s\n",
				 "name", "latitude", "longitude", "extras", "template");
		for(int i = 0; i < locList.size(); i++){
			locList.get(i).showLoc();
		}
		for (int i = 0; i < 75; i++)
			System.out.print("-");
		System.out.println();
	}

	// lineHandler
	// args: line / args: title
	// # accept a string line, which contains all information
	// # accept a string array, which is the template to correspond the information
	// # auto-added the location information read from the line to location list
	// Enhancement: -- the title information may have more want to store in location
	protected void lineHandler(String line, String[] title, String[] selection){
		boolean useSelection;
		if (selection.length == 0) {
			useSelection = false;
		} else {
			useSelection = true;
		}
		String name 	= "";
		String latitude = "";
		String longitude= "";
		String extras 	= "";
		String template = "";
		String valid 	= "";
		String id 		= "";
		boolean flag = false;
		int j = 0;
		String parts[] = line.split(cvsSplitRegex);
		for(int i = 0; i < parts.length; i++){
			parts[i] = parts[i].trim();
			title[j] = title[j].trim();
			valid += parts[i];
			// If value starts with a quote, mark that the element may contain commas:
			if (parts[i].indexOf("\"") == 0 && flag == false) {
				flag = true; valid += ", "; continue;
			} 
			// If the element started with a quote, mark that the element has finished handling: 
			if (parts[i].indexOf("\"") == parts[i].length()-1 && flag == true) {
				flag = false;
			} 
			if (flag == true) {
				valid += ", "; continue; 
			}
			// If column on csv corresponds to name, assign this element as name:
			if (title[j].toUpperCase().equals("NAME")) 
				name = valid;
			// If column on csv corresponds to latitude, assign this element as latitude:
			else if (title[j].toUpperCase().equals("LATITUDE"))
				latitude = valid;
			// If column on csv corresponds to longitude, assign this element as longitude:
			else if (title[j].toUpperCase().equals("LONGITUDE"))
				longitude = valid;
			// If column does not correspond to name, lat, or long:
			else {
				// Grab name of ID:
				if (title[j].toUpperCase().equals("ID")) {
					id = valid;
				}
				// If not first element of template, add a comma:
				if(template != "") template += ",";
				// If not first element of extras, add a comma:
				if(extras != "") extras += ",";
				// Add element name to title and element contents to extras:
				template += title[j];
				extras += valid;
			}
			valid = "";
			j++;
		}

		Location loc = new Location(name, latitude, longitude, id, extras, template);
		// Add the location if it's valid AND 
		// the location is either in the selection or a selection is not being used
		if(checkValid(loc) == true && (!useSelection || Arrays.asList(selection).contains(id)))
			locList.add(loc);
	}

	// checkValid - private function
	// args: location
	// # to check if location has already stored in location list
	// # accomplished by comparing key value
	// Enhancement: -- may ues object.contain();
	private boolean checkValid(Location loc){
		if(locList.contains(loc)) return false;
		else return true;
	}

	public void addLocation(Location l1) {
		locList.add(l1);
	}

}