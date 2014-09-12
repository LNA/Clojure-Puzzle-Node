(ns microblog.runner
  (:require [microblog.tweet-parser           :as p]
            [microblog.ui                     :as u]
            [microblog.connections            :as c])
  (:gen-class :main true))

(defn -main [input-file-path & args]
  (u/content-message)
  (let [parsed-tweets (p/tweets-from-file input-file-path)]
    (doseq [tweet parsed-tweets]
      (prn tweet))
    (doseq [username (p/all-usernames parsed-tweets)]
      (println "--------------------users that received tweets from" username "--------------------------------")
      (prn (c/users-who-received-tweets-from username parsed-tweets)))
    (doseq [username (p/all-usernames parsed-tweets)]
      (println "--------------------users that form a first level connection with" username "--------------------------------")
      (prn (c/first-level-connections-for username parsed-tweets)))
    (doseq [username (p/all-usernames parsed-tweets)]
      (println "--------------------users that form a second level connection with" username "--------------------------------")
      (prn (c/second-level-connections-for username parsed-tweets)))
    (doseq [username (p/all-usernames parsed-tweets)]
      (println "--------------------users that form a third level connection with" username "--------------------------------")
      (prn (c/third-level-connections-for username parsed-tweets)))
    (doseq [username (p/all-usernames parsed-tweets)]
      (println "--------------------users that form a fourth level connection with" username "--------------------------------")
      (prn (c/fourth-level-connections-for username parsed-tweets)))))
    