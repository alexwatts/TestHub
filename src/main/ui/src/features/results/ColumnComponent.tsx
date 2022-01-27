import React from "react";

interface ColumnProps {
  display: string
  index: number
}

export function ColumnComponent(props: ColumnProps) {
  return (
    <td key={props.index}>
      {props.display}
    </td>
  );
}
