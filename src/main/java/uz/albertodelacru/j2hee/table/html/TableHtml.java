package uz.albertodelacru.j2hee.table.html;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import uz.albertodelacru.j2hee.http.helper.HttpHelper;
import uz.albertodelacru.j2hee.table.cell.HtmlCell;
import uz.albertodelacru.j2hee.table.cell.MergeCell;
import uz.albertodelacru.j2hee.table.helper.TableHelper;

public class TableHtml {
	private int maxNumRows = 0;
	private int maxNumColumns = 0;
	private int numDataRows = 0;

	// Table section checks
	private boolean hasHeader = false;
	private boolean hasTitle = false;

	// Used to apply basic style to those columns and rows
	private boolean hasTotalRow = false;
	private boolean hasTotalColumn = false;

	// Raw DOM style table
	private Document jsoupHtmlTable = null;

	// Merge Map
	private Map<Integer, List<HtmlCell>> tableMap = new LinkedHashMap<>();

	// Title
	private MergeCell title = null;

	public TableHtml(String rawHtmlTable, boolean hasTotalRow, boolean hasTotalColumn){
		this.jsoupHtmlTable = Jsoup.parse(rawHtmlTable);
		this.jsoupHtmlTable.charset( StandardCharsets.UTF_8 );
		
		// General HTML TABLE Features
		this.hasTotalRow = hasTotalRow;
		this.hasTotalColumn = hasTotalColumn;
		this.hasTitle = isTitlePresent(jsoupHtmlTable);
		this.hasHeader = isHeaderPresent(jsoupHtmlTable);
		this.maxNumColumns = calculateMaxTableColumns(jsoupHtmlTable);
		this.maxNumRows = calculateMaxTableRows(jsoupHtmlTable);

		// TABLE MAPs
		this.tableMap = TableHelper.initializeTableMap(this.maxNumRows, this.maxNumColumns);
		TableHelper.updateTableCellsMapWithMergeAndValueInfo(tableMap, jsoupHtmlTable, this.maxNumColumns);

		if( this.hasTitle ){
			this.title = processHtmlTableTitle(jsoupHtmlTable, this.maxNumColumns);
		}
	}

	public TableHtml(HttpHelper httpHelper, boolean hasTotalRow, boolean hasTotalColumn){
		this.jsoupHtmlTable = Jsoup.parse(httpHelper.makeHttpRequest());
		this.jsoupHtmlTable.charset( StandardCharsets.UTF_8 );
		
		// General HTML TABLE Features
		this.hasTotalRow = hasTotalRow;
		this.hasTotalColumn = hasTotalColumn;
		this.hasTitle = isTitlePresent(jsoupHtmlTable);
		this.hasHeader = isHeaderPresent(jsoupHtmlTable);
		this.maxNumColumns = calculateMaxTableColumns(jsoupHtmlTable);
		this.maxNumRows = calculateMaxTableRows(jsoupHtmlTable);

		// TABLE MAPs
		this.tableMap = TableHelper.initializeTableMap(this.maxNumRows, this.maxNumColumns);
		TableHelper.updateTableCellsMapWithMergeAndValueInfo(tableMap, jsoupHtmlTable, this.maxNumColumns);

		if( this.hasTitle ){
			this.title = processHtmlTableTitle(jsoupHtmlTable, this.maxNumColumns);
		}
	}

	private MergeCell processHtmlTableTitle(Document jsoupHtmlTable, int maxNumColumns){
		Elements cells = jsoupHtmlTable.select("caption");

		return new MergeCell(0, 0, maxNumColumns, 0, TableHelper.cleanCell(cells.get(0)));
	}

	private boolean isTitlePresent(Document jsoupHtmlTable){
		Elements cells = jsoupHtmlTable.select("caption");

		return !cells.isEmpty();
	}

	private boolean isHeaderPresent(Document jsoupHtmlTable){
		Elements cells = jsoupHtmlTable.select("th");
		return !cells.isEmpty();
	}

	private int calculateMaxTableColumns(Document jsoupHtmlTable){
		Elements rows = jsoupHtmlTable.getElementsByTag("tr");
		
		int maxSize = 0;

		for(Element row : rows){
			if( maxSize < row.getElementsByTag("td").size() ){
				maxSize = row.getElementsByTag("td").size();
			}
			
			if( maxSize < row.getElementsByTag("th").size() ){
				maxSize = row.getElementsByTag("th").size();
			}
		}

		return maxSize;
	}

	/**
	 * Including Headers
	 * @param rows	Array of Jsoup Elements
	 * @return num of rows
	 */
	private int calculateMaxTableRows(Document jsoupHtmlTable){
		Elements rows =  jsoupHtmlTable.getElementsByTag("tr");
		return rows.isEmpty() ? 0 : rows.size();
	}

	// GETTERs && SETTERs
	public int getMaxNumRows() {
		return maxNumRows;
	}

	public void setMaxNumRows(int maxNumRows) {
		this.maxNumRows = maxNumRows;
	}

	public int getMaxNumColumns() {
		return maxNumColumns;
	}

	public void setMaxNumColumns(int maxNumColumns) {
		this.maxNumColumns = maxNumColumns;
	}

	public int getNumDataRows() {
		return numDataRows;
	}

	public void setNumDataRows(int numDataRows) {
		this.numDataRows = numDataRows;
	}

	public Document getJsoupHtmlTable() {
		return jsoupHtmlTable;
	}

	public void setJsoupHtmlTable(Document jsoupHtmlTable) {
		this.jsoupHtmlTable = jsoupHtmlTable;
	}

	public boolean isHasHeader() {
		return hasHeader;
	}

	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	public boolean isHasTitle() {
		return hasTitle;
	}

	public void setHasTitle(boolean hasTitle) {
		this.hasTitle = hasTitle;
	}

	public boolean isHasTotalRow() {
		return hasTotalRow;
	}

	public void setHasTotalRow(boolean hasTotalRow) {
		this.hasTotalRow = hasTotalRow;
	}

	public boolean isHasTotalColumn() {
		return hasTotalColumn;
	}

	public void setHasTotalColumn(boolean hasTotalColumn) {
		this.hasTotalColumn = hasTotalColumn;
	}

	public Map<Integer, List<HtmlCell>> getTableMap() {
		return tableMap;
	}

	public void setTableMap(Map<Integer, List<HtmlCell>> tableMap) {
		this.tableMap = tableMap;
	}

	public MergeCell getTitle() {
		return title;
	}

	public void setTitle(MergeCell title) {
		this.title = title;
	}
}
