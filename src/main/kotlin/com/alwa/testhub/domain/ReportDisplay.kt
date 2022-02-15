package com.alwa.testhub.domain

data class ReportDisplay(val group: String, val rows: List<Row>)

data class Row(val name: String, val columns: List<Column>)

data class Column(val display: String, val image: Image?)

data class Image(val name: String, val mimeType: String, val data: String)
