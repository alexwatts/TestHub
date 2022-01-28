export interface DisplayResult {
    rows: RowData[];
}

export interface RowData {
    name: string;
    columns: ColumnData[];
}

export interface ColumnData {
    display: string;
    key: string
}