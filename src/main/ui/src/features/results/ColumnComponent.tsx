import {Column} from "./resultAPI";

interface ColumnProps extends React.Props<any>{
  column: Column
}

export function ColumnComponent(props: ColumnProps) {
  return (
    <td>
      {props.column.display}
    </td>
  );
}
