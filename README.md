# Java 2 Html Excel Export (J2HEE)
![Logo](img/logo.png)

*J2HEE* is a free open-source library to exporting HTML tables to excel most common formats. It provides easy and simple objects to handle:

- HTTP Requests URLs with HTML table
- HTML to Excel transformation
    - Including merge cells
- Simple Excel formatting

## POM Dependencies
```xml
<dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.apache.poi/poi -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi</artifactId>
        <version>3.9</version>
    </dependency>
	<!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
	<dependency>
		<groupId>org.jsoup</groupId>
		<artifactId>jsoup</artifactId>
		<version>1.16.2</version>
	</dependency>
</dependencies>
```

## Java examples
*HttpHelper*
Process a provided URL and clean the HTML tables, to return a ready-to-use String with the `TableHtml`

*TableHtml*
Handle the transformation of the HTML table, and provide method to access the table rows and columns. It is used to fetch the `WorkbookManager`.

As an input, it is possible to use 2 type of streams:
- The `raw` HTML table stream (as an String). You can use the `FileHelper` object as an easy-to-use interface to read and format .html files.
- The `HttpHelper` object, used to request and process the HTTP response. 

*WorkbookManager*
Receives an `TableHtml` and provides methods to return an Excel files.


```java
TableHtml table = new TableHtml( FileHelper.readExcelFileFromLocal("/kpi1.html"), false, true);

WorkbookManager wb = new WorkbookManager(table, "Test");
// Save the Excel to a .xls file
wb.saveWorkbookToLocal(path, "kpi1");

// Return the excel file as a Workbook
HSSFWorkbook hssfWorkbook = wb.getWb();

// Return the excel file as a Byte[]
byte[] byteArray = wb.getWbAsByteArray();
```