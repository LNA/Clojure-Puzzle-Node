(ns scrabble.dictionary_rules_spec
  (:require [speclj.core :refer :all]
            [scrabble.dictionary_rules :refer :all]))

(describe "Dictionary Rules"
          (it "returns true for a compatible word"
              (should= true
                       (compatible? "legs"))))
