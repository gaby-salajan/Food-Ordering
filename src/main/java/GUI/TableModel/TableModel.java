package GUI.TableModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

abstract class TableModel<T> extends AbstractTableModel
{
    protected List<T> modelData;
    protected List<String> columnNames;
    protected Class[] columnClasses;
    protected Boolean[] isColumnEditable;
    private final Class rowClass = Object.class;

    private boolean isModelEditable = false;

    /**
     * Constructor for empty table only column names
     * @param columnNames column names to be set
     */
    protected TableModel(List<String> columnNames)
    {
        this(new ArrayList<>(), columnNames);
    }

    /**
     * Constructor for TableModel
     * @param modelData list of table rows data
     * @param columnNames list of headers
     */
    protected TableModel(List<T> modelData, List<String> columnNames)
    {
        setDataAndColumnNames(modelData, columnNames);
    }

    /**
     * Sets the table data and the headers
     * @param modelData table data to be set
     * @param columnNames headers to be set
     */
    protected void setDataAndColumnNames(List<T> modelData, List<String> columnNames) {
        this.modelData = modelData;
        this.columnNames = columnNames;
        columnClasses = new Class[getColumnCount()];
        isColumnEditable = new Boolean[getColumnCount()];
        fireTableStructureChanged();
    }

    /**
     * Returns the class of the given column
     * @param column column to get Class from
     * @return Class of given column
     */
    public Class getColumnClass(int column) {
        Class columnClass = null;

        if (column < columnClasses.length)
            columnClass = columnClasses[column];

        if (columnClass == null)
            columnClass = super.getColumnClass(column);

        return columnClass;
    }

    /**
     * Returns the number of columns
     * @return number of columns
     */
    public int getColumnCount()
    {
        return columnNames.size();
    }

    /**
     * Returns the given column's name
     * @param column column to get name from
     * @return column name
     */
    public String getColumnName(int column) {
        Object columnName = null;

        if (column < columnNames.size())
        {
            columnName = columnNames.get( column );
        }

        return (columnName == null) ? super.getColumnName( column ) : columnName.toString();
    }

    /**
     * Returns the number of rows
     * @return number of rows
     */
    public int getRowCount()
    {
        return modelData.size();
    }

    /**
     * Check if a given field from the table is editable
     * @param row field's row number
     * @param column field's column number
     * @return editable status
     */
    public boolean isCellEditable(int row, int column) {
        Boolean isEditable = null;

        if (column < isColumnEditable.length)
            isEditable = isColumnEditable[column];

        return (isEditable == null) ? isModelEditable : isEditable;
    }

    /**
     * Add a row to the table based on the given object
     * @param rowData object to be inserted as a row
     */
    public void addRow(T rowData) {
        insertRow(getRowCount(), rowData);
    }

    /**
     * Returns the given row as an object.
     * @param row row to get object from
     * @return row data as object
     */
    public T getRow(int row)
    {
        return modelData.get( row );
    }

    /**
     * Inserts a row based on the given object at the given position
     * @param row position where to insert
     * @param rowData object to be inserted as a row
     */
    public void insertRow(int row, T rowData) {
        modelData.add(row, rowData);
        fireTableRowsInserted(row, row);
    }

    /**
     * Removes the given row from the table
     * @param row position to be removed
     */
    public void removeRow(int row) {
        modelData.remove(row);
        fireTableRowsDeleted(row, row);
    }

    /**
     * Replaces the given row with the data from the given object
     * @param row position to be replaced
     * @param rowData object which replaces the given row
     */
    public void replaceRow(int row, T rowData) {
        modelData.set(row, rowData);
        fireTableRowsUpdated(row, row);
    }
}
