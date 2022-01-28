
import React from "react";
import {Column} from "./Column";
import {ColumnData} from "../types";

interface RowProps {
  name: string
  columns: ColumnData[]
}

export function Row(props: RowProps) {
  return (
    <tr>
      <td>{props.name}</td>
      {props.columns
          .map(function(column, idx) {
            return (
                <Column
                        key={idx}
                        display={column.display}/>
            )
      })}
    </tr>
  );
}