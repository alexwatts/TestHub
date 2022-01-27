import {Column} from "./resultAPI";
import React from "react";
import {ColumnComponent} from "./ColumnComponent";

interface RowProps {
  name: string
  columns: Column[]
  index: number
}

export function RowComponent(props: RowProps) {

  return (
    <tr key={props.index}>
      {props.name}
      {props.columns
          .map(function(column, idx) {
            return (
                <ColumnComponent
                    display={column.display}
                    index={idx}/>
            )
      })}
    </tr>
  );
}