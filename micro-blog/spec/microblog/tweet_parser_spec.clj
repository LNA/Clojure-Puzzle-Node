(ns microblog.tweet-parser-spec
  (:require [speclj.core :refer :all]
            [microblog.tweet-parser :refer :all]))

(describe "name-of-sender"
  (it "returns the name of a sender"
    (should="test_sender" (name-of-sender "test_sender: foo bar"))))

(describe "content"
  (it "returns the name of a sender"
    (should=" foo bar" (content "test_sender: foo bar"))))

(describe "receiver"
  (it "returns all names of receivers"
    (should-contain " @bar" (receivers "test_sender: @foo was @bar"))))

(describe "parse-tweets"
  (xit "returns a hash-map of the parsed tweets"
    (should= " foo bar" (parse-tweets "data/complete_input.txt"))))

(describe "parse-sender-and-receiver"
  (xit "returns a hash-map of each tweets sender and receiver"
    (should= " foo bar" (parse-sender-and-receiver "data/complete_input.txt"))))