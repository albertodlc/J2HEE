package uz.albertodelacru.jhxtable;


import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import uz.albertodelacru.jhxtable.file.helper.FileHelper;
import uz.albertodelacru.jhxtable.http.helper.HttpHelper;
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

    /**
     * Rigorous Test :-)
     */
    @Test
    public void loadAndConvertHtmlTable()
    {
        // WHEN
        TableHtml table = new TableHtml( new HttpHelper("http://localhost:8080/wstg/garantias/obtenerIndicadoresTotalesGarantia?paramsFiltro.tipoCt=GARANT") , true, true, true, true);
        // TEST

        // THEN
    }
}
