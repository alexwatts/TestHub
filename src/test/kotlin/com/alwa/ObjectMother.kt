package com.alwa

import com.alwa.testhub.domain.*
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
                        "mime_type": "gif;base64",
                        "name": "screenshot"
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
                        "mime_type": "gif;base64",
                        "name": "screenshot"
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
            TestResult(test1, Instant.parse(runOneTiming), true, null, "default"),
            TestResult(test2, Instant.parse(runOneTiming), false, null, "default"),
            TestResult(test3, Instant.parse(runOneTiming), true, null, "default"),

            TestResult(test2, Instant.parse(runTwoTiming), false, null, "default"),
            TestResult(test3, Instant.parse(runTwoTiming), true, null, "default"),

            TestResult(test1, Instant.parse(runThreeTiming), false, null, "default"),
            TestResult(test2, Instant.parse(runThreeTiming), false, null, "default"),
            TestResult(test3, Instant.parse(runThreeTiming), false, null, "default"),
        )
    }

    fun singleRunWithScreenShot() : List<TestResult> {
        val runOneTiming = "2020-05-20T09:00:00Z"
        val test1 = "test1"
        return listOf(
            TestResult(test1, Instant.parse(runOneTiming), true, ScreenShot("mime/type", "htQWEC6543"), "default"))

    }

    fun displayReport(
        testName: String, testRun: Instant) =
        ReportDisplay(
            "default",
            listOf(
                Row("header", listOf(Column(formatter.format(testRun), null))),
                Row(testName, listOf(Column("passed", Image("screenshot", "gif;base64", "guehnfdsaghl545423hbkj34lknb5hk34"))))
            )
        )

    fun emptyReport() =
        ReportDisplay(
            "default",
            listOf(Row("header", emptyList()))
        )

    fun twoGroupsDisplay() =
        ReportDisplay(
            "default",
            listOf(
                Row("header",
                    listOf(
                        Column(ObjectMother.formatter.format(Instant.parse("2021-11-20T09:01:00Z")), null),
                        Column(ObjectMother.formatter.format(Instant.parse("2021-11-20T09:00:00Z")), null)
                    )
                ),
                Row("Test Example",
                    listOf(
                        Column("passed", Image("screenshot", "gif;base64", "guehnfdsaghl545423hbkj34lknb5hk34")),
                        Column("passed", Image("screenshot", "gif;base64", "guehnfdsaghl545423hbkj34lknb5hk34"))
                    )
                )
            )
        )
}
