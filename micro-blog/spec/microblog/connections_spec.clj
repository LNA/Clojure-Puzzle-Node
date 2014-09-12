(ns microblog.tweet-parser-spec
  (:require [speclj.core :refer :all]
            [microblog.connections :refer :all]
            [microblog.tweet-parser :refer :all]))

; I'm testing private methods to get better at testing. 

(describe "private interface"
  (it "gives the tweets from a user"
    (let [tweets ["gia: hi @bob @marco!"
                  "bob: sup @marco?"]
          parsed-tweets (parse-tweets tweets)]
      (should= '({:content " sup @marco?", :sender "bob", :receivers ("marco")})
               (tweets-from "bob" parsed-tweets))))

  (it "gives the tweets sent to a user"
    (let [tweets ["gia: hi @bob @jenny!"
                  "bob: sup @marco?"
                  "marco: I sent this"]
          parsed-tweets (parse-tweets tweets)]
      (should= '({:content " sup @marco?", :sender "bob", :receivers ("marco")})
               (tweets-to "marco" parsed-tweets))))

  (it "collects both senders and receivers from parsed tweets"
    (let [tweets ["gia: hi @bob!"
                  "gia: sup @jenny?"
                  "other: sup @jimbo?"
                  "gia: hi again @bob?"]
          parsed-tweets (parse-tweets tweets)]
      (should= #{"gia" "bob" "jenny" "jimbo" "other"}
               (all-usernames parsed-tweets))))

  (it "gives users who received tweets"
    (let [tweets ["bob: hi @gia!"
                  "gia: sup @jenny?"
                  "other: sup @gia?"
                  "gia: hi again @bob?"]
          parsed-tweets (parse-tweets tweets)]
      (should= #{"bob" "jenny"}
               (users-who-received-tweets-from "gia" parsed-tweets))))

  (it "gives users who sent tweets"
    (let [tweets ["bob: hi @gia!"
                  "gia: sup @jenny?"
                  "other: sup @gia?"
                  "gia: hi again @bob?"]
          parsed-tweets (parse-tweets tweets)]
      (should= #{"bob" "other"}
               (users-who-sent-tweets-to "gia" parsed-tweets)))))

;public 
(describe "connections"
  (it "gives up to 4 levels of connections for a user"
    (let [tweets ["bob: hi @gia!"
                  "gia: hi @bob."
                  "bob: sup @other?"
                  "other: sup @bob?"
                  "other: sup @mac?"
                  "mac: hi @other?"
                  "nan: hi @mac!"
                  "mac: hi @nan"]
          parsed-tweets (parse-tweets tweets)]
      (should= #{"bob"}
          (first-level-connections-for  "gia" parsed-tweets))
      (should= ["other"]
          (second-level-connections-for "gia" parsed-tweets))
      (should= #{"mac"}
          (third-level-connections-for  "gia" parsed-tweets))
      (should= #{"nan"}
          (fourth-level-connections-for "gia" parsed-tweets)))))
