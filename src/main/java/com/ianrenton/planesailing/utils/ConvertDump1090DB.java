package com.ianrenton.planesailing.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.opencsv.CSVWriter;

/**
 * Standalone util for converting Dump1090's JSON database into CSV files for
 * Plane Sailing to use. We don't want to use the files "raw" as it takes
 * ages to read them in via JSON.
 */
public class ConvertDump1090DB {

	/**
	 * Location of saved copy of
	 * https://github.com/flightaware/dump1090/tree/master/public_html/db
	 */
	private static final File PATH_TO_DUMP1090_DB = new File("C:\\Users\\Ian\\Downloads\\db");

	private static final Map<String, String> ICAO_HEX_TO_REGISTRATION = new HashMap<>();
	private static final Map<String, String> ICAO_HEX_TO_TYPE = new HashMap<>();

	public static void main(String[] args) throws Exception {
		File[] files = PATH_TO_DUMP1090_DB.listFiles((dir, name) -> name.endsWith(".json"));
		for (File f : files) {
			String prefix = f.getName().replaceFirst(".json", "");

			FileInputStream fis = new FileInputStream(f);
			JSONTokener tokener = new JSONTokener(fis);
	        JSONObject object = new JSONObject(tokener);
	        
			for (String hex : object.keySet()) {
				if (!hex.equals("children")) {
					String completeICAOHex = prefix + hex;
					System.out.println(completeICAOHex);

					JSONObject o = object.getJSONObject(hex);
					if (o.has("r")) {
						ICAO_HEX_TO_REGISTRATION.put(completeICAOHex, o.getString("r"));
					}
					if (o.has("t")) {
						ICAO_HEX_TO_TYPE.put(completeICAOHex, o.getString("t"));
					}
				}
			}

			writeFile("aircraft_icao_hex_to_registration.csv", ICAO_HEX_TO_REGISTRATION);
			writeFile("aircraft_icao_hex_to_type.csv", ICAO_HEX_TO_TYPE);
		}
	}

	private static void writeFile(String filename, Map<String, String> map) throws IOException {
		FileWriter fw = new FileWriter(new File(PATH_TO_DUMP1090_DB, filename));
		CSVWriter writer = new CSVWriter(fw);

		List<String> sortedKeys = new ArrayList<>(map.keySet());
		Collections.sort(sortedKeys);
		
		for (String k : sortedKeys) {
			writer.writeNext(new String[] {k, map.get(k)});
		}
		writer.flush();
		writer.close();
	}

}
