(ns microblog.tweet-parser)
(use 'clojure.java.io)

(defn parse-tweets [file]
  (with-open [rdr (reader file)]
    (doseq [line (line-seq rdr)]
        (println line))))

(defn name-of-sender [line]
  (println  (re-find #"^[^:]+" line)))

(defn content [line]
  (println  (re-find #"[^:]+$" line)))

