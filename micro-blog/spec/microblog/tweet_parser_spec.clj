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
  (it "returns a hash-map of a tweets content and sender"
    (should= '({:content " hi @bob!", :sender "roberta"}) (parse-tweets  '("roberta: hi @bob!") :content))))

(describe "parse-sender-and-receiver"
  (it "returns a hash-map of senders and receivers"
    (should= '({:sender "gia", :receivers (" @bob")}) (parse-tweets '("gia: hi @bob!") :receivers))))

(describe "strip-symbol"
  (it "strips any given symbol from a string"
    (should= "foo" (strip-symbol "@foo" "@"))))
