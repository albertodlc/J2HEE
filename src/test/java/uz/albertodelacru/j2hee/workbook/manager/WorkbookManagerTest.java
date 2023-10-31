package uz.albertodelacru.j2hee.workbook.manager;

import uz.albertodelacru.j2hee.file.helper.FileHelper;
import uz.albertodelacru.j2hee.table.helper.TableHelper;
import uz.albertodelacru.j2hee.table.html.TableHtml;
import uz.albertodelacru.j2hee.workbook.manager.WorkbookManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import uz.albertodelacru.j2hee.table.html.TableHtml;

public class WorkbookManagerTest {

    private String tempPath = null;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp(){
        tempPath = folder.getRoot().getAbsolutePath();
    }

    @Test
    public void createWorkbookWithMultipleWorksheets() {
        // WHEN
        String filename = "Test";
        String fullPath = tempPath + "\\" + filename + ".xls";

        TableHtml tableSheet = new TableHtml( FileHelper.readHtmlFileFromLocal("/simpleTable.html"), false, false);
        
        WorkbookManager wb = new WorkbookManager();
        wb.addSheetToWorkbook(tableSheet, "Sheet One");
        wb.addSheetToWorkbook(tableSheet, "Sheet Two");
        wb.addSheetToWorkbook(tableSheet, "Sheet Three");
        wb.addSheetToWorkbook(tableSheet, "Kpi 1");
        wb.addSheetToWorkbook(tableSheet, "Kpi 2");
        wb.addSheetToWorkbook(tableSheet, "kpi 3");
        wb.addSheetToWorkbook(tableSheet, "kpi 4");
        wb.addSheetToWorkbook(tableSheet, "kpi 5");

        // TEST
        wb.saveWorkbookToLocal( tempPath, filename);

        // THEN
        assertTrue("File must exist", FileHelper.existFile( fullPath ));
        
        HSSFWorkbook createdWb = FileHelper.readExcelFileFromLocal( fullPath );
        assertNotNull("Workbook is not null", createdWb );
        
        HSSFSheet sheet1 = createdWb.getSheetAt(0);
        HSSFSheet sheet8 = createdWb.getSheetAt(7);

        assertNotNull("Worksheet is not null", sheet1 );
        assertEquals("Sheet name must be 'Sheet One'", "Sheet One", sheet1.getSheetName() );
        assertEquals("Sheet name must be 'kpi 5'", "kpi 5", sheet8.getSheetName() );
    }
}
