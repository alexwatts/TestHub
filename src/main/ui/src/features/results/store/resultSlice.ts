import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { RootState } from '../../../app/store';
import { fetchResults } from '../resultAPI';
import {DisplayResult} from "../types";

export interface ResultsState {
  value: DisplayResult;
  status: 'idle' | 'loading' | 'failed';
}

const initialState: ResultsState = {
  value:  { rows:[ { name: "header", columns: []}] },
  status: 'idle',
};

export const fetchAsync = createAsyncThunk(
  'results/fetchResults',
  async () => {
    const response = await fetchResults();
    return response;
  }
);

export const resultsSlice = createSlice({
  name: 'results',
  initialState,
  reducers: {
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchAsync.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchAsync.fulfilled, (state, action) => {
        state.status = 'idle';
        state.value = action.payload;
      });
  },
});

export const selectResults = (state: RootState) => state.displayResult.value;

export default resultsSlice.reducer;