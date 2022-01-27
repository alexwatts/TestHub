import {Column} from "./resultAPI";
import React from "react";
import {ColumnComponent} from "./ColumnComponent";

interface RowProps {
  name: string
  columns: Column[]
}

export function RowComponent(props: RowProps) {
  return (
    <tr>
      <td>{props.name}</td>
      {props.columns
          .map(function(column, idx) {
            return (<ColumnComponent
                    key={idx}
                    display={column.display}/>)
      })}
    </tr>
  );
}