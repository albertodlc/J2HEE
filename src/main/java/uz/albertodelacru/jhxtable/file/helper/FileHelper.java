package uz.albertodelacru.jhxtable.file.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FileHelper {
	
	private FileHelper() {
		throw new IllegalStateException("Utility class");
	}

	private static String cleanString(String stringToClean){
		String cleaned = stringToClean.replace("\u00a0"," ");

		return cleaned;
	}

    public static void saveExcelFileToLocal(String path, HSSFWorkbook wb ){
		try {
			FileOutputStream out = new FileOutputStream(path);
			wb.write(out);
			out.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readExcelFileFromLocal(String path){
		StringBuilder contentBuilder = new StringBuilder();

		InputStream is = FileHelper.class.getResourceAsStream(path);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cleanString(contentBuilder.toString());
	}
}
