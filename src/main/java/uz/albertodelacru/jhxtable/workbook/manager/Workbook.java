package uz.albertodelacru.jhxtable.workbook.manager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

public class Workbook {
    // Default CELL Styles (All tables have this styles)
    protected CellStyle headerCellStyle = null;
    protected CellStyle totalCellStyle = null;
    protected CellStyle dataCellStyle = null;
	protected CellStyle titleCellStyle = null;

    protected HSSFWorkbook wb = new HSSFWorkbook();

    public Workbook(  ){
        // Generate Default styles
		headerCellStyle = wb.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerCellStyle.setAlignment( CellStyle.ALIGN_CENTER );
		headerCellStyle.setVerticalAlignment( CellStyle.VERTICAL_CENTER );
		
		totalCellStyle = wb.createCellStyle();
		totalCellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		totalCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		totalCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		totalCellStyle.setVerticalAlignment( CellStyle.VERTICAL_CENTER );

		dataCellStyle = wb.createCellStyle();
		dataCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		dataCellStyle.setVerticalAlignment( CellStyle.VERTICAL_CENTER );

		titleCellStyle = wb.createCellStyle();
        titleCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		titleCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		titleCellStyle.setAlignment( CellStyle.ALIGN_CENTER );
		titleCellStyle.setVerticalAlignment( CellStyle.VERTICAL_CENTER );
    }
}
