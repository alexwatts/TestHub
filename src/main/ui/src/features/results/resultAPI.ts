type DisplayResult = {
  rows: Row[];
};

type Row = {
  name: string;
  columns: Column[];
}

type Column = {
  display: string;
};


// A mock function to mimic making an async request for data
export function fetchResults() {
  return new Promise<{ displayResult: DisplayResult }>((resolve) =>
    setTimeout(() => resolve({
      displayResult: {
        rows: [{
          name: "header",
          columns: [{display: "15-33-54"}, {display: "16-33-54"}]
        },
        {
          name: "test1",
          columns: [{display: "success"}, {display: "failed"}]
        },
        {
          name: "test2",
          columns: [{display: "failed"}, {display: "success"}]
        }
      ]
    }
    }), 500)
  );
}

export default DisplayResult
