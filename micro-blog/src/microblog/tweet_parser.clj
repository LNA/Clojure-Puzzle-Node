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
    (map (fn [x] (first-level-connections-for x tweets)) first-level-users))); higher level function.  Still returns a lazy seq

(defn second-level-connections-including-user [user tweets]
  (let [intermediate-connections (first-level-connections-for-users-first-level-connections user tweets)]
    (distinct (apply concat intermediate-connections))))

(defn second-level-connections-for [user tweets]
  (let [second-level-including-user (second-level-connections-including-user user tweets)]
    (filter #(not= % user) second-level-including-user)))

(defn trans-third-level-connections [user tweets]
  (let [second-level-users (second-level-connections-for user tweets)]
    (map (fn [x] (first-level-connections-for x tweets)) second-level-users)))

(defn first-trans-third-level-connections-for [user tweets] ;duplication
  (first (trans-third-level-connections user tweets))) 

(defn third-level-connections-for [user tweets]
  (let [trans-level-connections (first-trans-third-level-connections-for user tweets)
        first-level-connections (first-level-connections-for user tweets)]
    (clojure.set/difference trans-level-connections first-level-connections)))

(defn trans-fourth-level-connections-for [user tweets]
  (let [third-level-users (third-level-connections-for user tweets)] 
    (map (fn [x] (first-level-connections-for x tweets)) third-level-users)))

(defn first-trans-fourth-level-connections-for [user tweets] ;duplication
  (first (trans-fourth-level-connections-for user tweets))) 

(defn fourth-level-connections-for [user tweets]
  (let [trans-level-connections (first-trans-fourth-level-connections-for user tweets)
        second-level-connections (second-level-connections-for user tweets)]
    (clojure.set/difference trans-level-connections second-level-connections)))