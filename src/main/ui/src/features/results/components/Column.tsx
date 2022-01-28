import React from "react";
import classNames from "classnames";
import styles from '../Result.module.css'

interface ColumnProps {
  display: string
}

export function Column(props: ColumnProps) {

  const failed = props.display === 'failed'
  const passed = props.display === 'passed'
  const empty = props.display === 'empty'

  return (
    <td className={
      classNames(
          failed && styles.columnFailed,
          passed && styles.columnPassed,
          empty && styles.columnEmpty
          )
    }>
      {props.display}
    </td>
  );
}
