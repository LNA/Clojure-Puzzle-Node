(ns microblog.tweet-parser)
(use 'clojure.java.io)

(defn sender-content-message []
  (println "Here is a list of senders and their content: "))

(defn sender-receiver-message []
  (println "The senders and receivers are: "))

(defn name-of-sender [line]
  (re-find #"^[^:]+" line))

(defn content [line]
  (re-find #"[^:]+$" line))

(defn receivers [line]
  (re-seq #"[^:]@[a-zA-Z]+" line))

(defn parse-tweets [file]
  (with-open [rdr (reader file)]
    (doseq [line (line-seq rdr)]
      (println { :sender (name-of-sender line) :content (content line)}))))

(defn parse-sender-and-receiver [file]
  (with-open [rdr (reader file)]
    (doseq [line (line-seq rdr)]
      (println { :sender (name-of-sender line) :receivers (receivers line)}))))