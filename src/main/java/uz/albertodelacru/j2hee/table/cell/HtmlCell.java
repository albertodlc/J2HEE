package uz.albertodelacru.j2hee.table.cell;

public class HtmlCell {
    protected String value = "";
    protected Integer rowNum = null;
    protected Integer colNum = null;
    private boolean isMerged = false;
    private boolean isBlocked = false;

    public HtmlCell( Integer rowNum, Integer colNum ){
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    public HtmlCell( Integer rowNum, Integer colNum, String value ){
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.value = value;
    }

    public HtmlCell( Integer rowNum, Integer colNum, boolean isMerged, boolean isBlocked ){
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.isMerged = isMerged;
        this.isBlocked = isBlocked;
    }

    public HtmlCell( Integer rowNum, Integer colNum, boolean isMerged, boolean isBlocked, String value ){
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.isMerged = isMerged;
        this.value = value;
        this.isBlocked = isBlocked;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getColNum() {
        return colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    public boolean isMerged() {
        return isMerged;
    }

    public void setMerged(boolean isMerged) {
        this.isMerged = isMerged;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }
}
