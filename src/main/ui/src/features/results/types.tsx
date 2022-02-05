export interface DisplayResult {
    rows: RowData[];
}

export interface RowData {
    name: string;
    columns: ColumnData[];
}

export interface ColumnData {
    key: string;
    display: string;
    image?: ImageData;
}

export interface ImageData {
    name: string;
    mimeType: string;
    data: string;
}

export interface RowProps {
    name: string;
    columns: ColumnData[];
}

export interface ColumnProps {
    columnId: number;
    display: string;
    image?: ImageData;
}

export interface HoverOverImageIconProps {
    image?: ImageData
}