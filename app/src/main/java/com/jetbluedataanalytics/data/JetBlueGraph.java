package com.jetbluedataanalytics.data;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class JetBlueGraph implements Serializable {

	public static HashSet<Airport> airports;
	public static HashMap<String, ArrayList<FlightData>> flights;
	public static ArrayList<FlightData> flightsToShow;

	public JetBlueGraph(Context context) {
		init();
		// Parse in Data
		Parser.parseFromCVS(context);

		//initData1();
		//initData2();

		// Check Data
		// Parser.printAirports();

		// writeToJS();
		// writeToJS1();
		// write();


	}


	private void init() {
		airports = new HashSet<>();
		flights = new HashMap<>(2);

		flights.put(Constants.POINTS, new ArrayList<FlightData>());
		flights.put(Constants.LOWEST, new ArrayList<FlightData>());
	}

	public static ArrayList<FlightData> fakeQuery() {
		Input input = new Input();
		input.originCode = "JFK";
		input.destinationTypes.add(Constants.NIGHTLIFE);
		input.destinationTypes.add(Constants.ROMANCE);
		input.destinationTypes.add(Constants.BEACH);

		input.regions.add(Constants.OTHER_CARRIBEAN);
		input.regions.add(Constants.THE_SOUTH);
		input.regions.add(Constants.MTN_WEST);
		input.priceRangeOn = true;
		input.priceRange[0] = 0;
		input.priceRange[1] = 500;
		input.nonstop = true;
		input.dateRangeOn = true;
		input.start = Calendar.JANUARY;
		input.end = Calendar.MARCH;

		flightsToShow =  query(input);

//		HashSet<Pair> pairs = new HashSet<>();
//		for(int i = 0 ; i < flightsToShow.size(); i++){
//			Pair p = new Pair();
//			p.from = flightsToShow.get(i).from;
//			p.to = flightsToShow.get(i).to;
//			pairs.add(p);
//		}
//
//		return pairs;

		return flightsToShow;
	}

	public static ArrayList<FlightData> getFlightDataForOriginAirport(String origin, String dest){
		ArrayList<FlightData> temp = flights.get(Constants.POINTS);
		ArrayList<FlightData> data = new ArrayList<>();
		for(int i = 0 ; i < temp.size() ; i++){
			if(temp.get(i).from.equals(origin)){
				data.add(temp.get(i));
			}
		}

		return data;
	}

	public static ArrayList<FlightData> query(Input input) {
		Airport origin = null;

		ArrayList<FlightData> resultantFlightList = new ArrayList<FlightData>();
		ArrayList<Airport> prospectiveDests = new ArrayList<>();
		HashMap<Airport, Integer> rankings = new HashMap<>();
		origin = Parser.getAirport(input.originCode);

		if (input.nonstop) {
			Iterator<String> dests = origin.destinations.iterator();
			while (dests.hasNext()) {
				String destCode = dests.next();
				Airport dest = Parser.getAirport(destCode);
				prospectiveDests.add(dest);

			}

			// RANK EACH CITY
			for (int i = 0; i < prospectiveDests.size(); i++) {
				Airport a = prospectiveDests.get(i);
				rankings.put(a, 0);
				for (int j = 0; j < input.destinationTypes.size(); j++) {
					if (a.destTypeIds.contains(input.destinationTypes.get(j))) {
						int rank = (input.destinationTypes.size() - j) * Constants.DEST_TYPE_PREC;
						rankings.put(a, rank + rankings.get(a));
						break;
					}
				}
				int rank2;
				for (int j = 0; j < input.regions.size(); j++) {
					if (a.regionName.equals(input.regions.get(j))) {
						rank2 = (input.regions.size() - j) * Constants.REGION_PREC;
						rankings.put(a, rank2 + rankings.get(a));
						break;
					}
				}
				int x = 2;

			}

			prospectiveDests.clear();
			for (Airport a : rankings.keySet()) {
				int rank = rankings.get(a);
				int index = 0;
				for (int j = 0; j < prospectiveDests.size(); j++) {
					int rank2 = rankings.get(prospectiveDests.get(j));
					if (rank2 < rank) {
						break;
					}
					index++;
				}

				prospectiveDests.add(index, a);

			}

			// resultantFlightList is the final overall list of flight data
			Iterator<Airport> pDests = prospectiveDests.iterator();
			ArrayList<FlightData> tempFlightList = flights.get(Constants.LOWEST);

			while (pDests.hasNext()) {
				Airport temp = pDests.next();
				for (int i = 0; i < tempFlightList.size(); i++) {
					if (temp.airportCode.equals(tempFlightList.get(i).to)
							&& origin.airportCode.equals(tempFlightList.get(i).from))
						resultantFlightList.add(tempFlightList.get(i));
				}

			}

			for (int i = 0; i < resultantFlightList.size(); i++) {
				int startMonth = input.start;
				int endMonth = input.end;

				if (input.priceRangeOn && (resultantFlightList.get(i).dollarFare > input.priceRange[1]
						|| resultantFlightList.get(i).dollarFare < input.priceRange[0])) {
					resultantFlightList.remove(i);
					i--;
				} else if (!input.dateRangeOn && resultantFlightList.get(i).date.get(Calendar.MONTH) < startMonth) {
					resultantFlightList.remove(i);
					i--;
				} else if (input.dateRangeOn && (resultantFlightList.get(i).date.get(Calendar.MONTH) < startMonth
						|| resultantFlightList.get(i).date.get(Calendar.MONTH) > endMonth)) {
					resultantFlightList.remove(i);
					i--;
				} else {
				}
			}

//			for (int i = 0; i < resultantFlightList.size(); i++) {
//				System.out.println(resultantFlightList.get(i));
//			}



		}
		return resultantFlightList;
	}

	private void initData2(){

	}


}
