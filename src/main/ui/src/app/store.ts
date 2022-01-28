import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import resultsReducer from '../features/results/store/resultSlice';

export const store = configureStore({
  reducer: {
    displayResult: resultsReducer,
  },
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
