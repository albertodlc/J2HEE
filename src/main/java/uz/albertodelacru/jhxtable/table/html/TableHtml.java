package uz.albertodelacru.jhxtable.table.html;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import uz.albertodelacru.jhxtable.http.helper.HttpHelper;

public class TableHtml {
	private int maxNumRows = 0;
	private int maxNumColumns = 0;
	private int numDataRows = 0;

	// Table section checks
	private boolean hasHeader = false;
	private boolean hasTitle = false;
	private boolean hasTotalRow = false;
	private boolean hasTotalColumn = false;

	// Raw and processed Tables
	private String rawHtmlTable = null;
	private Document jsoupHtmlTable = null;
	// All ROWS
	private Elements htmlTableRows = null;

	// Title
	private Element title = null;
	private Element headerRow = null;
	private Elements dataRows = null;
	private Element footerRow = null;

	public TableHtml(String rawHtmlTable, boolean hasTitle, boolean hasHeader, boolean hasTotalRow, boolean hasTotalColumn){
		this.hasTitle = hasTitle;
		this.hasHeader = hasHeader;
		this.hasTotalRow = hasTotalRow;
		this.hasTotalColumn = hasTotalColumn;

		this.rawHtmlTable = rawHtmlTable;
		this.jsoupHtmlTable = Jsoup.parse(rawHtmlTable);
		this.jsoupHtmlTable.charset( StandardCharsets.UTF_8 );

		this.htmlTableRows =  processJsoupDocument(jsoupHtmlTable, hasTitle);

		this.title = processHtmlTableTitle(this.htmlTableRows, hasTitle);
		this.headerRow = processHtmlTableHeader(this.htmlTableRows, hasTitle, hasHeader);

		this.maxNumRows = calculateMaxTableRows(this.htmlTableRows);
		this.numDataRows = calculateNumDataTableRows(this.htmlTableRows, hasTitle, hasHeader, hasTotalRow);
		this.maxNumColumns = calculateMaxTableColumns(this.headerRow);

		this.footerRow = processHtmlTableFooter(this.htmlTableRows, hasTotalRow);
		this.dataRows = processHtmlTableData(this.htmlTableRows, hasHeader, hasTotalRow, hasTitle);
	}

	public TableHtml(HttpHelper httpHelper, boolean hasTitle, boolean hasHeader, boolean hasTotalRow, boolean hasTotalColumn){
		this.hasTitle = hasTitle;
		this.hasHeader = hasHeader;
		this.hasTotalRow = hasTotalRow;
		this.hasTotalColumn = hasTotalColumn;

		this.rawHtmlTable = httpHelper.makeHttpRequest();
		this.jsoupHtmlTable = Jsoup.parse(rawHtmlTable);
		this.jsoupHtmlTable.charset( StandardCharsets.UTF_8 );

		this.htmlTableRows =  processJsoupDocument(jsoupHtmlTable, hasTitle);

		this.title = processHtmlTableTitle(this.htmlTableRows, hasTitle);
		this.headerRow = processHtmlTableHeader(this.htmlTableRows, hasTitle, hasHeader);

		this.maxNumRows = calculateMaxTableRows(this.htmlTableRows);
		this.numDataRows = calculateNumDataTableRows(this.htmlTableRows, hasTitle, hasHeader, hasTotalRow);
		this.maxNumColumns = calculateMaxTableColumns(this.headerRow);

		this.footerRow = processHtmlTableFooter(this.htmlTableRows, hasTotalRow);
		this.dataRows = processHtmlTableData(this.htmlTableRows, hasHeader, hasTotalRow, hasTitle);
	}

	private Element processHtmlTableTitle(Elements rows, boolean hasTitle){
		// Is empty
		if( rows.isEmpty() ){
			return null;
		}

		// Does not has Title
		if( !hasTitle ){
			return null;
		}

		return rows.get(0);
	}

	private Elements processJsoupDocument(Document jsoupHtmlTable, boolean hasTitle){
		Elements htmlTable = jsoupHtmlTable.select("tr");

		if( hasTitle ){
			Elements htmlTableTitle = jsoupHtmlTable.select("caption");

			if( !htmlTableTitle.isEmpty() ){
				htmlTable.add(0, htmlTableTitle.get(0));
			}
		}

		return htmlTable;
	}

	private Element isHeaderPresent(Element row){
		Elements headersCells = row.select("th");

		if( headersCells.isEmpty() ){
			hasHeader = false;

			return null;
		}

		return row;
	}

	private Element processHtmlTableHeader(Elements rows, boolean hasTitle, boolean hasHeader){
		// Is empty
		if( rows.isEmpty() ){
			return null;
		}

		// Does not has header
		if( !hasHeader ){
			return null;
		}

		// Get header (after title)
		if( hasTitle ){
			return isHeaderPresent(rows.get(1));
		}

		// Get header (no title)
		return isHeaderPresent(rows.get(0));
	}

	private Element processHtmlTableFooter(Elements rows, boolean hasTotalRow){
		if( !hasTotalRow ){
			return null;
		}

		return rows.get( maxNumRows - 1 );
	}

	private Elements processHtmlTableData(Elements rows,  boolean hasHeader, boolean hasTotalRow, boolean hasTitle){
		List<Element> elements = new ArrayList<>();
		
		int fromRow = 0;
		int toRow = maxNumRows;
		
		if( hasTitle ){
			fromRow++;
		}

		if( hasHeader ){
			fromRow++;
		}

		if( hasTotalRow ){
			toRow--;
		}

		for( ; fromRow < toRow; fromRow++ ){
			elements.add( rows.get( fromRow ) );
		}

		return new Elements(elements);
	}

	private int calculateMaxTableColumns(Element dataRow){
		return !dataRow.getElementsByTag("th").isEmpty() ? dataRow.getElementsByTag("th").size() : 0 ;
	}

	/**
	 * Only includes Rows from the data section (Not header or Title)
	 * @param rows	Array of Jsoup Elements
	 * @return num of rows
	 */
	private int calculateNumDataTableRows(Elements rows, boolean hasTitle, boolean hasHeader, boolean hasTotalRow ){
		int subtract = 0;

		if( hasTotalRow ){
			subtract++;
		}

		if( hasTitle ){
			subtract++;
		}

		if( hasHeader ){
			subtract++;
		}

		return rows.size() - subtract ;
	}

	/**
	 * Including Headers and Titles (if they exist)
	 * @param rows	Array of Jsoup Elements
	 * @return num of rows
	 */
	private int calculateMaxTableRows(Elements rows){
		return rows.size();
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

	public String getRawHtmlTable() {
		return rawHtmlTable;
	}

	public void setRawHtmlTable(String rawHtmlTable) {
		this.rawHtmlTable = rawHtmlTable;
	}

	public Document getJsoupHtmlTable() {
		return jsoupHtmlTable;
	}

	public void setJsoupHtmlTable(Document jsoupHtmlTable) {
		this.jsoupHtmlTable = jsoupHtmlTable;
	}

	public Elements getHtmlTableRows() {
		return htmlTableRows;
	}

	public void setHtmlTableRows(Elements htmlTableRows) {
		this.htmlTableRows = htmlTableRows;
	}

	public Element getTitle() {
		return title;
	}

	public String getTitleText(){
		if(title != null){
			return title.html();
		}

		return null;
	}

	public void setTitle(Element title) {
		this.title = title;
	}

	public Element getHeaderRow() {
		return headerRow;
	}

	public void setHeaderRow(Element headerRow) {
		this.headerRow = headerRow;
	}

	public Elements getDataRows() {
		return dataRows;
	}

	public void setDataRows(Elements dataRows) {
		this.dataRows = dataRows;
	}

	public Element getFooterRow() {
		return footerRow;
	}

	public void setFooterRow(Element footerRow) {
		this.footerRow = footerRow;
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

	
}
