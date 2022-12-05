(ns aoc-2022.test-core
  (:require [clojure.test :refer [is testing]]))

(defmacro advent-test [day expected-arr]
  (list 'do
        `(require (quote [~(symbol (format "aoc-2022.dec-%02d" day)) :as ~(symbol "day")]))
        (apply (partial list
                        'clojure.test/deftest
                        (symbol (format "test-dec%02d" day)))
               (for [i (range 0 4)]
                 (let [input (nth ["simple" "full"] (mod i 2))
                       part (inc (quot i 2))
                       expected (nth expected-arr i)
                       run (symbol "day" "run")]
                   `(testing ~(format "part %d, %s input" part input)
                      (is (= ~expected
                             (~run ~(format "inputs/dec-%02d-%s.txt" day input)
                                   ~part)))))))))
