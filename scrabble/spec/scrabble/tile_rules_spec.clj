(ns scrabble.tile_rules_spec
  (:require [speclj.core :refer :all]
            [scrabble.tile_rules :refer :all]))

(describe "Tile Scorer"
          (it "gives the correct score for lower case letters"
              (should= 7
                       (score-letter "f"))
              (should= 5
                       (score-letter "w")))
          (it "gives a split string for bid"
              (should= ["b" "i" "d"]
                       (get-letters "bid")))
          (it "gives the score for each letter in a given word"
              (should= [8 4 2]
                       (score-letters "bid")))
          (it "gives the score for a word"
              (should= 14
                       (score-word "bid")))
          )
