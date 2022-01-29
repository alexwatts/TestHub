import React from "react";
import classNames from "classnames";
import styles from '../Result.module.css'

interface ColumnProps {
  header: boolean
  display: string
}

export function Column(props: ColumnProps) {
    const failed = props.display === 'failed'
    const passed = props.display === 'passed'
    const empty = props.display === 'empty'

    function displayHeader(display: string) {
        if (display === 'header') return ''
        const date = new Date(display);
        return date.getDate() + '/' + date.getMonth() + 1 + '/' + date.getFullYear() + ' '
            + date.getHours() + ':' + date.getMinutes();
    }

    if (props.header) {
        return (
            <td className={styles.headerValue}>
                {displayHeader(props.display)}
            </td>
        )
    }

    return (
        <td className={
            classNames(
                failed && styles.columnFailed,
                passed && styles.columnPassed,
                empty && styles.columnEmpty
            )}>
    </td>
  );
}
