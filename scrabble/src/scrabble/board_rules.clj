(ns scrabble.board_rules
 (:require
       [scrabble.game_pieces :as g]
       [clojure.string :refer [replace]]
           [clojure.java.io :as io]
           [clojure.string :as str]
               [clojure.set])
  (:refer-clojure :exclude [replace]))
