FROM hseeberger/scala-sbt
WORKDIR /sudoku
ADD . /sudoku
CMD sbt test
