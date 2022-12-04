(ns aoc-2022.dec-03
  (:require [clojure.java.io :refer [reader]]
            [clojure.set :refer [intersection]]))

(defn- range' [lower upper]
  (range lower (inc upper)))

(def ^:private lowercase (range' (int \a) (int \z)))
(def ^:private uppercase (range' (int \A) (int \Z)))

(defmacro ^:private score [c]
  `(case (int ~c)
     ~lowercase (inc (- (int ~c) (int \a)))
     ~uppercase (+ (- (int ~c) (int \A)) 27)))

(defn- solve [line part group]
  (case part
    1 (let [middle (/ (count line) 2)]
        (->> line
             (split-at middle)
             (map set)
             (apply intersection)
             (first)
             (score)))
    2 (if (not= (count group) 3)
        0
        (->> group
             (map set)
             (apply intersection)
             (first)
             (score)))))

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
