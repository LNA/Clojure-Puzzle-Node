(ns microblog.tweet-parser)
(use 'clojure.java.io)

(defn sender-content-message []
  (println "Here is a list of senders and their content: "))

(defn sender-receiver-message []
  (println "The senders and receivers are: "))

(defn first-level-message []
  (println "The first level connections are: "))

(defn name-of-sender [line]
  (re-find #"^[^:]+" line))

(defn content [line]
  (re-find #"[^:]+$" line))

(defn strip-symbol [tweet to-strip]
  (apply str (remove #((set to-strip) %) tweet)))

(defn receivers [line]
  (re-seq #"[^:]@[a-zA-Z]+" line))

(defn read-file [file]
  (with-open [rdr (reader file)]
    (doall (line-seq rdr))))

(defn output [requested-output input]
  (if (= :content requested-output)
      (content input)
      (receivers input)))

(defn parse-tweets [lines requested-output]
  (map 
    #(hash-map :sender (name-of-sender %), requested-output (output requested-output %)) lines))

(defn output-tweets [file requested-output]
  (let [lines (read-file file)
        tweets (parse-tweets lines requested-output) ]
    (doseq [line tweets]
      (println line))))

(defn output-first-level-connections [file]
  (let [lines (read-file file)
        level-one (find-first-level-connections lines)]
        (doseq [line level-one]
          (println line))))
 