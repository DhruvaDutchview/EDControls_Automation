package dhruvakumar.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;

public class DataReader {

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws FileNotFoundException, IOException, ParseException
	{
		//reading the jsonfile
		
		File file=new File(filePath);
		String JsonContent=FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		
//		JSONParser jsonparse=new JSONParser();
//		JSONObject jsonObject=(JSONObject)jsonparse.parse(new FileReader(file));
//		
//		String JsonContent=(String)jsonObject.toString();
		
		//String to Hashmap using JAcksonDataBind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(JsonContent, new TypeReference<List<HashMap<String, String>>>() {
		});
		
		return data;
		
		
		
	}
	
	
	
	
	
	
}
