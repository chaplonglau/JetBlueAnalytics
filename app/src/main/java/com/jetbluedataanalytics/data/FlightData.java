package com.jetbluedataanalytics.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class FlightData {
	public String from;
	public String to;
	public GregorianCalendar date;
	public double dollarFare;
	public double dollarTax;
	public double pointsFare;
	public double pointsTax;
	public boolean isDomesticRoute, isPrivateFare;
	//DollarFare	DollarTax	PointsFare	PointsTax	IsDomesticRoute	IsPrivateFare

//	public FlightData(String from, String to, String date, double fare, double tax, double pfare, double ptax, boolean b1, boolean b2 ){
//		
//	}

	public FlightData(){

	}

	public FlightData(String from, String to, String d, double dollarFare, double dollarTax, double pointsFare,
			double pointsTax, boolean isDomesticRoute, boolean isPrivateFare) {
		super();
		this.from = from;
		this.to = to;
		this.dollarFare = dollarFare;
		this.dollarTax = dollarTax;
		this.pointsFare = pointsFare;
		this.pointsTax = pointsTax;
		this.isDomesticRoute = isDomesticRoute;
		this.isPrivateFare = isPrivateFare;
		
		String[] split = d.split(" ");
		String[] day = split[0].split("/");
		int dd = Integer.parseInt(day[0]);
		int mm = Integer.parseInt(day[1]);
		int yyyy = Integer.parseInt(day[2]);
		
		String[] time = split[1].split(":");
		int hrs = Integer.parseInt(time[0]);
		int mins = Integer.parseInt(time[1]);
		
		
		date = new GregorianCalendar(yyyy, mm, dd, hrs,mins,0);
	}

	@Override
	public String toString() {
		return "FlightData [from=" + from + ", to=" + to + ", date=" + date.get(Calendar.MONTH)
				+ ", dollarFare=" + dollarFare + ", dollarTax=" + dollarTax
				+ ", pointsFare=" + pointsFare + ", pointsTax=" + pointsTax
				+ ", isDomesticRoute=" + isDomesticRoute + ", isPrivateFare="
				+ isPrivateFare + "]";
	}
	
	
	
	
}
