import React from 'react';
import { Provider } from 'react-redux';
import {render, screen} from '@testing-library/react'
import ResultsContainer from './ResultsContainer';
import createMockStore from 'redux-mock-store';

const mockStore = createMockStore([]);

describe('My Connected React-Redux Component', () => {
    let store;

    beforeEach(() => {
        store = mockStore({
            displayResult: {
                value: {
                    rows:[
                        { name: "header", columns: [{display: "2022-02-04T09:21:51.18508214"}, {display: "2022-02-03T09:21:51.18508214"}, , {display: "2022-02-02T14:07:12.18508214"}]},
                        { name: "aTest", columns: [{display: "passed"}, {display: "failed"}, {display: "empty"}]}
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
        const myElement = await screen.getByTestId('header-row');
        expect(myElement).toHaveTextContent('04/02/2022 09:21')
        expect(myElement).toHaveTextContent('03/02/2022 09:21')
        expect(myElement).toHaveTextContent('02/02/2022 14:07')
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