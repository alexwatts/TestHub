package com.alwa

import com.alwa.testhub.domain.Column
import com.alwa.testhub.domain.ReportDisplay
import com.alwa.testhub.domain.Row
import com.alwa.testhub.domain.TestResult
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object ObjectMother {

    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC))

    fun report() = """
        [
          {
            "line": 1,
            "elements": [
              {
                "start_timestamp": "2021-11-03T15:35:12.467Z",
                "line": 36,
                "name": "Test Example",
                "description": "",
                "id": "some-test-example",
                "after": [
                  {
                    "embeddings": [
                      {
                        "data": "guehnfdsaghl545423hbkj34lknb5hk34",
                        "mime_type": "application/json",
                        "name": "data"
                      }
                    ],
                    "result": {
                      "duration": 453768000,
                      "status": "passed"
                    },
                  }
                ],
                "type": "scenario",
                "keyword": "Scenario",
                "steps": [
                  {
                    "result": {
                      "duration": 26263603000,
                      "status": "passed"
                    },
                    "line": 37,
                    "name": "Do some activity",
                  },
                  {
                    "result": {
                      "duration": 3320000,
                      "status": "passed"
                    },
                    "line": 38,
                    "name": "Do some more activity"
                  }
                ]
              }
            ],
            "name": "TestFile",
            "description": "",
            "id": "someId",
            "keyword": "Feature",
            "uri": "file:src/test/resources/testExample/features/example.feature",
            "tags": []
          }
        ]
    """.trimIndent()

    fun failedReport() = """
                [
          {
            "line": 1,
            "elements": [
              {
                "start_timestamp": "2021-11-03T15:35:12.467Z",
                "line": 36,
                "name": "Test Example",
                "description": "",
                "id": "some-test-example",
                "after": [
                  {
                    "embeddings": [
                      {
                        "data": "guehnfdsaghl545423hbkj34lknb5hk34",
                        "mime_type": "application/json",
                        "name": "data"
                      }
                    ],
                    "result": {
                      "duration": 453768000,
                      "status": "passed"
                    },
                  }
                ],
                "type": "scenario",
                "keyword": "Scenario",
                "steps": [
                  {
                    "result": {
                      "duration": 26263603000,
                      "status": "passed"
                    },
                    "line": 37,
                    "name": "Do some activity",
                  },
                  {
                    "result": {
                      "duration": 3320000,
                      "status": "failed"
                    },
                    "line": 38,
                    "name": "Do some more activity"
                  }
                ]
              }
            ],
            "name": "TestFile",
            "description": "",
            "id": "someId",
            "keyword": "Feature",
            "uri": "file:src/test/resources/testExample/features/example.feature",
            "tags": []
          }
        ]
    """.trimIndent()

    fun threeTestsThreeRuns() : List<TestResult> {
        val runOneTiming = "2020-05-20T09:00:00Z"
        val runTwoTiming = "2020-05-20T09:16:00Z"
        val runThreeTiming = "2020-05-20T09:39:00Z"

        val test1 = "test1"
        val test2 = "test2"
        val test3 = "test3"

        return listOf(
            TestResult(test1, Instant.parse(runOneTiming), true),
            TestResult(test2, Instant.parse(runOneTiming), false),
            TestResult(test3, Instant.parse(runOneTiming), true),

            TestResult(test2, Instant.parse(runTwoTiming), false),
            TestResult(test3, Instant.parse(runTwoTiming), true),

            TestResult(test1, Instant.parse(runThreeTiming), false),
            TestResult(test2, Instant.parse(runThreeTiming), false),
            TestResult(test3, Instant.parse(runThreeTiming), false),
        )
    }

    fun displayReport(
        testName: String, testRun: Instant) =
        ReportDisplay(
            listOf(
                Row("header", listOf(Column(formatter.format(testRun)))),
                Row(testName, listOf(Column("passed")))
            )
        )
}
