package com.jetbluedataanalytics.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Airport implements Serializable{

	
	public String airportCode;
	public String regionName;
	public String marketGroupName;
	public HashSet<Integer> destTypeIds;
	public HashSet<String> destinations;

	
	public Airport(String code, String region, String mgn){
		airportCode = code;
		this.regionName = region;
		marketGroupName = mgn;
		destTypeIds = new HashSet<>();
		destinations = new HashSet<>();
	}

	public void addDestinationType(int x){
		destTypeIds.add(x);
	}
	
	public void addDestination(Airport a){
		destinations.add(a.airportCode);
	}
	
	
	

	@Override
	public String toString() {
		return "Airport [airportCode=" + airportCode + ", regionName=" + regionName + ", marketGroupName="
				+ marketGroupName + ", destTypeIds=" + destTypeIds + ", destinations=" + destinations + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airportCode == null) ? 0 : airportCode.hashCode());
		result = prime * result + ((marketGroupName == null) ? 0 : marketGroupName.hashCode());
		result = prime * result + ((regionName == null) ? 0 : regionName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Airport){
			Airport a = (Airport)obj;
			if((a.airportCode).equals(this.airportCode)){
				return true;
			}
		}
		return false;
	}

	

	
	
	

	


	
	
	
}
