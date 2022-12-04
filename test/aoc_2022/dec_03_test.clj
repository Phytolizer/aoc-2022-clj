(ns aoc-2022.dec-03-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2022.dec-03 :refer [run]]))

(deftest dec-03
  (dorun (for [[part [simple full]] [[1 [157 7766]]
                                     [2 [70 2415]]]]
           (do
             (testing (str "part " part " simple")
               (is (= (run "inputs/dec-03-simple.txt" part) simple)))
             (testing (str "part " part " full")
               (is (= (run "inputs/dec-03-full.txt" part) full)))))))
