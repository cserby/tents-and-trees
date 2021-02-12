FROM hseeberger/scala-sbt:graalvm-ce-21.0.0-java11_1.4.7_2.12.13

RUN mkdir /tents-and-trees && chown -R sbtuser:sbtuser /tents-and-trees
USER sbtuser
WORKDIR /tents-and-trees
COPY --chown=sbtuser:sbtuser src/ src/
COPY --chown=sbtuser:sbtuser project/ project/
COPY --chown=sbtuser:sbtuser build.sbt build.sbt

RUN sbt test:compile

CMD sbt test