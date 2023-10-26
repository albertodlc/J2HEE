package uz.albertodelacru.jhxtable.workbook.manager;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import uz.albertodelacru.jhxtable.file.helper.FileHelper;
import uz.albertodelacru.jhxtable.table.cell.HtmlCell;
import uz.albertodelacru.jhxtable.table.cell.MergeCell;
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

	private void generateSheetTitleRow( HSSFSheet ws, TableHtml table ){
		// If not Title, early return
		if( !table.isHasTitle() ){
			return;
		}

		// Añadimos una region de merge al Excel (InitialRow, NumberOfCellsToMerge)
		ws.addMergedRegion(new CellRangeAddress(table.getTitle().getRowNum(), table.getTitle().getRowNum(), table.getTitle().getColNum(), table.getMaxNumColumns() - 1));

		Row row = ws.createRow(table.getTitle().getRowNum());
		Cell cell = row.createCell(table.getTitle().getColNum());

		cell.setCellValue(table.getTitle().getValue());
		cell.setCellStyle(titleCellStyle);
	}

	private boolean isColumnMerge( MergeCell currentCell ){
		return currentCell.getColSpan() > 0;
	}

	private boolean isRowMerge( MergeCell currentCell ){
		return currentCell.getRowSpan() > 0;
	}

	private void generateSheetDataRows( HSSFSheet ws, TableHtml table ){
		int offsetFila = table.isHasTitle() ? 1 : 0;

		for( int x = 0; x < table.getTableMap().size(); x++ ){
			Row row = ws.createRow(x + offsetFila);

			for(int y = 0; y < table.getTableMap().get(x).size(); y++ ){
				Cell cell = row.createCell(y);
				
				if( table.getTableMap().get(x).get(y).isMerged() ){
					// Añadimos una region de merge al Excel (InitialRow, NumberOfCellsToMerge)
					MergeCell currentCell = (MergeCell) table.getTableMap().get(x).get(y);

					if( isColumnMerge(currentCell) ){
						int initialMergeColumn = y;
						int finalMergeColumn = (y + currentCell.getColSpan()) > 0 ? (y + currentCell.getColSpan()) - 1 : 0 ;

						ws.addMergedRegion(new CellRangeAddress(x + offsetFila, x + offsetFila, initialMergeColumn, finalMergeColumn));
					}

					if( isRowMerge(currentCell) ){
						int initialMergeRow = x + offsetFila;
						int finalMergeRow = (x + offsetFila + currentCell.getRowSpan()) > 0 ? (x + offsetFila + currentCell.getRowSpan()) - 1 : 0;

						ws.addMergedRegion(new CellRangeAddress(initialMergeRow, finalMergeRow, y, y));
					}
				}

				cell.setCellValue( table.getTableMap().get(x).get(y).getValue() );
				cell.setCellStyle( dataCellStyle );

				// Last Row
				if( (x == table.getTableMap().size() - 1) ){
					if( table.isHasTotalRow() ){
						cell.setCellStyle( totalCellStyle );
					}
				}

				// Last Column
				if( (y == table.getTableMap().get(x).size() - 1) ){
					if( table.isHasTotalColumn() ){
						cell.setCellStyle( totalCellStyle );
					}
				}

				// Header
				if( (x == 0) ){
					if( table.isHasTotalColumn() ){
						cell.setCellStyle( headerCellStyle );
					}
				}

			}
		}
	}

	private void generateExcelSheetFromHtmlTable(TableHtml table, String sheetName){
		HSSFSheet ws = wb.createSheet(sheetName);

		generateSheetTitleRow(ws, table);
		generateSheetDataRows(ws, table);

		// Autoformateamos las columnas (para ajustar el ancho al texto)
		for( int i = 0; i < table.getMaxNumColumns(); i++ ){
			ws.autoSizeColumn(i);
		}
	}

	public void addSheetToWorkbook(TableHtml table, String sheetName){
		generateExcelSheetFromHtmlTable(table, sheetName);
	}

	public void saveWorkbookToLocal(String path){
		FileHelper.saveExcelFileToLocal(path + "." + DEFAULT_FILENAME, wb);
	}

	public void saveWorkbookToLocal(String path, String filename){
		FileHelper.saveExcelFileToLocal(path + "\\" + WorkbookHelper.addExcelExtensionToFilename(filename), wb);
	}
}
