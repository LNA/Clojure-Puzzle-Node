(ns scrabble.scorer
 (:require
       [clojure.string :refer [replace]]
           [clojure.java.io :as io]
           [clojure.string :as str]
               [clojure.set])

  (:refer-clojure :exclude [replace]))

(def tiles
  ["i4" "w5" "g6" "f7" "s2" "e1" "l3" "h8" "n1" "f7"
    "b8" "r12" "u3" "g6" "i4" "q9" "o3" "d2" "s2" "f7" ])

(def score-letter
   (into {} (map (fn [s] [(subs s 0 1) (Integer/valueOf (subs s 1))])
                                          tiles)))
(defn get-letters [word]
  (rest (str/split word #"")))

(defn score-letters [word]
  (map (fn [letter] (score-letter letter)) (get-letters word)))

(defn score-word [word]
  (reduce + (score-letters word)))
