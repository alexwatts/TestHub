import React, {useEffect, useState} from 'react';

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
              {results.rows.map(function(row) {
                  return (<tr>
                      <td>{row.name}</td>
                      {row.columns.map(function(column){
                          return (<td>{column.display}</td>)
                      })}
                  </tr>)
              })}
            </table>



        </span>
      <div className={styles.row}>
      </div>
      <div className={styles.row}>
      </div>
    </div>
  );
}
