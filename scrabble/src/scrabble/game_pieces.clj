(ns scrabble.game_pieces
  (:require
    [clojure.string :as st]))

(defn dictionary []
  [ "gyre","gimble","wabe","mimsy",
   "borogoves","raths","outgrabe","jabberwock",
   "jubjub","shun","frumious","bandersnatch","vorpal","manxome",
   "foe","tumtum", "uffish","whiffling","tulgey","burbled",
   "galumphing","frabjous","callooh","callay",
   "chortled","brillig","slithy","toves","gyre","gimble","mome"])

(defn board[]
  [
   "1 1 1 1 1 1 1 1 1 1 1 1",
   "1 1 1 2 1 2 1 1 1 1 1 1",
   "1 2 1 1 1 3 1 1 1 1 2 1",
   "2 1 1 1 1 1 1 1 1 2 2 1",
   "1 1 1 2 1 1 1 1 1 1 1 1",
   "1 1 1 1 1 1 2 1 1 1 2 1",
   "1 1 1 1 1 1 1 1 2 1 1 1",
   "1 1 1 1 1 1 1 1 1 1 1 2",
   "1 1 1 1 1 1 1 1 1 1 1 1"
   ])

(defn tiles []
  ["i4" "w5" "g6" "f7" "s2" "e1" "l3" "h8" "n1" "f7"
    "b8" "r12" "u3" "g6" "i4" "q9" "o3" "d2" "s2" "f7" ])
