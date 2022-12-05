(ns aoc-2022.dec-05
  (:require [clojure.java.io :refer [reader]]))

(defn- digit? [c]
  (let [zero (int \0)
        nine (int \9)]
    (<= zero (int c) nine)))

(defn- until-numbers
  ([lines] (until-numbers lines []))
  ([lines acc]
   (if (or (empty? lines)
           (digit? (nth (first lines) 1)))
     acc
     (recur (rest lines) (conj acc (first lines))))))

(defn- move-command? [line]
  (let [matcher (re-matcher #"^move (\d+) from (\d+) to (\d+)$" line)]
    (when (re-find matcher)
      (let [groups (re-groups matcher)]
        (map #(Integer/parseInt %) (rest groups))))))

(defn- ->MoveCommand [[num from to]]
  {:num num
   :from (dec from)
   :to (dec to)})

(defn- parse-commands [lines]
  (for [line (->> lines
                  (map move-command?)
                  (filter identity))]
    (->MoveCommand line)))

(defn- parse-crates [lines]
  (for [line (until-numbers lines)]
    (for [i (range 1 (count line) 4)]
      (nth line i))))

(defn- ->Stacks [crates]
  (for [stack-i (range 0 (apply max (map count crates)))]
    (loop [crates (reverse crates)
           acc []]
      (let [crate (nth (first crates) stack-i \space)]
        (if (= crate \space)
          acc
          (recur (rest crates) (conj acc crate)))))))

(defn- run-command [part stacks command]
  (case part
    1 (loop [stacks (into [] stacks)
             it (range 0 (:num command))]
        (if (empty? it)
          stacks
          (recur
           (let [from (get stacks (:from command))
                 to (get stacks (:to command))]
             (assoc stacks
                    (:from command) (pop from)
                    (:to command) (conj to (last from))))
           (rest it))))
    2 (let [stacks (into [] stacks)
            from (get stacks (:from command))
            to (get stacks (:to command))
            [rest carried] (split-at (- (count from) (:num command)) from)]
        (assoc stacks
               (:from command) rest
               (:to command) (concat to carried)))))

(defn- solve [lines part]
  (->> (reduce (partial run-command part)
               (-> lines
                   (parse-crates)
                   (->Stacks))
               (parse-commands lines))
       (map last)
       (apply str)))

(defn run [input part]
  (let [rdr (reader input)]
    (solve (line-seq rdr) part)))
