(ns aoc-2022.dec-04-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2022.dec-04 :refer [run]]))

(deftest dec-04
  (dorun (for [[part [simple full]] [[1 [2 433]]
                                     [2 [4 852]]]]
           (do
             (testing (str "part " part " simple")
               (is (= (run "inputs/dec-04-simple.txt" part) simple)))
             (testing (str "part " part " full")
               (is (= (run "inputs/dec-04-full.txt" part) full)))))))
