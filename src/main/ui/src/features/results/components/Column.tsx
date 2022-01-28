import React from "react";

interface ColumnProps {
  display: string
}

export function Column(props: ColumnProps) {
  return (
    <td>{props.display}</td>
  );
}
