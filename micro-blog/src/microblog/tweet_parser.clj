(ns microblog.tweet-parser)

(defn name-of-sender [file]
  (println  (re-find #"^[^:]+" (slurp file))))

(defn content [file]
  (println  (re-find #"[^:]+$" (slurp file))))


