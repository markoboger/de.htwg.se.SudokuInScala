package de.htwg.se.sudoku.aview


import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RecordedSimulation extends Simulation {

  val httpProtocol = http
    .baseURL("http://localhost:8080")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:36.0) Gecko/20100101 Firefox/36.0")

  val uri1 = "http://localhost:8080/sudoku"

  val scn = scenario("de.htwg.se.sudoku.aview.RecordedSimulation")
    .exec(http("request_0")
      .get("/sudoku/new"))
    .pause(14)
    .exec(http("request_1")
      .get("/sudoku/random"))
    .pause(14)
    .exec(http("request_2")
      .get("/sudoku/111"))
    .pause(8)
    .exec(http("request_3")
      .get("/sudoku/solve"))
    .pause(7)
    .exec(http("request_4")
      .get("/sudoku/new"))
    .pause(11)
    .exec(http("request_5")
      .get("/sudoku/random"))
    .pause(5)
    .exec(http("request_6")
      .get("/sudoku/solve"))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}