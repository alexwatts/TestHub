import React, {useEffect} from 'react';

import { useAppDispatch } from '../../app/hooks';
import {
    fetchAsync,
} from './resultSlice';
import DisplayResult from "./resultAPI";
import {RowComponent} from "./RowComponent";

interface ResultProps {
    results: DisplayResult;
}

export function Results(props: ResultProps) {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(fetchAsync())
  }, [dispatch])

  return (
    <div>
        <table>
            <tbody>
            {props.results.rows.map(function(row) {
                return (<RowComponent row={row}></RowComponent>)
            })}
            </tbody>
        </table>
    </div>
  );

}
