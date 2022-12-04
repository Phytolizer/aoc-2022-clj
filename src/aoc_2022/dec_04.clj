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
    (or (and (<= a-low b-low)
             (<= b-low a-high))
        (and (<= b-low a-low)
             (<= a-low b-high)))))

(defn- solve [line part]
  (let [ranges (->> line
                    (split' #",")
                    (map (partial split' #"-"))
                    (map (partial map #(Integer/parseInt %))))]
    (case part
      1 (bool->int (permute-args range-contains?
                                 (first ranges)
                                 (second ranges)))
      2  (bool->int  (permute-args range-overlaps?
                                   (first ranges)
                                   (second ranges))))))

(defn run [input part]
  (with-open [rdr (reader input)]
    (loop [lines (line-seq rdr)
           total 0]
      (if (empty? lines)
        total
        (let [line (first lines)]
          (recur (rest lines)
                 (+ total (solve line part))))))))
