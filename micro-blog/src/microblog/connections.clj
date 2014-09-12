(ns microblog.connections
 (:require
   [clojure.string :refer [replace]]
   [clojure.set])
 (:refer-clojure :exclude [replace]))

(defn tweets-from [user tweets]
  (filter #(= user (:sender %)) tweets))

(defn tweets-to [user tweets]
  (filter #(contains? ( set(:receivers %)) user) tweets))

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
    (map (fn [x] (first-level-connections-for x tweets)) first-level-users)))

(defn second-level-connections-including-user [user tweets]
  (let [intermediate-connections (first-level-connections-for-users-first-level-connections user tweets)]
    (distinct (apply concat intermediate-connections))))

(defn second-level-connections-for [user tweets]
  (let [second-level-including-user (second-level-connections-including-user user tweets)]
    (filter #(not= % user) second-level-including-user)))

(defn trans-third-level-connections [user tweets]
  (let [second-level-users (second-level-connections-for user tweets)]
    (map (fn [x] (first-level-connections-for x tweets)) second-level-users)))

(defn first-trans-third-level-connections-for [user tweets] 
  (first (trans-third-level-connections user tweets))) 

(defn third-level-connections-for [user tweets]
  (let [trans-level-connections (first-trans-third-level-connections-for user tweets)
        first-level-connections (first-level-connections-for user tweets)]
    (clojure.set/difference trans-level-connections first-level-connections)))

(defn trans-fourth-level-connections-for [user tweets]
  (let [third-level-users (third-level-connections-for user tweets)] 
    (map (fn [x] (first-level-connections-for x tweets)) third-level-users)))

(defn first-trans-fourth-level-connections-for [user tweets] 
  (first (trans-fourth-level-connections-for user tweets))) 

(defn fourth-level-connections-for [user tweets]
  (let [trans-level-connections (first-trans-fourth-level-connections-for user tweets)
        second-level-connections (second-level-connections-for user tweets)]
    (clojure.set/difference trans-level-connections second-level-connections)))
