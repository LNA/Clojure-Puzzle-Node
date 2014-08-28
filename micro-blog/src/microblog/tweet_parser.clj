(ns microblog.tweet-parser
 (:require
   [clojure.string :refer [replace]]
   [clojure.java.io :as io]
   [clojure.set])
 (:refer-clojure :exclude [replace]))

(defn content-message []
  (println "Here is a list of senders, receivers, and their content: "))

(defn first-level-message []
  (println "The first level connections are: "))

(defn name-of-sender [line]
  (re-find #"^[^:]+" line))

(defn content [line]
  (re-find #"[^:]+$" line))

(defn receivers [line]
  (re-seq #"(?<=@)[a-zA-Z]+" line))

(defn read-file [file]
  (with-open [rdr (io/reader file)]
    (doall (line-seq rdr))))

(defn parse-tweets [lines]
  (map
    #(hash-map :sender (name-of-sender %),
               :receivers (receivers %),
               :content (content %))
    lines))

(defn tweets-from-file [file]
  (let [lines (read-file file)]
    (parse-tweets lines)))

(defn tweets-from [user tweets]
  (filter #(= user (:sender %)) tweets))

(defn tweets-to [user tweets]
  (filter #(contains? ( set(:receivers %)) user) tweets))

(defn all-usernames [tweets]
  (set (concat (mapcat :receivers tweets)
               (map :sender tweets))))

(defn users-who-received-tweets-from [user tweets]
  (let [user-tweets (tweets-from user tweets)]
    (set (mapcat :receivers user-tweets))))

(defn users-who-sent-tweets-to [user tweets]
  (let [tweets-to-user (tweets-to user tweets)]
    (set (map :sender tweets-to-user))))

(defn first-level-connections-for [user tweets]
  (let [receivers (users-who-received-tweets-from user tweets)
        senders   (users-who-sent-tweets-to user tweets)]
  (clojure.set/intersection receivers senders)))

(defn first-level-connections-for-users-first-level-connections [user tweets]
  (let [first-level-users (first-level-connections-for user tweets)]
    (for [first-level-user first-level-users]
      (first-level-connections-for first-level-user tweets))))

(defn second-level-connections-including-user [user tweets]
  (let [intermediate-connections (first-level-connections-for-users-first-level-connections user tweets)]
    (distinct (apply concat intermediate-connections))))