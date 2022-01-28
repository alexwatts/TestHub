import React, {useEffect} from 'react';

import { useAppDispatch } from '../../../app/hooks';
import {
    fetchAsync,
} from '../store/resultSlice';
import {Row} from "./Row";
import { DisplayResult } from "../types";

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
            {props.results.rows
                .map(function(row, idx) {
                    return (
                        <Row
                            key={idx}
                            name={row.name}
                            columns={row.columns}/>
                    )
                })
            }
            </tbody>
        </table>
    </div>
  );

}
