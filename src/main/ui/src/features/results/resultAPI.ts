import {DisplayResult} from "./types";

export function fetchResults() {
  return fetch('reports')
      .then(res => res.json())
      .then(res => {
        return res as DisplayResult
      });
}
