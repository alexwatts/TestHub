import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { RootState, AppThunk } from '../../app/store';
import DisplayResult, { fetchResults } from './resultAPI';

export interface ResultsState {
  value: DisplayResult;
  status: 'idle' | 'loading' | 'failed';
}

const initialState: ResultsState = {
  value:  { rows:[] },
  status: 'idle',
};

export const fetchAsync = createAsyncThunk(
  'results/fetchResults',
  async () => {
    const response = await fetchResults();
    return response.displayResult;
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