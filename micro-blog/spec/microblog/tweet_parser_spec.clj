(ns microblog.tweet-parser-spec
  (:require [speclj.core :refer :all]
            [microblog.tweet-parser :refer :all]))

(describe "name-of-sender"
  (it "returns the name of a sender"
    (should="test_sender" (name-of-sender "test_sender: foo bar"))))

(describe "content"
  (it "returns the name of a sender"
    (should=" foo bar" (content "test_sender: foo bar"))))

(describe "parse-tweets"
  (it "returns a hash-map of the parsed tweets"
    (should= " foo bar" (parse-tweets "data/complete_input.txt"))))
