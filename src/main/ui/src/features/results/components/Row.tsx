import React from "react";
import {Column} from "./Column";
import {ColumnData} from "../types";

interface RowProps {
  name: string
  header: boolean
  columns: ColumnData[]
}

export function Row(props: RowProps) {

    const column = function (column: ColumnData, idx: number) {
        return (
            <Column
                key={idx}
                header={props.header}
                display={column.display}/>
        )
    }

    return (
        <tr>
          <td>{props.name}</td>
          {props.columns.map(column)}
        </tr>
    );

}