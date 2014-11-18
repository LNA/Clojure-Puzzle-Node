(ns scrabble.scorer_spec
  (:require [speclj.core :refer :all]
            [scrabble.scorer :refer :all]))

(describe "Scorer"
          (it "gives the corrrect score for lower case letters"
              (should= 7
                       (find-score "f"))
              (should= 5
                       (find-score "w")))
          )
