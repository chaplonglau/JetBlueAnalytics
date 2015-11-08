package com.jetbluedataanalytics.data;

import android.content.Context;

import com.jetbluedataanalytics.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Parser {
	public static void parseFromCVS(Context context) {
		// GET MARKET GROUP NAMES
		//File marketGroupFile = new File(Constants.PATH + Constants.FILES_TO_PARSE[6] + Constants.FILE_TYPE);
		BufferedReader reader = null; //context.getResources().openRawResource(R.raw.MarketGroup_stg)
		reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.market_group)));


		HashMap<Integer, String> marketMappings = new HashMap<>();
		String line = "";
		try {
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (i != 0) {
					// System.out.println(line);
					String[] mappings = line.split(",");
					marketMappings.put(Integer.parseInt(mappings[0]), mappings[1]);
				}

				i++;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Geographic region id -> Market, Geographic Region Name
		//File regionFile = new File(Constants.PATH + Constants.FILES_TO_PARSE[4] + Constants.FILE_TYPE);

		reader = null;
		reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.geo_regions)));


		class Name {
			public String marketName, regionName;
		}
		HashMap<Integer, Name> regionMappings = new HashMap<>();
		line = "";
		try {
			int i = 0;
			while ((line = reader.readLine()) != null) {

				if (i != 0) {
					// System.out.println(line);
					String[] mappings = line.split(",");
					int rid = Integer.parseInt(mappings[0]);
					Name name = new Name();
					name.marketName = marketMappings.get(Integer.parseInt(mappings[1]));
					name.regionName = mappings[2];
					regionMappings.put(rid, name);
				}

				i++;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// GET AIRPORT CODES (MAP TO GEOGRAPHIC REGIONS)
		//File airportRegionFile = new File(Constants.PATH + Constants.FILES_TO_PARSE[0] + Constants.FILE_TYPE);

		reader = null;
		reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.aiport_region)));


		line = "";
		try {
			int i = 0;
			while ((line = reader.readLine()) != null) {

				if (i != 0) {
					// System.out.println(line);
					String[] mappings = line.split(",");
					int mapId = Integer.parseInt(mappings[1]);
					Name name = regionMappings.get(mapId);
					Airport airport = new Airport(mappings[0], name.regionName, name.marketName);
					JetBlueGraph.airports.add(airport);
					// System.out.println(airport);
				}

				i++;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int x = 2;

		// GET DestTypeName and DisplayOrder
		//File destTypeName = new File(Constants.PATH + Constants.FILES_TO_PARSE[2] + Constants.FILE_TYPE);


		reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.dest_type)));

		class Dest {
			String name;
			int order;
		}
		HashMap<Integer, Dest> destMap = new HashMap<>();
		line = "";
		try {
			int i = 0;
			while ((line = reader.readLine()) != null) {

				if (i != 0) {
					// System.out.println(line);
					String[] mappings = line.split(",");
					int id = Integer.parseInt(mappings[0]);
					String name = mappings[1];
					int displayOrder = Integer.parseInt(mappings[2]);
					Dest d = new Dest();
					d.name = name;
					d.order = displayOrder;

					destMap.put(id, d);
				}

				i++;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// MAKE GRAPH
		//File originDestFile = new File(Constants.PATH + Constants.FILES_TO_PARSE[1] + Constants.FILE_TYPE);

		reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.city_pair_dest_type)));


		line = "";
		try {
			int i = 0;
			while ((line = reader.readLine()) != null) {

				if (i != 0) {
					// System.out.println(line);
					String[] mappings = line.split(",");
					String origin = mappings[0];
					String dest = mappings[1];
					int id = Integer.parseInt(mappings[2]);
					Airport from = getAirport(origin); // There is no ALB
														// airport in
														// AirportRegion,
														// therefore
														// it cannot be found in
														// airports
					Airport to = getAirport(dest);
					if (from != null && to != null) {
						from.addDestination(to);

					}
					if (to != null) {
						to.addDestinationType(id);
					}
					int saf = 2;
				}

				i++;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read Fare data
		//File fareFile = new File(Constants.PATH + Constants.FILES_TO_PARSE[3] + Constants.FILE_TYPE);

		reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.fares)));


		line = "";
		try {
			int i = 0;
			while ((line = reader.readLine()) != null) {

				if (i != 0) {
					// System.out.println(line);
					String[] mappings = line.split(",");
					String origin = mappings[1];
					String dest = mappings[2];
					String date = mappings[3];
					String nonstop = mappings[4];
					String type = mappings[5];
					double fare = Double.parseDouble(mappings[6]);
					double tax = Double.parseDouble(mappings[7]);
					double pfare = Double.parseDouble(mappings[8]);
					double ptax = Double.parseDouble(mappings[9]);
					boolean isDomestic = (Integer.parseInt(mappings[10]) == 1) ? true : false;
					boolean isPrivate = (Integer.parseInt(mappings[11]) == 1) ? true : false;

					FlightData fd = new FlightData(origin, dest, date, fare, tax, pfare, ptax, isDomestic, isPrivate);
					ArrayList<FlightData> list = JetBlueGraph.flights.get(type);
					list.add(fd);
				}

				i++;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void printAirports() {
		Iterator<Airport> it = JetBlueGraph.airports.iterator();
		while (it.hasNext()) {
			Airport a = it.next();
			System.out.println(a);
		}


	}

	public static Airport getAirport(String code) {
		Iterator<Airport> it = JetBlueGraph.airports.iterator();
		while (it.hasNext()) {
			Airport a = it.next();
			if (a.airportCode.equals(code)) {
				return a;
			}
		}

		return null;

	}

	public static Airport getDesitination(String code) {
		Iterator<Airport> it = JetBlueGraph.airports.iterator();
		while (it.hasNext()) {
			Airport a = it.next();
			if (a.airportCode.equals(code)) {
				return a;
			}
		}

		return null;

	}

	public static int getDisplayOrder(int id) {
		switch (id) {
		case 19:
			return 5;
		case 21:
			return 4;
		case 22:
			return 3;
		case 23:
			return 2;
		case 24:
			return 1;
		}
		return 0;
	}
}
