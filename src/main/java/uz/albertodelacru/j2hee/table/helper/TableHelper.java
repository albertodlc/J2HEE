package uz.albertodelacru.j2hee.table.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import uz.albertodelacru.j2hee.table.cell.HtmlCell;
import uz.albertodelacru.j2hee.table.cell.MergeCell;

public class TableHelper {
    private TableHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<Integer, List<HtmlCell>> initializeTableMap(int maxNumRows, int maxNumColumns){
        Map<Integer, List<HtmlCell>> tableMap = new LinkedHashMap<>();
        for( int x = 0; x < maxNumRows; x++ ){
            tableMap.put(x, new ArrayList<>());

            for( int y = 0; y < maxNumColumns; y++ ){
                tableMap.get(x).add(new HtmlCell(x, y));
            }
        }

        return tableMap;
    }

    public static void updateTableCellsMapWithMergeAndValueInfo(Map<Integer, List<HtmlCell>> tableMap, Document jsoupHtmlTable, int maxNumColumns){
        Elements rows = jsoupHtmlTable.getElementsByTag("tr");
        
        for( int x = 0; x < rows.size(); x++ ){
            Elements columns = rows.get(x).select("th,td");
            int initialPosition = 0;
            for( int y = 0; y < columns.size(); y++ ){
                // Initialize cell value (if it is merged, we must find next valid cell on the row)
                initialPosition = findNextNotMergedPositionOnRow(x, initialPosition, maxNumColumns, tableMap);
                
                // Cell has attribute rowSpan
                if( columns.get(y).hasAttr("rowspan") ){
					String rowNumAttr = columns.get(y).attr("rowspan");
                    int rowSpan = Integer.parseInt(rowNumAttr);
                    
                    // Update current cell
                    tableMap.get(x).set(initialPosition, new MergeCell(x, initialPosition, rowSpan, 0, true, true, cleanCell( columns.get(y) ) ) );
                    
                    // Update extra cells, where current one is expanding (NO value, they are empty)
                    // Start on 1, because the 0 is updates previously with the value
                    for(int z = 1; z < rowSpan; z++){
                        tableMap.get(x + z).set(initialPosition, new MergeCell(x + z, initialPosition, 0, 0, true, true));
                    }
				}
                //Cell has attribute colSpan
                else if(columns.get(y).hasAttr("colspan")){
					String colNumAttr = columns.get(y).attr("colspan");
                    int colSpan = Integer.parseInt(colNumAttr);                    

                    // Update current cell
                    tableMap.get(x).set(initialPosition, new MergeCell(x, initialPosition, 0, colSpan, true, true, cleanCell( columns.get(y) ) ) );


                    // Update extra cell where current is expanding (NO value, is empty)
                    for(int z = 1; z < colSpan; z++){
                        tableMap.get(x).set(initialPosition + z, new MergeCell(x, initialPosition + z, 0, 0, true, true));
                    }
                }
                // Normal cell without colspan OR rowspan
                else{
                    tableMap.get(x).get(initialPosition).setValue( cleanCell( columns.get(y) ) );
                    tableMap.get(x).get(initialPosition).setBlocked(true);
                }
                
            }
        }
    }

    /**
     * Handle the pointer of the TableMap Row
     * @param currentRow
     * @param initialPos
     * @param rowSize
     * @param tableMap
     * @return
     */
    private static int findNextNotMergedPositionOnRow(int currentRow, int initialPos, int rowSize, Map<Integer, List<HtmlCell>> tableMap){
        for(int i = initialPos; i < rowSize; i++ ){
            // NO merged and NO blocked (Updated)
            if( !tableMap.get(currentRow).get(i).isMerged() && !tableMap.get(currentRow).get(i).isBlocked() ){
                return i; 
            }
        }

        return -1;
    }

    public static void displayTableDebug(Map<Integer, List<HtmlCell>> tableMap){
        for(int x = 0; x < tableMap.keySet().size(); x++){
            for(int y = 0; y < tableMap.get(x).size(); y++){
                if( tableMap.get(x).get(y).getValue().length() == 0 ){
                    System.out.print("X");
                }else{
                    System.out.print(limitHeaderSize(tableMap.get(x).get(y).getValue()));
                }
                
                System.out.print("  ");
            }

            System.out.println();
        }
    }

    private static String limitHeaderSize(String value){
        if( value.length() > 100){
            return value.substring(0, 1);
        }

        return value;
    }  

    /**
	 * Limpia y normaliza los valores que puede contener una celda de una tabla HTML:
	 * - <span>
	 * - <a>
	 * @param rawCell
	 * @return
	 */
	public static String cleanCell(Element rawCell){
		String cleanedData = "";

		if( !rawCell.children().isEmpty() ){
			// TODO: Ampliar con posible valores que vengan como child de un <td>
			if( rawCell.html().contains("</a>") ){
				cleanedData = rawCell.getElementsByTag("a").html();
			}else if( rawCell.html().contains("</span>") ){
				cleanedData = rawCell.getElementsByTag("span").html();
			}else if( rawCell.html().contains("</div>") ){
				cleanedData = rawCell.getElementsByTag("div").html();
			}
		}else{
			cleanedData = rawCell.html();
		}

		return cleanedData;
	}
}
