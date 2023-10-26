package uz.albertodelacru.jhxtable.table.cell;

public class MergeCell extends HtmlCell {
    private Integer rowSpan = null;
    private Integer colSpan = null;

    public MergeCell( Integer rowNum, Integer colNum, Integer rowSpan, Integer colSpan ){
        super(rowNum, colNum);

        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
    }

    public MergeCell( Integer rowNum, Integer colNum, Integer rowSpan, Integer colSpan, String value ){
        super(rowNum, colNum, value);

        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
    }

    public MergeCell( Integer rowNum, Integer colNum, Integer rowSpan, Integer colSpan, boolean isMerged, boolean isBlocked){
        super(rowNum, colNum, isMerged, isBlocked);

        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
    }

    public MergeCell( Integer rowNum, Integer colNum, Integer rowSpan, Integer colSpan, boolean isMerged, boolean isBlocked, String value ){
        super(rowNum, colNum, isMerged, isBlocked, value);

        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
    }

    public Integer getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }

    public Integer getColSpan() {
        return colSpan;
    }

    public void setColSpan(Integer colSpan) {
        this.colSpan = colSpan;
    }

    
}
