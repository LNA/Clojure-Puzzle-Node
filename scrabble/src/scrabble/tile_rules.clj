(ns scrabble.tile_rules
  (:require
    [scrabble.game_pieces :as g]
    [clojure.string :refer [replace]]
    [clojure.java.io :as io]
    [clojure.string :as str]
    [clojure.set])
  (:refer-clojure :exclude [replace]))

(def score-letter
   (into {} (map (fn [s] [(subs s 0 1) (Integer/valueOf (subs s 1))])
                                          (g/tiles))))
(defn get-letters [word]
  (rest (str/split word #"")))

(defn score-letters [word]
  (map (fn [letter] (score-letter letter)) (get-letters word)))

(defn score-word [word]
  (reduce + (score-letters word)))
