package uz.albertodelacru.jhxtable.workbook.manager;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import uz.albertodelacru.jhxtable.file.helper.FileHelper;
import uz.albertodelacru.jhxtable.table.html.TableHtml;
import uz.albertodelacru.jhxtable.workbook.helper.WorkbookHelper;

public class WorkbookManager extends Workbook {
	/**
	 * Initialize Workbook without Worksheets
	 */
	public WorkbookManager(){
		super();
	}

	/**
	 * Initialize Workbook with a single Worksheet
	 * @param table
	 */
    public WorkbookManager(TableHtml table, String sheetName){
		super();
		generateExcelSheetFromHtmlTable(table, sheetName);
    }

	public WorkbookManager(Map<String, TableHtml> tables){
		super();

		for(Map.Entry<String, TableHtml> table : tables.entrySet() ){
			generateExcelSheetFromHtmlTable(table.getValue(), table.getKey());
		}
	}

	/**
	 * Limpia y normaliza los valores que puede contener una celda de una tabla HTML:
	 * - <span>
	 * - <a>
	 * @param rawCell
	 * @return
	 */
	private String cleanCell(Element rawCell){
		String cleanedData = "";

		if( !rawCell.children().isEmpty() ){
			// TODO: Ampliar con posible valores que vengan como child de un <td>
			if( rawCell.html().contains("</a>") ){
				cleanedData = rawCell.getElementsByTag("a").html();
			}else if( rawCell.html().contains("</span>") ){
				cleanedData = rawCell.getElementsByTag("span").html();
			}
		}else{
			cleanedData = rawCell.html();
		}

		return cleanedData;
	}

	private int generateSheetTitleRow( HSSFSheet ws, TableHtml table, int numOfRow ){
		// If not Title, early return
		if( !table.isHasTitle() ){
			return numOfRow;
		}

		// Merge Title row to occupy all columns
		ws.addMergedRegion(new CellRangeAddress(0, 0, 0, table.getMaxNumColumns() - 1));
		Row row = ws.createRow(numOfRow);
		Cell cell = row.createCell(0);

		cell.setCellValue(table.getTitleText());
		cell.setCellStyle(titleCellStyle);

		numOfRow++;

		return numOfRow;
	}

	private int generateSheetHeaderRow( HSSFSheet ws, TableHtml table, int numOfRow ){
		// If not Header, early return
		if( !table.isHasHeader() ){
			return numOfRow;
		}

		Row row = ws.createRow(numOfRow);
		Elements headerColumnData = table.getHeaderRow().getElementsByTag("th");
		
		// Loop on header row ALL columns
		for( int i = 0; i < headerColumnData.size(); i++ ){
			Cell cell = row.createCell(i);

			cell.setCellValue( cleanCell( headerColumnData.get(i) ) );
			cell.setCellStyle( headerCellStyle );
		}

		numOfRow++;

		return numOfRow;
	}

	private int generateSheetDataRows( HSSFSheet ws, TableHtml table, int numOfRow ){
		// Generamos los datos de la tabla (NO se tiene en cuenta TOTAL si es que tiene)
		for( int i = 0; i < table.getNumDataRows(); i++ ){
			// Creamos la fila
			Row row = ws.createRow(numOfRow);

			// Guardamos la fila en una variable intermedia
			Element htmlRow = table.getDataRows().get(i);

			// Guardamos los datos de la fila en una variable intermedia
			Elements htmlColumnsData = htmlRow.getElementsByTag("td");

			WorkbookHelper.addExtraValuesToMergeRow(htmlColumnsData, table);

			// Recorremos las columnas/datos de esa fila
			for(int z = 0; z < htmlColumnsData.size(); z++){
				//Comprobamos si se tiene que mergear las FILAS (contiene atributo rowspan)
				if( htmlColumnsData.get(z).hasAttr("rowspan") ){
					String rowNumAttr = htmlColumnsData.get(z).attr("rowspan");

					// Si es mayor que 1 (mergeamos)
					if( Integer.parseInt(rowNumAttr) > 1 ){
						// Añadimos una region de merge al Excel (InitialRow, NumberOfCellsToMerge)
						ws.addMergedRegion(new CellRangeAddress(numOfRow, (numOfRow + Integer.parseInt(rowNumAttr)) - 1, z, z));
					}
				}

				// Creamos la celda de datos en la fila
				Cell cell = row.createCell(z);
				cell.setCellValue( cleanCell(htmlColumnsData.get(z)) );

				// Si estamos en la última columna Y tiene columna de total
				if((z == table.getMaxNumColumns() - 1) && table.isHasTotalColumn()){
					cell.setCellStyle( totalCellStyle );
				}
				else{
					cell.setCellStyle( dataCellStyle );
				}
			}

			// Avanzamos de FILA
			numOfRow++;
		}

		return numOfRow;
	}

	private void generateSheetTotalRow( HSSFSheet ws, TableHtml table, int numOfRow ){
		// If not Title, early return
		if( !table.isHasTotalRow() ){
			return ;
		}

		// Creamos la fila
		Row row = ws.createRow(numOfRow);

		// Guardamos la fila en una variable intermedia
		Element htmlRow = table.getFooterRow();

		// Guardamos los datos de la fila en una variable intermedia
		Elements htmlColumnsData = htmlRow.getElementsByTag("td");

		WorkbookHelper.addExtraValuesToMergeColumns(htmlColumnsData, table);

		// Recorremos las columnas/datos de esa fila
		for(int i = 0; i < htmlColumnsData.size(); i++){
			// Comprobamos si se tiene que mergear las COLUMNAS (contiene atributo colspan)
			if( htmlColumnsData.get(i).hasAttr("colspan") ){
				String colNumAttr = htmlColumnsData.get(i).attr("colspan");

				// Si es mayor que 1 (mergeamos)
				if( Integer.parseInt(colNumAttr) > 1 ){
					// Añadimos una region de merge al Excel (InitialRow, NumberOfCellsToMerge)
					ws.addMergedRegion(new CellRangeAddress(numOfRow, numOfRow, i, (i + Integer.parseInt(colNumAttr)) - 1));
				}
			}

			// Creamos la celda de datos en la fila
			Cell cell = row.createCell(i);
			cell.setCellValue( cleanCell(htmlColumnsData.get(i)) );
			cell.setCellStyle( totalCellStyle );
		}

	}

	private void generateExcelSheetFromHtmlTable(TableHtml table, String sheetName){
		HSSFSheet ws = wb.createSheet(sheetName);

		// Contador general de en qué fila nos encontramos
		int rownum = 0;

		// TITLE
		rownum = generateSheetTitleRow(ws, table,rownum);
		
		// HEADER
		rownum = generateSheetHeaderRow(ws, table, rownum);
		
		// DATOS
		rownum = generateSheetDataRows(ws, table, rownum);

		generateSheetTotalRow(ws, table, rownum);

		// Autoformateamos las columnas (para ajustar el ancho al texto)
		for( int i = 0; i < table.getMaxNumColumns(); i++ ){
			ws.autoSizeColumn(i);
		}
	}

	public void addSheetToWorkbook(TableHtml table, String sheetName){
		generateExcelSheetFromHtmlTable(table, sheetName);
	}

	public void saveWorkbookToLocal(String path){
		FileHelper.saveExcelFileToLocal(path, wb);
	}
}
