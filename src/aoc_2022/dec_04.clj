(ns aoc-2022.dec-04
  (:require [clojure.java.io :refer [reader]]
            [clojure.string :refer [split]]))

(defn- split' [set text]
  (split text set))

(defn- permute-args [f a b]
  (or (f a b)
      (f b a)))

(defn- bool->int [b]
  (if b 1 0))

(defn- range-contains? [a b]
  (let [[a-low a-high] a
        [b-low b-high] b]
    (and (<= a-low b-low)
         (<= b-high a-high))))

(defn- range-overlaps? [a b]
  (let [[a-low a-high] a
        [b-low b-high] b]
    (or (<= a-low b-low a-high)
        (<= b-low a-low b-high))))

(defn- solve [line part]
  (bool->int
   (apply
    (partial permute-args
             (case part
               1 range-contains?
               2 range-overlaps?))
    (->> line
         (split' #",")
         (map (partial split' #"-"))
         (map (partial map #(Integer/parseInt %)))))))

(defn run [input part]
  (with-open [rdr (reader input)]
    (loop [lines (line-seq rdr)
           total 0]
      (if (empty? lines)
        total
        (recur (rest lines)
               (+ total (solve (first lines) part)))))))
