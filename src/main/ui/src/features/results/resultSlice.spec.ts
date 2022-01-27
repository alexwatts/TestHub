import resultsReducer, {
  ResultsState,
} from './resultSlice';

describe('result reducer', () => {
  const initialState: ResultsState = {
    value: { rows: [] },
    status: 'idle',
  };
  it('should handle initial state', () => {
    expect(resultsReducer(undefined, { type: 'unknown' })).toEqual({
      value: { rows: [] },
      status: 'idle',
    });
  });
});
