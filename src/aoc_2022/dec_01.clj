(ns aoc-2022.dec-01
  (:require [clojure.java.io :refer [reader]]))

(defn- insert-max [maxes x keep]
  (if (empty? maxes)
    (cons x maxes)
    (if (> keep (count maxes))
      (cons x maxes)
      (let [min-max (apply min maxes)]
        (if (<= x min-max)
          maxes
          (cons x (remove #{min-max} maxes)))))))

(defn run [input part]
  (with-open [rdr (reader input)]
    (let [numbers (line-seq rdr)
          maxes-to-keep (if (= part 1) 1 3)
          iter (fn [numbers count maxes]
                 (if (empty? numbers)
                   (apply + (if (> count 0)
                              (insert-max maxes count maxes-to-keep)
                              maxes))
                   (let [number (first numbers)]
                     (if (empty? number)
                       (recur (rest numbers) 0 (insert-max maxes count maxes-to-keep))
                       (recur (rest numbers) (+ count (Integer/parseInt number)) maxes)))))]
      (iter numbers 0 nil))))
