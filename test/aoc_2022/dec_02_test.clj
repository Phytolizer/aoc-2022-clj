(ns aoc-2022.dec-02-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2022.dec-02 :refer [run]]))

(deftest dec-02
  (dorun (for [[part [simple full]] [[1 [15 14531]]
                                     [2 [12 11258]]]]
           (do
             (testing (str "part " part " simple")
               (is (= (run "inputs/dec-02-simple.txt" part) simple)))
             (testing (str "part " part " full")
               (is (= (run "inputs/dec-02-full.txt" part) full)))))))
