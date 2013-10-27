package com.frlncr.parser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

public class CSVParser {
	public List[] readScores(String fileName) throws Exception {
		List<Double> returnValue = new ArrayList<Double>();
		List<Double> returnXValue = new ArrayList<Double>();
//		String fileName = "C:\\Users\\MR SHAFIEI\\Desktop\\Reihane\\test3_scan.csv";

		ICsvListReader listReader = null;
		try {
			listReader = new CsvListReader(new FileReader(fileName),
					CsvPreference.STANDARD_PREFERENCE);

			for (int i = 0; i < 16; i++) {
				listReader.read();
			}

			final CellProcessor[] processors = getProcessors();
			List<Object> customerList;
			while ((customerList = listReader.read(processors)) != null ) {
				returnValue.add(((Double) customerList.get(1)));
				returnXValue.add(((Double) customerList.get(0)));
			}

		} finally {
			if (listReader != null) {
				listReader.close();
			}
		}
		
		List[] vals = new List[]{returnValue, returnXValue};

		return vals;
	}


	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] {
				new ParseDouble(), new ParseDouble(), new ParseDouble(),
				new ParseDouble(), new ParseDouble(), new ParseDouble(),
				new ParseDouble() };

		return processors;
	}
}
