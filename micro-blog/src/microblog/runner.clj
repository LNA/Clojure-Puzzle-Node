(ns microblog.runner
  (:require [microblog.tweet-parser :as p])
  (:gen-class :main true))

(defn -main [input-file-path & args]
  (p/content-message)
  (let [parsed-tweets (p/tweets-from-file input-file-path)]
    (doseq [tweet parsed-tweets]
      (prn tweet))
    (doseq [username (p/all-usernames parsed-tweets)]
      (println "--------------------users that received tweets from" username "--------------------------------")
      (prn (p/users-who-received-tweets-from username parsed-tweets)))
    (doseq [username (p/all-usernames parsed-tweets)]
      (println "--------------------users that form a first level connection with" username "--------------------------------")
      (prn (p/first-level-connections-for username parsed-tweets)))
    (doseq [username (p/all-usernames parsed-tweets)]
      (println "--------------------users that form a second level connection with" username "--------------------------------")
      (prn (p/second-level-connections-for username parsed-tweets)))
    (doseq [username (p/all-usernames parsed-tweets)]
      (println "--------------------users that form a third level connection with" username "--------------------------------")
      (prn (p/third-level-connections-for username parsed-tweets)))
    (doseq [username (p/all-usernames parsed-tweets)]
      (println "--------------------users that form a fourth level connection with" username "--------------------------------")
      (prn (p/fourth-level-connections-for username parsed-tweets)))))
    