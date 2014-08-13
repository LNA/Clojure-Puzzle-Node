(ns microblog.tweet-parser)
(use 'clojure.java.io)

(defn name-of-sender [line]
  (re-find #"^[^:]+" line))

(defn content [line]
  (re-find #"[^:]+$" line))

(defn parse-tweets [file]
  (with-open [rdr (reader file)]
    (doseq [line (line-seq rdr)]
        (println { :sender (name-of-sender line) :content (content line)}))))
