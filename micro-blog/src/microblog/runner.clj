(ns microblog.runner
    (:require [microblog.tweet-parser :refer [parse-tweets]])
    (:gen-class :main true))

(defn -main []
    (parse-tweets)
    )
