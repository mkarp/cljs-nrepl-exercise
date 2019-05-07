(defproject cljs-nrepl-exercise "0.1.0-SNAPSHOT"

  :dependencies
  [[org.clojure/clojure "1.10.0"]
   [org.clojure/clojurescript "1.10.520"]]

  :plugins
  [[cider/cider-nrepl "0.21.1"]]

  :source-paths
  ["dev"
   "src"
   "target"]

  :profiles
  {:dev
   {:dependencies
    [[nrepl/nrepl "0.6.0"]
     [com.bhauman/figwheel-main "0.2.0"]
     [com.bhauman/rebel-readline "0.1.4"]
     [com.bhauman/rebel-readline-cljs "0.1.4"]
     [cider/piggieback "0.4.0"]
     [clojure-complete "0.2.5"]
     [proto-repl "0.3.1"]]

    :repl-options
    {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}}

  :aliases
  {"nrepl" ["with-profile" "dev" "repl"]
   "rebl" ["with-profile" "dev" "trampoline" "run" "-m" "rebel-readline.main"]})
