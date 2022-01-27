import React from "react";

interface ColumnProps {
  display: string
}

export function ColumnComponent(props: ColumnProps) {
  return (
    <td>{props.display}</td>
  );
}
