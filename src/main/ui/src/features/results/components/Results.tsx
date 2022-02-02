import React, {useEffect} from 'react';

import { useAppDispatch } from '../../../app/hooks';
import {
    fetchAsync,
} from '../store/resultSlice';
import {Row} from "./Row";
import {HeaderRow} from "./HeaderRow";
import {DisplayResult, RowData} from "../types";
import styles from '../Result.module.css'

interface ResultProps {
    results: DisplayResult;
}

export function Results(props: ResultProps) {
    const dispatch = useAppDispatch();

    useEffect(() => {
        dispatch(fetchAsync())
    }, [dispatch])

    const headerRow = function (row: RowData) {
        return (
            <HeaderRow
                key={0}
                name={row.name}
                columns={row.columns}/>
        )
    }

    const row = function (row: RowData, idx: number) {
        return (
            <Row
                key={idx}
                name={row.name}
                columns={row.columns}/>
        )
    }

    return (
        <span className={styles.value}>
            <table className={styles.styledtable}>
                <thead>
                    {headerRow(props.results.rows[0])}
                </thead>
                <tbody>
                    {props.results.rows.slice(1, props.results.rows.length).map(row)}
                </tbody>
            </table>
        </span>
    );

}