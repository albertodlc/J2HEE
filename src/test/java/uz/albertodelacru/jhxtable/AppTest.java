package uz.albertodelacru.jhxtable;


import static org.junit.Assert.*;

import org.junit.Test;

import uz.albertodelacru.jhxtable.file.helper.FileHelper;
import uz.albertodelacru.jhxtable.table.html.TableHtml;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void loadAndConvertHtmlTable()
    {
        // WHEN
        String rawHtmlTable = FileHelper.readExcelFileFromLocal( "/tableExample.html" );

        // TEST
        TableHtml htmlTable = new TableHtml(rawHtmlTable, true, true, true, true);
        
        // THEN
        assertEquals("Se tiene que devolver una tabla con 8 columnas", 8, htmlTable.getMaxNumColumns());
        assertEquals("Se tiene que devolver una tabla con un Total (incluyendo Título, Cabecera y Total) de 17 filas", 17, htmlTable.getMaxNumRows());
        assertEquals("Se tiene que devolver una tabla con un Parcial (sin incluir Título, Cabecera y Total) de 15 filas", 14, htmlTable.getNumDataRows());

        assertEquals("El título generado tiene que ser 'TOTAL EQUIPOS AVERIADOS' ", "TOTAL EQUIPOS AVERIADOS", htmlTable.getTitleText());
    }
}
