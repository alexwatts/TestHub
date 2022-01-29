import React, {useEffect} from 'react';

import { useAppDispatch } from '../../../app/hooks';
import {
    fetchAsync,
} from '../store/resultSlice';
import {Row} from "./Row";
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

    const row = function (row: RowData, idx: number) {
        return (
            <Row
                key={idx}
                name={row.name}
                header={idx === 0}
                columns={row.columns}/>
        )
    }

    return (
        <span className={styles.value}>
            <table>
                <tbody>
                    {props.results.rows.map(row)}
                </tbody>
            </table>
        </span>
    );
}