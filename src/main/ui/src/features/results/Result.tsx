import React, {useEffect} from 'react';

import { useAppSelector, useAppDispatch } from '../../app/hooks';
import {
    fetchAsync,
    selectResults,
} from './resultSlice';
import styles from './Result.module.css';

export function Results() {
  const results = useAppSelector(selectResults);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(fetchAsync())
  }, [dispatch])

  return (
    <div>
        <span className={styles.value}>
            <table>
            <tbody>
              {results.rows.map(function(row, idx) {
                  return (<tr key={idx}>
                      <td key={idx}>{row.name}</td>
                      {row.columns.map(function(column, idx){
                          return (<td key={idx}>{column.display}</td>)
                      })}
                  </tr>)
              })}
              </tbody>
            </table>
        </span>
    </div>
  );
}
