package com.jetbluedataanalytics.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Input implements Serializable{
	public String originCode = null;
	public ArrayList<Integer> destinationTypes = new ArrayList<>();
	public ArrayList<String> regions = new ArrayList<>();
	public boolean international = true;
	public boolean nonstop = true;
	public boolean dateRangeOn = false;
	public int start, end;
	public boolean priceRangeOn = false;
	public double[] priceRange = new double[2];
	public boolean points = false;
	
	
	
	
}
