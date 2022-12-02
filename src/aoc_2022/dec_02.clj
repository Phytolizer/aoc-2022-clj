(ns aoc-2022.dec-02
  (:require [clojure.java.io :refer [reader]]
            [clojure.string :refer [split]]))

(defn- game-result [a x]
  (condp = (list a x)
    '(\A \X) :draw
    '(\A \Y) :win
    '(\A \Z) :loss
    '(\B \X) :loss
    '(\B \Y) :draw
    '(\B \Z) :win
    '(\C \X) :win
    '(\C \Y) :loss
    '(\C \Z) :draw))

(defn- inverse-game-result [a result]
  (condp = (list a result)
    '(\A :draw) \X
    '(\A :win) \Y
    '(\A :loss) \Z
    '(\B :draw) \Y
    '(\B :win) \Z
    '(\B :loss) \X
    '(\C :draw) \Z
    '(\C :win) \X
    '(\C :loss) \Y))

(def ^:private win-scores
  {:win 6
   :draw 3
   :loss 0})

(def ^:private text-to-shape
  {\X :rock
   \Y :paper
   \Z :scissors})

(def ^:private text-to-result
  {\X :loss
   \Y :draw
   \Z :win})

(def ^:private shape-scores
  {:rock 1
   :paper 2
   :scissors 3})

(defn- split' [re s]
  (split s re))

(defn- iter [games total type]
  (if (empty? games)
    total
    (let [score (if (seq (first games))
                  (let [[a x] (->> (first games)
                                   (split' #" ")
                                   (map first))]
                    (case type
                      :response (let [shape-score (->> x
                                                       (get text-to-shape)
                                                       (get shape-scores))]
                                  (->> (list a x)
                                       (apply game-result)
                                       (get win-scores)
                                       (+ shape-score)))
                      :result (let [response (get text-to-result x)]
                                (->> (list a response)
                                     (apply inverse-game-result)
                                     (get text-to-shape)
                                     (get shape-scores)
                                     (+ (get win-scores response))))
                      (throw (ex-info "Unknown type" {:type type}))))
                  0)]
      (recur (rest games) (+ total score) type))))

(defn run [input part]
  (with-open [rdr (reader input)]
    (let [games (line-seq rdr)
          type (if (= part 1) :response :result)]
      (iter games 0 type))))
