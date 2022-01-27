import {Row} from "./resultAPI";
import React from "react";
import {ColumnComponent} from "./ColumnComponent";

interface RowProps extends React.Props<any>{
  row: Row
}

export function RowComponent(props: RowProps) {
  return (
    <tr key={props.row.name}>
      {props.row.name}
      {props.row.columns.map(function(column) {
        return (<ColumnComponent column={column}/>)
      })}
    </tr>
  );
}
