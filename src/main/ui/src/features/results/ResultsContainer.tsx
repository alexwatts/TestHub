import React from 'react';
import {connect} from 'react-redux';
import {selectResults} from "./resultSlice";
import {RootState} from "../../app/store";
import {Results} from "./Result";

const mapStateToProps = function(state: RootState) {
  return {
    results: selectResults(state)
  }
}

export default connect(mapStateToProps)(Results);