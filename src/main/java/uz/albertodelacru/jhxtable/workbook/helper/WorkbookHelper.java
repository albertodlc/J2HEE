package uz.albertodelacru.jhxtable.workbook.helper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import uz.albertodelacru.jhxtable.table.html.TableHtml;

public class WorkbookHelper {
    /**
     * Check if the Columns of the row, has the maximum size (the header size)
     * @param columnsData Array of elements to check
     * @param headerSize maximum number of elements
     * @return true: if equals, false: if less than
     */
    public static boolean isColumnDataSizeEqual(Elements columnsData, int headerSize){
		return (columnsData.size() == headerSize);
	}

    public static void addExtraValuesToMergeRow(Elements htmlColumnsData, TableHtml table){
        if( !WorkbookHelper.isColumnDataSizeEqual(htmlColumnsData, table.getMaxNumColumns()) ){
            int difference = table.getMaxNumColumns() - htmlColumnsData.size();
            // Add extra empty values to complete the Array of Column elements
            for(int t = 0; t < difference; t++ ){
                htmlColumnsData.add(0, new Element("td") );
            }
        }
    }

    public static void addExtraValuesToMergeColumns(Elements htmlColumnsData, TableHtml table){
        if( !WorkbookHelper.isColumnDataSizeEqual(htmlColumnsData, table.getMaxNumColumns()) ){
            int difference = table.getMaxNumColumns() - htmlColumnsData.size();
            // Add extra empty values to complete the Array of Column elements
            for(int t = 0; t < difference; t++ ){
                htmlColumnsData.add(1, new Element("td") );
            }
        }
    }
}