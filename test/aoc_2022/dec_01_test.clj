(ns aoc-2022.dec-01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2022.dec-01 :refer [run]]))

(deftest part-1
  (dorun (for [[part [simple full]] [[1 [24000 69289]] [2 [45000 205615]]]]
           (do
             (testing (str "part " part " simple")
               (is (= (run "inputs/dec-01-simple.txt" part) simple)))
             (testing (str "part " part " full")
               (is (= (run "inputs/dec-01-full.txt" part) full)))))))
