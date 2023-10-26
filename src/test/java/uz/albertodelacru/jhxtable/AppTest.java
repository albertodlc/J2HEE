package uz.albertodelacru.jhxtable;

import org.junit.Test;

import uz.albertodelacru.jhxtable.file.helper.FileHelper;
import uz.albertodelacru.jhxtable.table.helper.TableHelper;
import uz.albertodelacru.jhxtable.table.html.TableHtml;
import uz.albertodelacru.jhxtable.workbook.manager.WorkbookManager;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private static final String ENDPOINT_INDICADOR_TOTAL = "http://localhost:8080/wstg/garantias/obtenerIndicadoresTotalesGarantia";
	private static final String ENDPOINT_INDICADOR_SIN_REPUESTO = "http://localhost:8080/wstg/garantias/obtenerIndicadoresSinRepuestoEnGarantia?paramsFiltro.tipoCt=GARANT&paramsFiltro.MesGar1=10&paramsFiltro.AnioGar1=2023&paramsFiltro.fechaInicioText=2022-10-25&paramsFiltro.fechaFinText=2023-10-25&paramsFiltro.origen=TODO";
	private static final String ENDPOINT_INDICADOR_KPI_ONE = "http://localhost:8080/wstg/garantias/actIndicadoresGarantiaKpi1?paramsFiltro.tipoCt=GARANT&paramsFiltro.MesGar1=10&paramsFiltro.AnioGar1=2023&paramsFiltro.fechaInicioText=2022-10-25&paramsFiltro.fechaFinText=2023-10-25&paramsFiltro.origen=TODO";
	private static final String ENDPOINT_INDICADOR_KPI_TWO = "http://localhost:8080/wstg/garantias/actIndicadoresGarantiaKpi2?paramsFiltro.tipoCt=GARANT&paramsFiltro.MesGar1=10&paramsFiltro.AnioGar1=2023&paramsFiltro.fechaInicioText=2022-10-25&paramsFiltro.fechaFinText=2023-10-25&paramsFiltro.origen=TODO";
	private static final String ENDPOINT_INDICADOR_KPI_THREE = "http://localhost:8080/wstg/garantias/actIndicadoresGarantiaKpi3?paramsFiltro.tipoCt=GARANT&paramsFiltro.MesGar1=10&paramsFiltro.AnioGar1=2023&paramsFiltro.fechaInicioText=2022-10-25&paramsFiltro.fechaFinText=2023-10-25&paramsFiltro.origen=TODO";
	private static final String ENDPOINT_INDICADOR_KPI_FOUR = "http://localhost:8080/wstg/garantias/actIndicadoresGarantiaKpi4?paramsFiltro.tipoCt=GARANT&paramsFiltro.MesGar1=10&paramsFiltro.AnioGar1=2023&paramsFiltro.fechaInicioText=2022-10-25&paramsFiltro.fechaFinText=2023-10-25&paramsFiltro.origen=TODO";
	private static final String ENDPOINT_INDICADOR_KPI_FIVE = "http://localhost:8080/wstg/garantias/actIndicadoresGarantiaKpi5?paramsFiltro.tipoCt=GARANT&paramsFiltro.MesGar1=10&paramsFiltro.AnioGar1=2023&paramsFiltro.fechaInicioText=2022-10-25&paramsFiltro.fechaFinText=2023-10-25&paramsFiltro.origen=TODO";
	private static final String ENDPOINT_INDICADOR_KPI_SIX = "http://localhost:8080/wstg/garantias/actIndicadoresGarantiaKpi6?paramsFiltro.tipoCt=GARANT&paramsFiltro.MesGar1=10&paramsFiltro.AnioGar1=2023&paramsFiltro.fechaInicioText=2022-10-25&paramsFiltro.fechaFinText=2023-10-25&paramsFiltro.origen=TODO";
	private static final String ENDPOINT_INDICADOR_KPI_SEVEN = "http://localhost:8080/wstg/garantias/actIndicadoresGarantiaKpi7?paramsFiltro.tipoCt=GARANT&paramsFiltro.MesGar1=10&paramsFiltro.AnioGar1=2023&paramsFiltro.fechaInicioText=2022-10-25&paramsFiltro.fechaFinText=2023-10-25&paramsFiltro.origen=TODO";

    private final String path = "C:\\Users\\Alberto\\Desktop\\Projects";

    /**
     * Rigorous Test :-)
     */
    @Test
    public void loadAndConvertHtmlTable_LocalFileTotalEqAv()
    {
        // WHEN
        TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/totalEquipoAv.html"), true, true);
        WorkbookManager wb = new WorkbookManager(table, "Test");

        // TEST
        //TableHelper.displayTableDebug(table.getTableMap());
        wb.saveWorkbookToLocal(path, "totalEquipoAv");
        // THEN
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void loadAndConvertHtmlTable_LocalFileKPI1()
    {
        // WHEN
        TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/kpi1.html"), false, true);
        WorkbookManager wb = new WorkbookManager(table, "Test");

        // TEST
        //TableHelper.displayTableDebug(table.getTableMap());
        wb.saveWorkbookToLocal(path, "kpi1");
        // THEN
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void loadAndConvertHtmlTable_LocalFileKPI2()
    {
        // WHEN
        TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/kpi2.html"), false, true);
        WorkbookManager wb = new WorkbookManager(table, "Test");

        // TEST
        TableHelper.displayTableDebug(table.getTableMap());
        wb.saveWorkbookToLocal(path, "kpi2.xls");
        // THEN
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void loadAndConvertHtmlTable_LocalFileKPI3()
    {
        // WHEN
        TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/kpi3.html"), false, true);
        WorkbookManager wb = new WorkbookManager(table, "Test");

        // TEST
        TableHelper.displayTableDebug(table.getTableMap());
        wb.saveWorkbookToLocal(path, "kpi3.xls");
        // THEN
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void loadAndConvertHtmlTable_LocalFileKPI4()
    {
        // WHEN
        TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/kpi4.html"), false, true);
        WorkbookManager wb = new WorkbookManager(table, "Test");

        // TEST
        TableHelper.displayTableDebug(table.getTableMap());
        wb.saveWorkbookToLocal(path, "kpi4.xls");
        // THEN
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void loadAndConvertHtmlTable_LocalFileKPI5()
    {
        // WHEN
        TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/kpi5.html"), false, true);
        WorkbookManager wb = new WorkbookManager(table, "Test");

        // TEST
        TableHelper.displayTableDebug(table.getTableMap());
        wb.saveWorkbookToLocal(path, "kpi5.xls");
        // THEN
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void loadAndConvertHtmlTable_LocalFileKPI6()
    {
        // WHEN
        TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/kpi6.html"), false, true);
        WorkbookManager wb = new WorkbookManager(table, "Test");

        // TEST
        TableHelper.displayTableDebug(table.getTableMap());
        wb.saveWorkbookToLocal(path, "kpi6.xls");
        // THEN
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void loadAndConvertHtmlTable_LocalFileKPI7()
    {
        // WHEN
        TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/kpi7.html"), false, true);
        WorkbookManager wb = new WorkbookManager(table, "Test");

        // TEST
        TableHelper.displayTableDebug(table.getTableMap());
        wb.saveWorkbookToLocal(path, "kpi7.xls");
        // THEN
    }

    /**
     * Test local Empty file
     */
    @Test
    public void loadAndConvertHtmlTable_LocalFileEmpty()
    {
        // WHEN
        TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/emptyTable.html"), true, true);
        WorkbookManager wb = new WorkbookManager(table, "Test");

        // TEST
        //TableHelper.displayTableDebug(table.getTableMap());
        wb.saveWorkbookToLocal(path, "totalGarantias");
        // THEN

    }

    /**
     * Test local Empty file
     */
    @Test
    public void loadAndConvertHtmlTable_LocalFileTotalGarantias()
    {
        // WHEN
        TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/totalGarantias.html"), true, true);
        WorkbookManager wb = new WorkbookManager(table, "Test");
        
        // TEST
        //TableHelper.displayTableDebug(table.getTableMap());
        wb.saveWorkbookToLocal(path, "totalGarantias");

        // THEN
    }

    /**
     * Test local Empty file
     */
    @Test
    public void loadAndConvertHtmlTable_LocalFileTotalFabEnvio()
    {
        // WHEN
        TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/totalFabEnvioTipEquipo.html"), false, true);
        WorkbookManager wb = new WorkbookManager(table, "Test");

        // TEST
        //TableHelper.displayTableDebug(table.getTableMap());
        wb.saveWorkbookToLocal(path, "totalFabEnvioTipEquipo.xls");

        // THEN

    }
}
