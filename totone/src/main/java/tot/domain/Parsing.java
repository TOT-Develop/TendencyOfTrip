package tot.domain;
 
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

public class Parsing {

	public static void main(String[] args) { 
		IOUtils.setByteArrayMaxOverride(600_000_000);
		
		String excelFilePath = "D:\\embeded\\restaurant\\restaurant15.xlsx";
		String jsonFilePath = "D:\\embeded\\restaurant\\restaurant15.json";
		
		try (FileInputStream file = new FileInputStream(excelFilePath);
				Workbook workbook = new XSSFWorkbook(file)){
			
			Sheet sheet = workbook.getSheetAt(0);
			List<Map<String, String>> data = new ArrayList();
			
			Row headerRow = sheet.getRow(0);
			for (int i=1; i <= sheet.getLastRowNum(); i++) {
//			for (int i=1; i <= 100; i++) {
				Row row = sheet.getRow(i);
				if(row == null) continue;
				
				Map<String,String> rowData = new HashMap<>();
				for (int j=0; j< headerRow.getLastCellNum(); j++) {
					String header = headerRow.getCell(j).getStringCellValue();
					String cellValue = row.getCell(j) != null ? row.getCell(j).toString() : " ";
					rowData.put(header, cellValue);
				}
				data.add(rowData);
			}
			
			Gson gson = new Gson();
			String json = gson.toJson(data);
			
			try(FileWriter fileWriter = new FileWriter(jsonFilePath)){
				fileWriter.write(json);
			}
			System.out.println("JSON으로 변환되었습니다.");
			
			
		}catch (IOException io) {
			io.printStackTrace();
		}
		
		
	}//main
	
	
}//class
