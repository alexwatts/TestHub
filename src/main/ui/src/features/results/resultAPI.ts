export type DisplayResult = {
  rows: Row[];
};

export type Row = {
  name: string;
  columns: Column[];
}

export type Column = {
  display: string;
  key: string
};

export function fetchResults() {
  return fetch('reports')
      // the JSON body is taken from the response
      .then(res => res.json())
      .then(res => {
        // The response has an `any` type, so we need to cast
        // it to the `User` type, and return it from the promise
        return res as DisplayResult
      });
}

export default DisplayResult
