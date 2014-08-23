(ns microblog.tweet-parser-spec
  (:require [speclj.core :refer :all]
            [microblog.tweet-parser :refer :all]))

(describe "name-of-sender"
  (it "returns the name of a sender"
    (should="test_sender" (name-of-sender "test_sender: foo bar"))))

(describe "content"
  (it "returns the content"
    (should=" foo bar" (content "test_sender: foo bar"))))

(describe "receivers"
  (it "returns all names of receivers"
    (should= ["foo" "bar"] (receivers "test_sender: @foo was @bar"))))

; (describe "output :content"
;   (it "returns the content of a tweet"
;     (should= " hi, @gia" (output :content "foo: hi, @gia"))))

; (describe "output :receivers"
;   (it "returns the receivers of a tweet"
;     (should= ["gia"] (output :receivers "foo: hi, @gia")))

;   (it "returns multiple receivers of a tweet"
;     (should= ["gia" "tom"] (output :receivers "foo: hi, @gia and @tom")))
;   )

(describe "parse-tweets"
  (it "returns a hash-map of a tweets content and sender"
    (should= 
      '({:content " hi @bob!", :sender "roberta" :receivers ["bob"]}) 
      (parse-tweets '("roberta: hi @bob!")))))

(describe "all-usernames"
  (it "collects both senders and receivers from parsed tweets"
    (let [tweets ["gia: hi @bob!"
                  "gia: sup @jenny?"
                  "other: sup @jimbo?"
                  "gia: hi again @bob?"]
          parsed-tweets (parse-tweets tweets)]
      (should= #{"gia" "bob" "jenny" "jimbo" "other"}
               (all-usernames parsed-tweets)))))

(describe "users-who-received-tweets-from"
  (it "gives users who received tweets"
    (let [tweets ["gia: hi @bob!"
                  "gia: sup @jenny?"
                  "other: sup @jimbo?"
                  "gia: hi again @bob?"]
          parsed-tweets (parse-tweets tweets)]
      (should= #{"bob" "jenny"}
               (users-who-received-tweets-from "gia" parsed-tweets)))))