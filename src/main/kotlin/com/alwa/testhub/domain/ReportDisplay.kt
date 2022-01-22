package com.alwa.testhub.domain

data class ReportDisplay(val rows: List<Row>)

data class Row(val name: String, val columns: List<Column>)

data class Column(val display: String)
