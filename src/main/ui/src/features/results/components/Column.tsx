import React from "react";
import classNames from "classnames";
import styles from '../Result.module.css'
import {ColumnProps} from "../types";
import {HoverOverImageIcon} from "./HoverOverImageIcon";

export function Column(props: ColumnProps) {
    const failed = props.display === 'failed'
    const passed = props.display === 'passed'
    const empty = props.display === 'empty'

    return (
        <td data-testid={'test-result-' + props.columnId}
            className={
                classNames(
                    failed && styles.columnFailed,
                    passed && styles.columnPassed,
                    empty && styles.columnEmpty
                )
            }>
            <HoverOverImageIcon image={props.image}/>
        </td>
    );
}
