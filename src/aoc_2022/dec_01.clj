(ns aoc-2022.dec-01
  (:require [clojure.java.io :refer [reader]]))

(defn- insert-max [maxes x keep]
  (if (> keep (count maxes))
    (cons x maxes)
    (let [min-max (apply min maxes)]
      (if (<= x min-max)
        maxes
        (cons x (remove #{min-max} maxes))))))

(defn- iter [numbers count maxes keep]
  (if (empty? numbers)
    (apply + (if (> count 0)
               (insert-max maxes count keep)
               maxes))
    (let [number (first numbers)
          [count maxes] (if (empty? number)
                          [0 (insert-max maxes count keep)]
                          [(+ count (Integer/parseInt number)) maxes])]
      (recur (rest numbers) count maxes keep))))

(defn run [input part]
  (with-open [rdr (reader input)]
    (let [numbers (line-seq rdr)
          maxes-to-keep (if (= part 1) 1 3)]
      (iter numbers 0 nil maxes-to-keep))))
