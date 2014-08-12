(defproject microblog "0.1.0"
  :description "Micro Blog"
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :profiles {:dev {:dependencies [[speclj "3.0.1"]]}}
  :plugins [[speclj "3.0.1"]]
  :test-paths ["spec"]
  :main microblog.runner
  :java-source-path "src/")