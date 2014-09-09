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

(describe "parse-tweets"
  (it "returns a hash-map of a tweets content and sender"
    (should=
      '({:content " hi @bob!", :sender "roberta" :receivers ["bob"]})
      (parse-tweets '("roberta: hi @bob!")))))

(describe "tweets-from"
  (it "gives the tweets from a user"
    (let [tweets ["gia: hi @bob @marco!"
                  "bob: sup @marco?"]
          parsed-tweets (parse-tweets tweets)]
      (should= '({:content " sup @marco?", :sender "bob", :receivers ("marco")})
               (tweets-from "bob" parsed-tweets)))))

(describe "tweets-to"
  (it "gives the tweets sent to a user"
    (let [tweets ["gia: hi @bob @jenny!"
                  "bob: sup @marco?"
                  "marco: I sent this"]
          parsed-tweets (parse-tweets tweets)]
      (should= '({:content " sup @marco?", :sender "bob", :receivers ("marco")})
               (tweets-to "marco" parsed-tweets)))))

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
    (let [tweets ["bob: hi @gia!"
                  "gia: sup @jenny?"
                  "other: sup @gia?"
                  "gia: hi again @bob?"]
          parsed-tweets (parse-tweets tweets)]
      (should= #{"bob" "jenny"}
               (users-who-received-tweets-from "gia" parsed-tweets)))))


(describe "users-who-sent-tweets-to"
  (it "gives users who sent tweets"
    (let [tweets ["bob: hi @gia!"
                  "gia: sup @jenny?"
                  "other: sup @gia?"
                  "gia: hi again @bob?"]
          parsed-tweets (parse-tweets tweets)]
      (should= #{"bob" "other"}
               (users-who-sent-tweets-to "gia" parsed-tweets)))))

(describe "first-level-connections-for"
  (it "gives first-level-connections for a user"
        (should= #{"bob"}
          (let [tweets ["bob: hi @gia!"
                        "gia: sup @jenny?"
                        "other: sup @gia?"
                        "gia: hi again @bob?"]
          parsed-tweets (parse-tweets tweets)]
          (first-level-connections-for "gia" parsed-tweets)))))

(describe "first-level-connections-for-users-first-level-connections"
  (it "gives first-level-connections-for-users-first-level-connections"
        (should= [#{"mac" "gia"} #{"other" "gia"}]
          (let [tweets ["bob: hi @gia!"
                        "bob: sup @other?"
                        "other: sup @bob?"
                        "gia: hi again @bob?"
                        "gia: hi @ava"
                        "ava: hi @gia"
                        "ava: hi @mac"
                        "mac: hi @ava"]
          parsed-tweets (parse-tweets tweets)]
          (first-level-connections-for-users-first-level-connections "gia" parsed-tweets)))))

(describe "second-level-connections-including-user"
  (it "gives second-level-connections-including-user"
        (should= ["mac" "gia" "other"]
          (let [tweets ["bob: hi @gia!"
                        "bob: sup @other?"
                        "other: sup @bob?"
                        "gia: hi again @bob?"
                        "gia: hi @ava"
                        "ava: hi @gia"
                        "ava: hi @mac"
                        "mac: hi @ava"]
          parsed-tweets (parse-tweets tweets)]
          (second-level-connections-including-user "gia" parsed-tweets)))))

(describe "second-level-connections-for"
  (it "gives second-level-connections for a user"
        (should= ["mac" "other"]
          (let [tweets ["bob: hi @gia!"
                        "bob: sup @other?"
                        "other: sup @bob?"
                        "gia: hi again @bob?"
                        "gia: hi @ava"
                        "ava: hi @gia"
                        "ava: hi @mac"
                        "mac: hi @ava"]
          parsed-tweets (parse-tweets tweets)]
          (second-level-connections-for "gia" parsed-tweets)))))

(describe "trans-third-level-connections"
  (it "gives the first-level-connections for the third-level-connections for a user"
        (should= [#{"mac" "bob"}]
          (let [tweets ["bob: hi @gia!"
                        "gia: hi @bob."
                        "bob: sup @other?"
                        "other: sup @bob?"
                        "other: sup @mac?"
                        "mac: hi @other?"]
          parsed-tweets (parse-tweets tweets)]
          (trans-third-level-connections "gia" parsed-tweets)))))

(describe "first-trans-third-level-connections-for"
  (it "gives the third-level-connections for a user"
        (should= #{"mac" "bob"}
          (let [tweets ["bob: hi @gia!"
                        "gia: hi @bob."
                        "bob: sup @other?"
                        "other: sup @bob?"
                        "other: sup @mac?"
                        "mac: hi @other?"]
          parsed-tweets (parse-tweets tweets)]
          (first-trans-third-level-connections-for "gia" parsed-tweets)))))

(describe "third-level-connections-for"
  (it "gives the third-level-connections for a user"
        (should= #{"mac"}
          (let [tweets ["bob: hi @gia!"
                        "gia: hi @bob."
                        "bob: sup @other?"
                        "other: sup @bob?"
                        "other: sup @mac?"
                        "mac: hi @other?"]
          parsed-tweets (parse-tweets tweets)]
          (third-level-connections-for "gia" parsed-tweets)))))

(describe "third-level-connections-for"
  (it "gives the third-level-connections for a user when a third level connections is already connected to the user"
        (should= #{"mac"}
          (let [tweets ["bob: hi @gia!"
                        "gia: hi @bob."
                        "bob: sup @other?"
                        "other: sup @bob?"
                        "other: sup @mac?"
                        "mac: hi @other?"
                        "other: sup @bob?"
                        "bob: hi @other?"]
          parsed-tweets (parse-tweets tweets)]
          (third-level-connections-for "gia" parsed-tweets)))))

(describe "trans-fourth-level-connections-for"
  (it "gives the third-level-connections for a user"
        (should= [#{"other" "nan"}]
          (let [tweets ["bob: hi @gia!"
                        "gia: hi @bob."
                        "bob: sup @other?"
                        "other: sup @bob?"
                        "other: sup @mac?"
                        "mac: hi @other?"
                        "nan: hi @mac!"
                        "mac: hi @nan"]
          parsed-tweets (parse-tweets tweets)]
          (trans-fourth-level-connections-for "gia" parsed-tweets)))))

(describe "first-trans-fourth-level-connections-for"
  (it "gives the third-level-connections for a user"
        (should= #{"other" "nan"}
          (let [tweets ["bob: hi @gia!"
                        "gia: hi @bob."
                        "bob: sup @other?"
                        "other: sup @bob?"
                        "other: sup @mac?"
                        "mac: hi @other?"
                        "nan: hi @mac!"
                        "mac: hi @nan"]
          parsed-tweets (parse-tweets tweets)]
          (first-trans-fourth-level-connections-for "gia" parsed-tweets)))))
