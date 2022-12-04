(ns aoc-2022.dec-03
  (:require [clojure.java.io :refer [reader]]
            [clojure.set :refer [intersection]]))

(def ^:private lowercase (range 97 123))
(def ^:private uppercase (range 65 91))

(defmacro ^:private score [c]
  `(case (int ~c)
     ~lowercase (inc (- (int ~c) 97))
     ~uppercase (+ (- (int ~c) 65) 27)))

(defn- solve [line part group]
  (case part
    1 (let [middle (/ (count line) 2)
            common (->> line
                        (split-at middle)
                        (map set)
                        (apply intersection))]
        (score (first common)))
    2 (if (not= (count group) 3)
        0
        (let [common (->> group
                          (map set)
                          (apply intersection))]
          (score (first common))))))

(defn run [input part]
  (with-open [rdr (reader input)]
    (loop [lines (line-seq rdr)
           total 0
           group []]
      (if (empty? lines)
        total
        (let [line (first lines)
              group (conj group line)]
          (recur (rest lines)
                 (+ total (solve line part group))
                 (if (= (count group) 3)
                   []
                   group)))))))
