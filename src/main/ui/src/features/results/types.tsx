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

export interface RowProps {
    name: string
    columns: ColumnData[]
}