import React from "react";
import {Column} from "./Column";
import {ColumnData} from "../types";

interface RowProps {
  name: string
  columns: ColumnData[]
}

export function Row(props: RowProps) {

    const column = function (column: ColumnData, idx: number) {
        return (
            <Column
                key={idx}
                columnId={idx}
                display={column.display}
                image={column.image}/>
        )
    }

    return (
        <tr>
          <td>{props.name}</td>
          {props.columns.map(column)}
        </tr>
    );

}
