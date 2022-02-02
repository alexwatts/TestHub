import React, {useEffect} from 'react';
import './App.css';
import ResultsContainer from "./features/results/components/ResultsContainer";

function App() {

    useEffect(() => {
        const splash = document.querySelector('.App-splash');
        setTimeout(() => {
            splash!.classList.add('display-none')
        }, 2000)
    }, [])


  return (
    <div className="App">
        <div className="App-splash">
            <h1 className="fade-in">TestHub</h1>
        </div>
        <header className="App-header">
        </header>
        <ResultsContainer/>
    </div>
  );
}

export default App;
