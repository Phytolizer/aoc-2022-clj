(defproject aoc-2022 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.trace "0.7.11"]]
  :main ^:skip-aot aoc-2022.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
