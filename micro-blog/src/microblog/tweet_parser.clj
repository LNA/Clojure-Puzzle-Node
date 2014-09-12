(ns microblog.tweet-parser
 (:require
   [clojure.string :refer [replace]]
   [clojure.java.io :as io]
   [clojure.set])
 (:refer-clojure :exclude [replace]))

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


(defn all-usernames [tweets]
  (set (concat (mapcat :receivers tweets)
   (map :sender tweets))))