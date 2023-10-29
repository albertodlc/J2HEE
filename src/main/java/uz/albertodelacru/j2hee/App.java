package uz.albertodelacru.j2hee;

import uz.albertodelacru.j2hee.file.helper.FileHelper;
import uz.albertodelacru.j2hee.table.html.TableHtml;
import uz.albertodelacru.j2hee.workbook.manager.WorkbookManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // String totalGarantias = FileHelper.readExcelFileFromLocal( "/totalGarantias.html" );
        // String repGarNo = FileHelper.readExcelFileFromLocal( "/repGar.html" );
        // String totalEqAv = FileHelper.readExcelFileFromLocal( "/totalEquipoAv.html" );
        // String totalFabEnvioTipEquipo = FileHelper.readExcelFileFromLocal( "/totalFabEnvioTipEquipo.html" );

        // TableHtml htmlTableTotalFabEnvioTipEquipo = new TableHtml(totalFabEnvioTipEquipo, true, true, false, true);
        // TableHtml htmlTableTotalEqAv = new TableHtml(totalEqAv, true, true, true, true);
        // TableHtml htmlTableTotalGarantias = new TableHtml(totalGarantias, true, true, true, true);
        // TableHtml htmlTableRepGarNo = new TableHtml(repGarNo, true, true, true, true);

        // WorkbookManager wb = new WorkbookManager(htmlTableTotalGarantias, "Total Gar.");
        // wb.addSheetToWorkbook(htmlTableRepGarNo, "Rep. Gar. NO");
        // wb.addSheetToWorkbook(htmlTableTotalEqAv, "Total Eq. Av.");
        // wb.addSheetToWorkbook(htmlTableTotalFabEnvioTipEquipo, "Total Fab. Envio");

        // wb.saveWorkbookToLocal("C:\\Users\\Alberto\\Desktop\\Projects\\Test.xls");
    }
}
