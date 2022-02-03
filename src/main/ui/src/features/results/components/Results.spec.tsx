import React from 'react';
import { Provider } from 'react-redux';
import {render, screen} from '@testing-library/react'
import ResultsContainer from './ResultsContainer';
import createMockStore from 'redux-mock-store';

const mockStore = createMockStore([]);

describe('Results Component test', () => {
    let store;

    beforeEach(() => {
        const headerColumns = [
            {display: "2022-02-04T09:21:51.12695495"},
            {display: "2022-02-03T10:14:15.86645368"},
            {display: "2022-02-02T14:07:12.57943864"}
        ]
        const testColumns = [
            {display: "passed"}, {display: "failed"}, {display: "empty"}
        ]
        store = mockStore({
            displayResult: {
                value: {
                    rows:[
                        { name: "header", columns: headerColumns},
                        { name: "aTest", columns: testColumns}
                    ]
                }
            },
            status: 'idle'
        });

        store.dispatch = jest.fn()

        render(
            <Provider store={store}>
                <ResultsContainer />
            </Provider>
        );
    });

    it('should render an header row with all test runs', async() => {
        expect(await screen.getByTestId('test-run-0')).toHaveTextContent('04/02/2022 09:21')
        expect(await screen.getByTestId('test-run-1')).toHaveTextContent('03/02/2022 10:14')
        expect(await screen.getByTestId('test-run-2')).toHaveTextContent('02/02/2022 14:07')
    });

    it('should render a test row with the pass style', async() => {
        const myElement = await screen.getByTestId('test-result-0');
        expect(myElement).toHaveProperty('className','columnPassed')
    });

    it('should render a test row with the fail style', async() => {
        const myElement = await screen.getByTestId('test-result-1');
        expect(myElement).toHaveProperty('className','columnFailed')
    });

    it('should render a test row with the empty style', async() => {
        const myElement = await screen.getByTestId('test-result-2');
        expect(myElement).toHaveProperty('className','columnEmpty')
    });

});